package cat.altimiras.matxos.matxoclock.api.resources;

import cat.altimiras.matxos.matxoclock.api.dao.ReadDAO;
import cat.altimiras.matxos.matxoclock.api.form.ReadForm;
import cat.altimiras.matxos.pojo.Read;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MatxoClockResource {

    private static Logger log = Logger.getLogger(MatxoClockResource.class.getName());

    @Autowired
    private ReadDAO readDAO;

    @Value("${validation.enabled}")
    private boolean validationEnabled;

    @Value("${validation.key}")
    private String key;

    @RequestMapping(
            path = "/api/race/{race}/read",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String read(@PathVariable("race") String race, @RequestBody ReadForm read) {
        log.log(Level.INFO, "new read " + read);
        try {

            if (!validationEnabled || (validationEnabled && isValid(read))) {
                readDAO.save(convert(read));
                return "ok";
            } else {
                return "ko";
            }

        } catch (DataIntegrityViolationException e) {
            log.log(Level.INFO, "Double read ignore" + read);
            return "ok";
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error saving read " + read, e);
            return "ko";
        }
    }

    private boolean isValid(ReadForm read) {

        try {
            String chunk = String.format("%s-%s-%s-%s-%s", read.getBib(), read.getControl(), read.getRace(), read.getTime(), key);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(chunk.getBytes("UTF-8"));
            return new String(digest.digest()).equals(read.getChecksum());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error validating checksum", e);
            return false;
        }
    }

    private Read convert (ReadForm form){
        return new Read(form.getRace(), form.getControl(), form.getBib(), form.getTime());
    }
}
