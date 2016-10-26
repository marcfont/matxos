package cat.altimiras.matxos.registration.dao;

import cat.altimiras.matxos.pojo.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationDAO extends JpaRepository<Registration, Long> {

    Long countByRace(String race);

    List<Registration> findByRaceAndPaymentIdNotNull(String race);

    Registration findByRaceAndPaymentId(String race, String paymentId);

    Long countBySizeAndPaymentIdNotNull(String size);

}
