package cat.altimiras.matxos.ranking.dao;

import cat.altimiras.matxos.pojo.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationDAO extends JpaRepository<Registration, Long> {

    List<Registration> findByRaceAndPaymentIdNotNull(String race);

}
