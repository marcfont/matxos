package cat.matxos.ranking.resources;

import cat.matxos.dao.ControlDAO;
import cat.matxos.pojo.Control;
import cat.matxos.pojo.Runner;
import cat.matxos.ranking.pojo.FilterRanking;
import cat.matxos.ranking.pojo.ReadRanking;
import cat.matxos.ranking.services.RankingService;
import cat.matxos.services.RouteService;
import cat.matxos.services.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RankingResource {

    private static Logger log = Logger.getLogger(RankingResource.class.getName());

    @Autowired
    private Environment env;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private ControlDAO controlDAO;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RunnerService runnerService;
/*
    @Autowired
    private SimpMessagingTemplate webSocket;

    @Value("${ws.timeout}")
    private long timeout;

    @PostConstruct
    public void ini(){
        webSocket.setSendTimeout(timeout);
    }
*/
    @GetMapping("ranking/race/{race}/ranking")
    public String general(@PathVariable("race") String race,
                          @RequestParam(value = "bib", required = false) String bib,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "route", required = false) String route,
                          @RequestParam(value = "gender", required = false) String gender,
                          Model model) {

        fillModel(model, race);

        try {
            FilterRanking filterRanking = new FilterRanking(bib, name, route, gender);
            List<ReadRanking> ranking = rankingService.ranking(race, filterRanking);
            model.addAttribute("rankings", ranking);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error ranking control", e);
            return "error";
        }


        return "general";
    }

    @GetMapping("ranking/race/{race}/control/{control}/ranking")
    public String control(@PathVariable("race") String race, @PathVariable("control") String control,
                          @RequestParam(value = "bib", required = false) String bib,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "route", required = false) String route,
                          @RequestParam(value = "gender", required = false) String gender,
                          Model model) {

        fillModel(model, race);
        model.addAttribute("control", control);
        try {
            FilterRanking filterRanking = new FilterRanking(bib, name, route, gender);
            model.addAttribute("controltitle", controlDAO.getOne(new Control.ControlKey(race, control)).getName().toUpperCase());

            List<ReadRanking> ranking = rankingService.rankingControl(race, control, filterRanking);
            model.addAttribute("rankings", ranking);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error ranking control", e);
            return "error";
        }

        return "control";
    }

    @GetMapping("ranking/race/{race}/bibs/{bib}/ranking")
    public String bib(@PathVariable("race") String race, @PathVariable("bib") String bib, Model model) {

        fillModel(model, race);

        try {
            Runner runner = runnerService.getRunner(race, bib);
            model.addAttribute("runner", runner.getName().toUpperCase());
            model.addAttribute("route", runner.getRoute());
            model.addAttribute("bib", bib);

            List<ReadRanking> ranking = rankingService.rankingBib(race, bib);
            model.addAttribute("rankings", ranking);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error ranking control", e);
            return "error";
        }

        return "bib";
    }
/*
    @PutMapping("ranking/races/{race}/controls/{control}/bibs/{bib}/push")
    public void pushRead(@PathVariable("race") String race,
                         @PathVariable("control") String control,
                         @PathVariable("bib") String bib,
                         @RequestParam(value = "time", required = false) String time) {

        try {
            log.log(Level.INFO, "WS push recieved bib:" + bib + " control:" + control + " race:" + race);
            ReadRanking r = rankingService.getReadUI(race, control, bib, time);
            webSocket.convertAndSend("/topic/ranking", r);
            log.log(Level.INFO, "WS msg delivered bib:" + bib + " control:" + control + " race:" + race);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error pushing WS", e);
        }
    }
*/
    private void fillModel(Model model, String race) {
        model.addAttribute("title", env.getProperty(race + ".race.name"));
        model.addAttribute("race", race);
        model.addAttribute("routes", routeService.getRoutes(race));
        model.addAttribute("controls", controlDAO.findAll(race));
    }
}
