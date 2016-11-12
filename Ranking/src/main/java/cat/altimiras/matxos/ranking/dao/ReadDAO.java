package cat.altimiras.matxos.ranking.dao;

import cat.altimiras.matxos.pojo.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadDAO extends JpaRepository<Read, Read.ReadKey> {

    @Query(value = "SELECT * FROM time_reads WHERE race = ?1 GROUP BY BIB", nativeQuery = true)
    List<Read> findAllRanking(String race);

    @Query(value = "SELECT * FROM time_reads WHERE bib = ?1 AND race = ?2 GROUP BY BIB", nativeQuery = true)
    List<Read> findByBib(String bib, String race);
}
