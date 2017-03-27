package cat.matxos.dao;

import cat.matxos.pojo.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadDAO extends JpaRepository<Read, Read.ReadKey> {

    //set sql_mode=''
    //@Query(value = "SELECT race, bib, control ,max(time) as time FROM time_reads WHERE race = ?1 GROUP BY BIB", nativeQuery = true)
    @Query(value = "select t.race, t.bib,t.t as time, t2.control from (SELECT race, bib, max(time) as t FROM time_reads WHERE race = ?1 group by bib) as t, time_reads as t2 where t.race=t2.race and t.t = t2.time and t.bib=t2.bib", nativeQuery = true)
    List<Read> findAllRanking(String race);

    @Query(value = "SELECT * FROM time_reads WHERE bib = ?1 AND race = ?2 ORDER BY time DESC LIMIT 1", nativeQuery = true)
    List<Read> findByBib(String bib, String race);

    @Query(value = "SELECT * FROM time_reads WHERE race = ?1", nativeQuery = true)
    List<Read> findAll(String race);

}
