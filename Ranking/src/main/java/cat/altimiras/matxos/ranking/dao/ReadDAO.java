package cat.altimiras.matxos.ranking.dao;

import cat.altimiras.matxos.pojo.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadDAO extends JpaRepository<Read, Read.ReadKey> {

    List<Read> findByRaceAndControl(String race, String control);
}
