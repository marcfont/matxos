package cat.matxos.ranking.resources;

import cat.matxos.dao.ReadDAO;
import cat.matxos.pojo.Control;
import cat.matxos.pojo.Read;
import cat.matxos.pojo.Runner;
import cat.matxos.services.ControlService;
import cat.matxos.services.RunnerService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RankingExportResource {

    private static Logger log = Logger.getLogger(RankingExportResource.class.getName());

    @Autowired
    private ReadDAO readDAO;

    @Autowired
    private ControlService controlService;

    @Autowired
    private RunnerService runnerService;

    @GetMapping("ranking/race/{race}/ranking/export")
    public void export(@PathVariable("race") String race, @RequestParam("psw") String psw, HttpServletResponse response) {

        try {
            if (psw.equals("123")){

                Map<String, Map<String, String>> agg = new HashMap<>();
                List<Read> all = readDAO.findAll(race);

                for (Read r : all) {

                    Map<String, String> times = agg.get(r.getReadKey().getBib());
                    if (times == null){
                        times = new HashMap<>();
                    }

                    times.put(r.getReadKey().getControl(), r.getTime());
                    agg.put(r.getReadKey().getBib(), times);
                }

                StringBuffer buffer = new StringBuffer();
                List<Control> controls = controlService.getControls(race);

                for(Map.Entry<String, Map<String, String>> e : agg.entrySet()){

                    Runner runner = runnerService.getRunner(race, e.getKey());

                    buffer.append(e.getKey()); //bib
                    buffer.append(",");
                    buffer.append(runner.getName());
                    buffer.append(",");
                    for(Control c : controls){
                        buffer.append(e.getValue().getOrDefault(c.getId(), "-"));
                        buffer.append(",");
                    }

                    buffer.append(runner.getRoute());
                    buffer.append("\n");
                }

                InputStream is = new StringBufferInputStream(buffer.toString());
                IOUtils.copy(is, response.getOutputStream());
                response.setHeader("Content-type", "text/csv");
                response.setHeader("Content-disposition", "attachment;filename=export.csv");
                response.flushBuffer();
            } else {
                log.log(Level.SEVERE, "Psw incorrect");
            }
        } catch (Exception e){
            log.log(Level.SEVERE, "Error downloading csv", e);
        }


    }
}
