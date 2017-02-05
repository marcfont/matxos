package cat.matxos.dao;

import cat.matxos.pojo.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationDAO extends JpaRepository<Registration, Long> {

    Long countByRaceAndIsCompletedIsTrue(String race);

    List<Registration> findByRaceAndIsCompleted(String race, boolean complete);

    List<Registration> findByRaceAndIsWaitingIsTrueAndIsCompletedIsFalse(String race);

    Registration findByRaceAndPaymentId(String race, String paymentId);

    @Query(value = "SELECT count(*) FROM registration WHERE size = ?1 AND gender = ?2 AND race=?3 AND is_completed=1", nativeQuery = true)
    Long countBySizeAndAndIsCompletedIsTrueAndGender(String size, String gender, String race);

    List<Registration> findByRaceAndBib(String race, String bib);

}
