package cat.matxos.ranking.services;

import cat.matxos.dao.ControlDAO;
import cat.matxos.dao.ReadDAO;
import cat.matxos.pojo.Control;
import cat.matxos.pojo.Read;
import cat.matxos.pojo.Runner;
import cat.matxos.ranking.pojo.FilterRanking;
import cat.matxos.ranking.pojo.ReadRanking;
import cat.matxos.services.RunnerService;
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

    private static Map<String, Map<String, Integer>> weightControlRace = new HashMap<>();

    final private SimpleDateFormat df = new SimpleDateFormat("HH'h' mm'm' ss's'");

    @Autowired
    private ReadDAO readDAO;

    @Autowired
    private ControlDAO controlDAO;

    @Autowired
    private RunnerService runnerService;

    @Autowired
    private Environment env;

    @Override
    public List<ReadRanking> rankingControl(String race, String control, FilterRanking filterRanking) {

        try {
            Read filter = new Read(race, control, null, null);
            List<Read> reads = readDAO.findAll(Example.of(filter), new Sort(Sort.Direction.ASC, "time"));
            return filterAndConvert(reads, race, filterRanking);
        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }


    @Override
    public List<ReadRanking> rankingBib(String race, String bib) {
        try {
            Read filter = new Read(race, null, bib, null);
            List<Read> reads = readDAO.findAll(Example.of(filter), new Sort(Sort.Direction.ASC, "time"));
            return filterAndConvert(reads, race, new FilterRanking());
        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<ReadRanking> ranking(String race, FilterRanking filterRanking) {
        try {
            if (!weightControlRace.containsKey(race)) {
                iniWeight(race);
            }

            List<Read> reads;
            if (filterRanking.getBib() != null && !filterRanking.getBib().isEmpty()) {
                reads = readDAO.findByBib(filterRanking.getBib(), race);
            } else {
                reads = readDAO.findAllRanking(race);
            }

            TreeSet<Read> orderByControl = new TreeSet<>(new Comparator<Read>() {
                @Override
                public int compare(Read r1, Read r2) {
                    Integer weight1 = weightControlRace.get(race).get(r1.getReadKey().getControl());
                    Integer weight2 = weightControlRace.get(race).get(r2.getReadKey().getControl());
                    weight1 = weight1 == null ? Integer.MAX_VALUE : weight1;
                    weight2 = weight2 == null ? Integer.MAX_VALUE : weight2;
                    if (weight1 == weight2) {
                        int compare = Long.valueOf(r1.getTime()).compareTo(Long.valueOf(r2.getTime()));
                        return compare == 0 ? -1 : compare;
                    } else {
                        return weight2.compareTo(weight1); //ojo girats!
                    }
                }
            });
            orderByControl.addAll(reads);

            return filterAndConvert(orderByControl, race, filterRanking);

        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }

    private void iniWeight(String race) {

        List<Control> controls = controlDAO.findAll(race);

        Map<String, Integer> weightRace = new HashMap<>();
        for (Control c : controls) {
            weightRace.put(c.getId(), c.getOrder());
        }
        weightControlRace.put(race, weightRace);
    }

    public ReadRanking getReadUI(String race, String control, String bib, String time) {
        Read r = new Read(race, control, bib, time);
        List<ReadRanking> lr = filterAndConvert(Arrays.asList(r), race, new FilterRanking());
        if (lr != null && !lr.isEmpty()) {
            return lr.get(0);
        }
        return null;
    }

    private List<ReadRanking> filterAndConvert(Collection<Read> reads, String race, FilterRanking filterRanking) {

        Map<String, Runner> runnersRace = runnerService.getRunners(race);

        long start = Long.valueOf(env.getProperty(race + ".start.ms", "0"));

        List<ReadRanking> rankings = new ArrayList<>();
        for (Read read : reads) {

            Runner runner = runnersRace.get(read.getReadKey().getBib());
            if (runner == null) {
                log.log(Level.WARNING, "runner does not exist. bib:" + read.getReadKey().getBib());
                continue;
            }
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
            if (filterRanking.getRoute() != null && !filterRanking.getRoute().isEmpty()) {
                inRoute = runner.getRoute().equals(filterRanking.getRoute());
            }
            if (filterRanking.getName() != null && !filterRanking.getName().isEmpty()) {
                inName = StringUtils.stripAccents(runner.getName().toLowerCase()).contains(StringUtils.stripAccents(filterRanking.getName().toLowerCase()));
            }

            if (inBib && inGender && inRoute && inName) {
                Control c = controlDAO.findOne(new Control.ControlKey(race, read.getReadKey().getControl()));
                ReadRanking ranking = new ReadRanking();
                ranking.setBib(read.getReadKey().getBib());
                ranking.setRace(race);
                ranking.setTime(getTime(start, read.getTime()));
                ranking.setTimeMs(read.getTime());
                ranking.setControl(c.getName());
                ranking.setName(runner.getName());
                ranking.setRoute(runner.getRoute());
                ranking.setGender(runner.getMale() ? "M" : "F");
                ranking.setControlWeight(c.getOrder());

                rankings.add(ranking);
            }
        }
        return rankings;
    }

    private String getTime(long start, String time) {

        if ("9999999999999".equals(time)) {
            return "OUT";
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.valueOf(time));
        c.set(Calendar.YEAR, 0);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 0);
        Date tr = c.getTime();

        c.setTimeInMillis(start);
        c.set(Calendar.YEAR, 0);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 0);
        Date sr = c.getTime();

        long diff = tr.getTime() - sr.getTime();
        Date date = new Date(diff);
        df.setTimeZone(TimeZone.getTimeZone("CEST"));
        return df.format(date);
    }
}
