package cat.matxos.services;


import cat.matxos.pojo.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RaceServiceImpl implements RaceService {

    @Autowired
    private Environment env;

    @Override
    public List<Race> getRaces() {
        List r = new ArrayList();
        String[] races = env.getProperty("race.ids").split(",");
        for (String race : races) {
            Race ra = new Race();
            ra.setId(race);
            ra.setName(env.getProperty(race + ".race.name"));
            //ra.setStarTime(Long.valueOf(env.getProperty(race + ".start.ms")));
            r.add(ra);
        }

        return r;
    }
}
