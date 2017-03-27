package cat.matxos.scripts;


import cat.matxos.dao.RegistrationDAO;
import cat.matxos.pojo.Registration;
import cat.matxos.services.FEECService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FEECChecker {

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private FEECService feecService;

    public void check(String race, String outputFileName) throws Exception{
        System.out.println("Starting check race: " + race);

        StringBuffer buffer = new StringBuffer();

        List<Registration> registrationList = registrationDAO.findByRaceAndIsCompleted(race, true);

        System.out.println("There are : " + registrationList.size() + " to check");

        for(Registration r : registrationList){

            if (r.getFeec() != null && !r.getFeec().equals("1")){
                System.out.println("Checking " + r.getDni());
                if(!feecService.isValid(r.getFeec(), r.getDni())) {
                    buffer.append(r.getName());
                    buffer.append(",");
                    buffer.append(r.getEmail());
                    buffer.append(",");
                    buffer.append(r.getDni());
                    buffer.append(",");
                    buffer.append(r.getFeec());
                    buffer.append(",");
                    buffer.append("not-valid");
                    buffer.append("\n");

                    System.out.println(r.getDni() +  " is NOT valid");
                } else {
                    System.out.println(r.getDni() +  " is valid");
                }

                Thread.sleep(500);
            }
            Files.write(Paths.get(outputFileName), buffer.toString().getBytes());
        }

        System.out.println("Check done for race: " + race + " in file:" + outputFileName);
    }

}
