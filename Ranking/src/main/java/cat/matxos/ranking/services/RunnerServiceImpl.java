package cat.matxos.ranking.services;


import cat.matxos.dao.RegistrationDAO;
import cat.matxos.pojo.Registration;
import cat.matxos.ranking.pojo.Runner;
import cat.matxos.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RunnerServiceImpl implements RunnerService {

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
        if (last == null || last + 300000 > System.currentTimeMillis()) {

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
        }

        return runners.get(race);
    }

    private String getName(Registration registration) {
        return new StringBuffer(registration.getSurname1())
                .append(" ")
                .append(registration.getSurname2())
                .append(", ")
                .append(registration.getName()).toString();
    }

}
