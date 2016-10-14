package cat.altimiras.matxos.registration.services;


import com.twilio.sdk.LookupsClient;
import com.twilio.sdk.resource.instance.lookups.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Configuration
public class TwilioPhoneServiceImpl implements PhoneService {

    private static Logger log = Logger.getLogger(TwilioPhoneServiceImpl.class.getName());

    @Value("${twilio.enabled}")
    private boolean enabled;

    @Value("${twilio.sid}")
    private String sid;

    @Value("${twilio.password}")
    private String psw;

    private LookupsClient twilioClient;

    @Override
    public boolean isValid(String phoneNumber) {

        try {

            if (!enabled) {
                return true;
            }

            if (twilioClient == null) {
                twilioClient = new LookupsClient(sid, psw);
            }

            String prefixedNum = getPrefixedNum(phoneNumber);
            PhoneNumber phone = twilioClient.getPhoneNumber(prefixedNum, true);
            if (PhoneNumber.Type.MOBILE.equals(phone.getType())) {
                return true;
            } else {
                log.log(Level.FINE, String.format("invalid phone numbrer: %s", phoneNumber));
                return false;
            }

        } catch (Exception e) {
            log.log(Level.WARNING, "error validating phone", e);
            return false;
        }
    }

    private String getPrefixedNum(String num) {
        if (num.startsWith("+34")) {
            return num;
        } else {
            return "+34" + num;
        }
    }
}
