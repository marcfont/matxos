package cat.matxos.matxoclock.api.resources;

import cat.matxos.dao.ReadDAO;
import cat.matxos.dao.RegistrationDAO;
import cat.matxos.matxoclock.api.form.ReadForm;
import cat.matxos.pojo.Read;
import cat.matxos.pojo.Registration;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

    @Value("${ranking.enabled}")
    private boolean enabled;

    @Value("${ranking.endpoint}")
    private String endpointRanking;

    private CloseableHttpClient client;

    public MatxoClockResource() throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build());

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000).
                        setSocketTimeout(1000).build();
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslsf).build();
    }

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

                //notify ranking
                notifyRanking(read);

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

                if ("CAB".equalsIgnoreCase(r.getReadKey().getControl())
                        || "PR2".equalsIgnoreCase(r.getReadKey().getControl())) {
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
            } else if (!cabrera && !puigsacalm) {
                route = "BELL";
            }
            log.log(Level.INFO, "Updating route bib:" + bib + " route: " + route);

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

    private void notifyRanking(ReadForm read) {

        try {

            if (enabled) {

                String url = String.format(endpointRanking, read.getRace(), read.getControl(), read.getBib(), read.getTime());
                HttpPut put = new HttpPut(url);

                HttpResponse response = client.execute(put);
                if (response != null && response.getStatusLine().getStatusCode() != 200){
                    log.log(Level.SEVERE, "error notifying ranking status code:" + response.getStatusLine().getStatusCode());
                }

            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error notifying ranking", e);
        }


    }
}
