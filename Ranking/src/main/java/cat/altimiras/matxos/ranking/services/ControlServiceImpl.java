package cat.altimiras.matxos.ranking.services;


import cat.altimiras.matxos.pojo.Control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ControlServiceImpl implements ControlService {

    @Autowired
    private Environment env;

    private Map<String, List<Control>> controlsRace;

    @Override
    public List<Control> getControls(String race) {

        if (controlsRace == null) {
            loadControls();
        }
        return controlsRace.get(race);
    }

    @Override
    public String getControlName(String id) {
        return "lololo"; //TODO
    }

    private void loadControls() {

        String[] races = env.getProperty("race.ids").split(",");
        controlsRace = new HashMap<>();
        for (String race : races) {

            List controls = new ArrayList();

            int i = 1;
            boolean end = false;
            while (!end) {
                Control c = new Control();
                String id = env.getProperty(race + ".control." + i + ".id");
                if (id == null) {
                    end = true;
                } else {
                    c.setId(id);
                    c.setOrder(i);
                    c.setName(env.getProperty( race + ".control." + i + ".name"));
                    controls.add(c);
                    i++;
                }
            }

            controlsRace.put(race, controls);
        }

    }
}