package cat.matxos.services;


import cat.matxos.dao.RegistrationDAO;
import cat.matxos.pojo.Registration;
import cat.matxos.pojo.Runner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RunnerServiceImpl implements RunnerService {

    private static Logger log = Logger.getLogger(RunnerServiceImpl.class.getName());


    //super simple cache
    private static Map<String, Long> lastUpdate = new HashMap<>();

    private static Map<String, Map<String, Runner>> runners = new HashMap<>();

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private RouteService routeService;

    @Override
    public Runner getRunner(String race, String bib) {
        Map<String, Runner> ruRace = getRunners(race);
        return ruRace.get(bib);
    }

    @Override
    public Map<String, Runner> getRunners(String race) {

        Long last = lastUpdate.get(race);
        if (last != null) {
            log.info("last update was: " +  new Date(last).toString());
        }

        if (last == null || last + 30000 < System.currentTimeMillis()) {
            log.info("updating cache runners");
            Map<String, Runner> runnersRace = new HashMap<>();
            List<Registration> registrations = registrationDAO.findByRaceAndIsCompleted(race, true);

            for (Registration registration : registrations) {

                if (registration.getBib() != null && !registration.getBib().isEmpty()) {

                    String route = routeService.getRoute(race, registration.getRoute()).getName();

                    Runner r = new Runner(getName(registration), registration.getBib(), race, route, registration.getGender().equals("H"));
                    runnersRace.put(registration.getBib(), r);
                }
            }

            lastUpdate.put(race, System.currentTimeMillis());
            runners.put(race, runnersRace);
            log.info("updated cache runners");
        }

        return runners.get(race);
    }

    private String getName(Registration registration) {
        return new StringBuffer(StringUtils.capitalize(registration.getSurname1().toLowerCase()))
                .append(" ")
                .append(StringUtils.capitalize(registration.getSurname2().toLowerCase()))
                .append(", ")
                .append(StringUtils.capitalize(registration.getName().toLowerCase())).toString();
    }

}
