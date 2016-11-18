package cat.matxos.matxoclock.api.resources;

import cat.matxos.dao.ReadDAO;
import cat.matxos.dao.RegistrationDAO;
import cat.matxos.matxoclock.api.form.ReadForm;
import cat.matxos.pojo.Read;
import cat.matxos.pojo.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MatxoClockResource {

    private static Logger log = Logger.getLogger(MatxoClockResource.class.getName());

    @Autowired
    private ReadDAO readDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

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

                //FIXME hack actualizar ruta
                if (read.getRace().startsWith("MATXO") && "ARR".equalsIgnoreCase(read.getControl())) {
                    updateRoute(read.getRace(), read.getBib());
                }

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

    private Read convert(ReadForm form) {
        return new Read(form.getRace(), form.getControl(), form.getBib(), form.getTime());
    }

    private void updateRoute(String race, String bib) {
        try {

            boolean puigsacalm = false;
            boolean cabrera = false;
            List<Read> reads = readDAO.findByBib(bib, race);
            for (Read r : reads) {

                if ("CAB".equalsIgnoreCase(r.getReadKey().getControl())) {
                    cabrera = true;
                }
                if ("PUIG".equalsIgnoreCase(r.getReadKey().getControl())) {
                    puigsacalm = true;
                }
            }

            String route = "MAT";
            if (cabrera && puigsacalm) {
                route = "MAT";
            } else if (cabrera && !puigsacalm) {
                route = "CAB";
            } else if (!cabrera && puigsacalm) {
                route = "PUI";
            }

            List<Registration> registration = registrationDAO.findByRaceAndBib(race, bib);
            if (registration != null && registration.size() == 1) {
                Registration registration1 = registration.get(0);
                registration1.setRoute(route);
                registrationDAO.save(registration1);

            } else {
                log.log(Level.SEVERE, "ERROR, bib repeated!!!!! bib:" + bib + " at race: " + race);
            }


        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating route", e);
        }
    }
}
