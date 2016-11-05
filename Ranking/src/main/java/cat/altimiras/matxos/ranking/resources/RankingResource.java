package cat.altimiras.matxos.ranking.resources;

import cat.altimiras.matxos.ranking.pojo.ReadRanking;
import cat.altimiras.matxos.ranking.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("ranking/race/{race}/ranking")
    public String general(@PathVariable("race") String race, Model model) {

        fillModel(model, race);


        return "general";
    }

    @GetMapping("ranking/race/{race}/control/{control}/ranking")
    public String control(@PathVariable("race") String race, @PathVariable("control") String control, Model model) {

        fillModel(model, race);
        model.addAttribute("control", control);

        try {
            List<ReadRanking> ranking = rankingService.rankingControl(race, control);
            model.addAttribute("rankings", ranking);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error ranking control", e);
        }

        return "control";
    }

    @GetMapping("ranking/race/{race}/bibs/{bib}/ranking")
    public String bib(@PathVariable("race") String race, @PathVariable("bib") String bib, Model model) {

        fillModel(model, race);

        return "bib";
    }

    private void fillModel(Model model, String race) {
        model.addAttribute("title", env.getProperty(race + ".race.name"));
        model.addAttribute("race", race);
    }
}
