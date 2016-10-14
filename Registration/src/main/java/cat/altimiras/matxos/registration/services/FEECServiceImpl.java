package cat.altimiras.matxos.registration.services;


import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FEECServiceImpl implements FEECService {

    private static Logger log = Logger.getLogger(FEECServiceImpl.class.getName());

    @Value("${feec.endpoint}")
    private String endpoint;

    @Value("${feec.enabled}")
    private boolean enabled;

    private CloseableHttpClient client;

    public FEECServiceImpl() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000).
                        setSocketTimeout(1000).build();
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }

    @Override
    public boolean isValid(String numFEEC, String DNI) {

        try {
            if(!enabled) {
                return true;
            }

            if (numFEEC == null || numFEEC.isEmpty()){
                return true;
            }

            String url = String.format(endpoint, numFEEC, DNI);
            HttpGet get = new HttpGet(url);

            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200){
                /*
                OK resp = {"response":{"status":{"codi":"200","message":"OK"},"content":{"subcategories-clubs":[{"nomSubCategoria":"Majors habilitada FEDME - D","nomClub":"CENTRE EXCTA. TORELL\u00d3"}]}}}
                KO resp = {"response":{"status":{"codi":"200","message":"OK"},"content":{"subcategories-clubs":[]}}}
                 */
                String result = EntityUtils.toString(response.getEntity());
                if (result.contains("nomClub")) { //versio xapusa
                    return true;
                } else {
                    log.log(Level.FINE, String.format("invalid FEEC dni: %s, feec: %s ",DNI, numFEEC));
                    return false;
                }
            } else {
                log.log(Level.WARNING, "error validating FEEC, status code: " + response.getStatusLine().getStatusCode());
                return false;
            }

        } catch (Exception e) {
            log.log(Level.WARNING, "error validating FEEC", e);
            return false;
        }
    }
}
