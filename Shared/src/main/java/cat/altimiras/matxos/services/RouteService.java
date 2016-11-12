package cat.altimiras.matxos.services;


import cat.altimiras.matxos.pojo.Route;

import java.util.List;

public interface RouteService {

    List<Route> getRoutes(String race);

    Route getRoute(String race, String id);
}
