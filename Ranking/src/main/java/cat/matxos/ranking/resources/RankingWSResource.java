package cat.matxos.ranking.resources;

import cat.matxos.ranking.pojo.ReadWSMsg;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RankingWSResource {

    @MessageMapping("/ws/ranking")
    @SendTo("/topic/ranking")
    public ReadWSMsg greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return null;
    }
}
