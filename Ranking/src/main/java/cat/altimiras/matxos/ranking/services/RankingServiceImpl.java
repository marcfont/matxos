package cat.altimiras.matxos.ranking.services;

import cat.altimiras.matxos.pojo.Read;
import cat.altimiras.matxos.pojo.Registration;
import cat.altimiras.matxos.ranking.dao.ReadDAO;
import cat.altimiras.matxos.ranking.dao.RegistrationDAO;
import cat.altimiras.matxos.ranking.pojo.ReadRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RankingServiceImpl implements RankingService {

    private static Logger log = Logger.getLogger(RankingServiceImpl.class.getName());

    //Cache to avoid joins
    private static Map<String, String> runners;
    private static long lastUpdate;

    @Autowired
    private ReadDAO readDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private ControlService controlService;

    @Override
    public List<ReadRanking> rankingControl(String race, String control) {

        try {
            List<Read> reads = readDAO.findByRaceAndControl(race, control);
            return  convert(reads, race);
        } catch (Exception e) {
            log.log(Level.SEVERE, "error getting ranking", e);
        }
        return new ArrayList<>(0);
    }

    private List<ReadRanking> convert(List<Read> reads, String race){

        if (runners == null || lastUpdate> System.currentTimeMillis() + 300000){ //every 5min
            runners = getRunners(race);
        }

        List<ReadRanking> rankings = new ArrayList<>();
        for (Read read : reads){
            ReadRanking ranking = new ReadRanking();
            ranking.setBib(read.getReadKey().getBib());
            ranking.setRace(race);
            ranking.setTime(read.getTime());
            ranking.setControl(controlService.getControlName(read.getReadKey().getControl()));
            ranking.setName(runners.get(race+read.getReadKey().getBib()));

            rankings.add(ranking);
        }
        return  rankings;
    }


    private Map<String, String> getRunners(String race) {

        Map<String, String> runnersRace = new HashMap<>();
        List<Registration> registrations = registrationDAO.findByRaceAndPaymentIdNotNull(race);

        for (Registration registration : registrations) {

            if (registration.getBib() != null && !registration.getBib().isEmpty()) {
                String name = new StringBuffer(registration.getSurname1())
                        .append(" ")
                        .append(registration.getSurname2())
                        .append(", ")
                        .append(registration.getName()).toString();
                runnersRace.put(race + registration.getBib(), name);
            }
        }
        return runnersRace;
    }

}
