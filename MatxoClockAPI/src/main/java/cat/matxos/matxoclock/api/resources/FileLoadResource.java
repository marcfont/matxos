package cat.matxos.matxoclock.api.resources;

import cat.matxos.dao.ReadDAO;
import cat.matxos.pojo.Read;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class FileLoadResource {

    private static Logger log = Logger.getLogger(FileLoadResource.class.getName());

    @Autowired
    private ReadDAO readDAO;

    @Value("${validation.key}")
    private String psw;

    @PostMapping("/api/race/{race}/control/{control}/import")
    public String importReads(@PathVariable("race") String race,
                              @PathVariable("control") String control,
                              @RequestParam("psw") String psw,
                              @RequestParam("file") MultipartFile file) {

        try {
            if (psw.equals(this.psw)) {
                log.log(Level.INFO, "file import control " + control + " race " + race);

                String content = new String(file.getBytes());
                String[] reads = content.split("\n");
                for (String read : reads) {

                    String[] data = read.trim().split("/");

                    Read r = new Read(race,control, data[1].trim(), data[2].trim());
                    readDAO.save(r);
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error uploading import", e);
        }
        return "result";
    }

    @GetMapping("/api/race/{race}/control/{control}/upload")
    public String general(@PathVariable("race") String race,
                          @PathVariable("control") String control,
                          Model model){

        model.addAttribute("race", race);
        model.addAttribute("control", control);
        return "upload";
    }

}
