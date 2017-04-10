package cat.matxos.scripts;

import cat.matxos.dao.ReadDAO;
import cat.matxos.dao.RegistrationDAO;
import cat.matxos.pojo.Read;
import cat.matxos.pojo.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteChecker {

    @Autowired
    private ReadDAO readDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    public void check( String race) {

        List<Registration> registrations = registrationDAO.findByRaceAndIsCompleted(race, true);
        for(Registration r : registrations){
            updateRoute(race, r.getBib());
        }
    }

    private void updateRoute(String race, String bib) {
        try {

            boolean puigsacalm = false;
            boolean cabrera = false;
            Read filter = new Read(race, null, bib, null);
            List<Read> reads = readDAO.findAll(Example.of(filter), new Sort(Sort.Direction.ASC, "time"));
            for (Read r : reads) {

                if ("CAB".equalsIgnoreCase(r.getReadKey().getControl())
                        || "PR2".equalsIgnoreCase(r.getReadKey().getControl())) {
                    cabrera = true;
                }
                if ("PUI".equalsIgnoreCase(r.getReadKey().getControl())) {
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
            System.out.println("Updating route bib:" + bib + " route: " + route);

            List<Registration> registration = registrationDAO.findByRaceAndBib(race, bib);
            if (registration != null && registration.size() == 1) {
                Registration registration1 = registration.get(0);
                registration1.setRoute(route);
                registrationDAO.save(registration1);

            } else {
                System.out.println("ERROR, bib repeated!!!!! bib:" + bib + " at race: " + race);
            }


        } catch (Exception e) {
            System.out.println("Error updating route" + e.getLocalizedMessage());
        }
    }
}
