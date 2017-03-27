package cat.matxos.scripts;


import cat.matxos.dao.RegistrationDAO;
import cat.matxos.pojo.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BibAssign {

    @Autowired
    private RegistrationDAO registrationDAO;

    public void assign(int startBib, String race) {

        List<Registration> registrationList = registrationDAO.findByRaceAndIsCompleted(race, true);

        System.out.println("There are : " + registrationList.size() + " to assign");

        for (Registration r : registrationList) {

            if(r.getBib() == null){
                r.setBib(String.valueOf(startBib));
                startBib++;
            }

            registrationDAO.save(r);

        }
        System.out.println("Bibs assigned. Last bib: " + startBib);
    }
}
