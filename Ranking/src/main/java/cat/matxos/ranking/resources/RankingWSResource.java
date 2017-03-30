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
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RankingWSResource {

    private static Logger log = Logger.getLogger(RankingWSResource.class.getName());

    @Autowired
    private RankingService rankingService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Value("${ws.timeout}")
    private long timeout;

    @PostConstruct
    public void ini(){
        webSocket.setSendTimeout(timeout);
    }

    @RequestMapping(
            path = "ranking/races/{race}/controls/{control}/bibs/{bib}/push",
            method = RequestMethod.PUT)
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

}
