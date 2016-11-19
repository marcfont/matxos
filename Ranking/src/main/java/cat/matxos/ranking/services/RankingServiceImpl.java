package cat.matxos.ranking.services;

import cat.matxos.pojo.Control;
import cat.matxos.pojo.Read;
import cat.matxos.dao.ReadDAO;
import cat.matxos.ranking.pojo.FilterRanking;
import cat.matxos.ranking.pojo.ReadRanking;
import cat.matxos.ranking.pojo.Runner;
import cat.matxos.services.ControlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RankingServiceImpl implements RankingService {

    private static Logger log = Logger.getLogger(RankingServiceImpl.class.getName());

    //Cache to avoid joins
    private static Map<String, Map<String, Runner>> runners = new HashMap<>(); //map of races/ map of runners
    private static long lastUpdate = System.currentTimeMillis();

    private static Map<String,Map<String, Integer>> weightControlRace = new HashMap<>();

    final private SimpleDateFormat df = new SimpleDateFormat("HH'h' mm'm' ss's'");

    @Autowired
    private ReadDAO readDAO;

    @Autowired
    private ControlService controlService;

    @Autowired
    private RunnerService runnerService;

    @Autowired
    private Environment env;

    @Override
    public List<ReadRanking> rankingControl(String race, String control, FilterRanking filterRanking) {

        try {
            Read filter = new Read(race, control, null, null);
            List<Read> reads = readDAO.findAll(Example.of(filter), new Sort(Sort.Direction.ASC, "time"));
            return  filterAndConvert(reads, race, filterRanking);
        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }


    @Override
    public List<ReadRanking> rankingBib (String race, String bib){
        try {
            Read filter = new Read(race, null, bib, null);
            List<Read> reads = readDAO.findAll(Example.of(filter), new Sort(Sort.Direction.ASC, "time"));
            return  filterAndConvert(reads, race, new FilterRanking());
        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<ReadRanking> ranking(String race, FilterRanking filterRanking) {
        try {
            if (!weightControlRace.containsKey(race)){
                iniWeight(race);
            }

            List<Read> reads;
            if (filterRanking.getBib() != null && !filterRanking.getBib().isEmpty()){
                reads = readDAO.findByBib(filterRanking.getBib(), race);
            } else {
                reads = readDAO.findAllRanking(race);
            }

            TreeSet<Read> orderByControl = new TreeSet<>(new Comparator<Read>() {
                @Override
                public int compare(Read r1, Read r2) {
                    Integer weight1 = weightControlRace.get(race).get(r1.getReadKey().getControl());
                    Integer weight2 = weightControlRace.get(race).get(r2.getReadKey().getControl());
                    if( weight1 == weight2) {
                        return Long.valueOf(r1.getTime()).compareTo(Long.valueOf(r2.getTime()));
                    } else {
                        return weight2.compareTo(weight1); //ojo girats!
                    }
                }
            });
            orderByControl.addAll(reads);

            return  filterAndConvert(orderByControl, race, filterRanking);

        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }

    private void iniWeight(String race){

        List<Control> controls = controlService.getControls(race);

        Map<String, Integer> weightRace = new HashMap<>();
        for(Control c : controls) {
            weightRace.put(c.getId(), c.getOrder());
        }
        weightControlRace.put(race, weightRace);
    }

    public ReadRanking getReadUI(String race, String control, String bib, String time){
        Read r = new Read(race,control,bib,time);
        List<ReadRanking> lr = filterAndConvert(Arrays.asList(r), race, new FilterRanking());
        if(lr!=null && !lr.isEmpty()){
            return lr.get(0);
        }
        return null;
    }

    private List<ReadRanking> filterAndConvert(Collection<Read> reads, String race, FilterRanking filterRanking){

        if (!runners.containsKey(race) || lastUpdate> System.currentTimeMillis() + 300000){ //every 5min
            Map<String, Runner> runnersRace = runnerService.getRunners(race);
            runners.put(race, runnersRace);
            lastUpdate = System.currentTimeMillis();
        }

        long start = Long.valueOf(env.getProperty(race + ".start.ms", "0"));

        List<ReadRanking> rankings = new ArrayList<>();
        for (Read read : reads){

            Runner runner = runners.get(race).get(read.getReadKey().getBib());
            boolean inBib = true;
            boolean inGender = true;
            boolean inRoute = true;
            boolean inName = true;


            if (filterRanking.getBib() != null && !filterRanking.getBib().isEmpty()) {
                inBib = runner.getBib().equals(filterRanking.getBib());
            }
            if (filterRanking.isMale() != null) {
                inGender = runner.getMale().equals(filterRanking.isMale());
            }
            if (filterRanking.getRoute() !=null && !filterRanking.getRoute().isEmpty()) {
                inRoute = runner.getRoute().equals(filterRanking.getRoute());
            }
            if (filterRanking.getName() != null && !filterRanking.getName().isEmpty()) {
                inName = StringUtils.stripAccents(runner.getName().toLowerCase()).contains(StringUtils.stripAccents(filterRanking.getName().toLowerCase()));
            }

            if (inBib && inGender && inRoute && inName) {
                ReadRanking ranking = new ReadRanking();
                ranking.setBib(read.getReadKey().getBib());
                ranking.setRace(race);
                ranking.setTime(getTime(start, read.getTime()));
                ranking.setControl(controlService.getControlName(race, read.getReadKey().getControl()));
                ranking.setName(runner.getName());
                ranking.setRoute(runner.getRoute());
                ranking.setGender(runner.getMale() ? "M" : "F");

                rankings.add(ranking);
            }
        }
        return  rankings;
    }



    private String getTime( long start, String time) {

        long diff = Long.valueOf(time) - start;
        Date date = new Date(diff);
        return df.format(date);
    }




}
