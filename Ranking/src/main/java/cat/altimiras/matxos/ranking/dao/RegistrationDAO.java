package cat.altimiras.matxos.ranking.dao;

import cat.altimiras.matxos.pojo.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationDAO extends JpaRepository<Registration, Long> {

    List<Registration> findByRaceAndIsCompleted(String race, boolean complete);

    List<Registration> findByRaceAndBib(String race, String bib);

}
