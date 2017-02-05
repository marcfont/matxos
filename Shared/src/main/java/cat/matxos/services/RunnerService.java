package cat.matxos.services;



import cat.matxos.pojo.Runner;

import java.util.Map;

public interface RunnerService {


    Runner getRunner(String race, String bib);

    Map<String, Runner> getRunners(String race);
}
