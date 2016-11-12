package cat.altimiras.matxos.ranking.services;


import cat.altimiras.matxos.ranking.pojo.Runner;

import java.util.Map;

public interface RunnerService {


    Runner getRunner(String race, String bib);

    Map<String, Runner> getRunners(String race);
}
