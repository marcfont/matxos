package cat.matxos.dao;

import cat.matxos.pojo.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationDAO extends JpaRepository<Registration, Long> {

    Long countByRace(String race);

    List<Registration> findByRaceAndIsCompleted(String race, boolean complete);

    Registration findByRaceAndPaymentId(String race, String paymentId);

    Long countBySizeAndAndIsCompletedIsTrue(String size);

    List<Registration> findByRaceAndBib(String race, String bib);

}
