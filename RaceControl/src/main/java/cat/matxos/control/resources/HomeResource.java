package cat.matxos.control.resources;

import cat.matxos.control.dao.ControlTimesDao;
import cat.matxos.control.pojo.ControlInfo;
import cat.matxos.control.pojo.Read;
import cat.matxos.pojo.Control;
import cat.matxos.pojo.Race;
import cat.matxos.services.ControlService;
import cat.matxos.services.RaceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class HomeResource {

    private static Logger log = Logger.getLogger(HomeResource.class.getName());

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ControlTimesDao timesDao;

    @Autowired
    private ControlService controlService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private Environment env;

    @GetMapping("/control/home")
    public String home(Model model) {

        List<Race> races = raceService.getRaces();
        model.addAttribute("races", races);
        model.addAttribute("tite", "Control");

        return "home";
    }

    @GetMapping("/control/races/{race}")
    public String homeRace(@PathVariable("race") String race, Model model) {


        fillModel(model, race);
        return "race";
    }

    @PostMapping("/control/races/{race}/out")
    public String newOut(@PathVariable("race") String race, @RequestParam("control") String control, @RequestParam("bib") String bib, Model model) {

        try {
            Read out = new Read(race, control, bib, "9999999999999");
            timesDao.save(out);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error adding out", e);
            return "error";
        }

        fillModel(model, race);
        return "race";
    }

    @PostMapping("/control/races/{race}/read")
    public String newRead(@PathVariable("race") String race, @RequestParam("control") String control, @RequestParam("bib") String bib, @RequestParam("time") String time, Model model) {

        try {

            long startTime = Long.valueOf(env.getProperty("MATXOS17.start.ms"));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(startTime);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            long day = c.getTimeInMillis();

            Date d = dateFormat.parse(time);

            Read newread = new Read(race, control, bib, String.valueOf( day+d.getTime()));
            timesDao.save(newread);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error adding out", e);

            model.addAttribute("error-msg", e.getMessage());

            return "error";
        }
        fillModel(model, race);
        return "race";
    }

    @GetMapping("/control/races/{race}/pending")
    public String pending(@PathVariable("race") String race,
                          @RequestParam(value = "control", required = true) String control,
                          Model model) {

        List<Control> controlsBefore =  controlService.getControlsBefore(race, control);
        List<Control> controlsAfter =  controlService.getControlsAfter(race, control);

        String[] controlsBeforeNames = controlsBefore.stream().map(c ->  c.getId() ).collect(Collectors.toList()).toArray(new String[controlsBefore.size()]);
        String[] controlsAfterNames = controlsAfter.stream().map(c ->  c.getId() ).collect(Collectors.toList()).toArray(new String[controlsAfter.size()]);

        List<Read> in = new ArrayList<>();
        List<Read> outs = new ArrayList<>();
        if (controlsBeforeNames.length != 0){
            in = timesDao.getReadsIn(race, controlsBeforeNames);
            outs = timesDao.getOutsIn(race, controlsBeforeNames);

        }
        List<Read> notIn = new ArrayList<>();
        if (controlsAfterNames.length != 0){
            notIn =timesDao.getReadsIn(race, controlsAfterNames);
        }


        in.removeAll(notIn);
        in.removeAll(outs);

        model.addAttribute("reads", in);

        fillModel(model, race);
        return "pending";
    }

    private void fillModel(Model model, String race) {
        model.addAttribute("controls", controlService.getControls(race));
        model.addAttribute("title", env.getProperty(race + ".race.name"));
        model.addAttribute("race", race);
    }
}
