package cat.altimiras.matxos.registration.services;


import cat.altimiras.matxos.pojo.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RouteServiceImpl implements RouteService {

    @Autowired
    private Environment env;

    private Map<String, List<Route>> routesRace;

    @Override
    public List<Route> getRoutes(String race) {

        if(routesRace == null) {
            loadRoutes();
        }
        return routesRace.get(race);
    }

    @Override
    public Route getRoute(String race, String id) {
        return routesRace.getOrDefault(race, new ArrayList<>()).stream().filter(r -> r.getId().equals(id)).findFirst().get();
    }

    private void loadRoutes() {

        String[] races = env.getProperty("race.ids").split(",");
        routesRace = new HashMap<>();
        for (String race : races){

            List routes = new ArrayList();

            int i = 1;
            boolean end = false;
            while (!end) {
                Route r = new Route();
                String id = env.getProperty(race +".route." + i + ".id");
                if (id == null) {
                    end = true;
                } else {
                    r.setId(id);
                    r.setName(env.getProperty(race +".route." + i + ".name"));
                    routes.add(r);
                    i++;
                }
            }

            routesRace.put(race, routes);

        }


    }
}
