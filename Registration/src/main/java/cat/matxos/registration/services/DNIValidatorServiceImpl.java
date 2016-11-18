package cat.matxos.registration.services;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DNIValidatorServiceImpl implements DNIValidatorService{

    private static Logger log = Logger.getLogger(DNIValidatorServiceImpl.class.getName());

    @Value("${vip.file.path}")
    private String path;

    @Value("${vip.enabled}")
    private boolean vipEnabled;

    private String validChars = "TRWAGMYFPDXBNJZSQVHLCKET";
    private Pattern nifRexp = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKET]{1}");
    private Pattern nieRexp = Pattern.compile("^[XYZ]{1}[0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKET]{1}");

    private static Map<String, Object> vipDnis;

    @Override
    public boolean isValid(String dni) {

        Matcher dniMatcher = nifRexp.matcher(dni);
        Matcher nieMatcher = nieRexp.matcher(dni);

        if ( dniMatcher.find() ||  nieMatcher.find()) {

            String nie = dni.replaceFirst("^[X]", "0")
                    .replaceFirst("^[Y]", "1")
                    .replaceFirst("^[Z]", "2");

            String letter = nie.substring(nie.length()-1);
            int charIndex = Integer.valueOf(nie.substring(0,nie.length()-1)) % 23;

            return String.valueOf(validChars.charAt(charIndex)).equals(letter);
        } else {
            return false;
        }
    }

    @Override
    public boolean isVIP(String dni) {

        if (vipDnis==null && vipEnabled) {
            loadVIPDni();
        }

        return vipDnis.containsKey(dni.toUpperCase());
    }

    private void loadVIPDni(){

        try {
            vipDnis = new HashMap<>();
            String content = FileUtils.readFileToString(new File(path));
            String[] dnis = content.split(",");
            for (String dni : dnis){
                vipDnis.put(dni.toUpperCase(),dni);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error loading vip dnis",e);
        }

    }
}
