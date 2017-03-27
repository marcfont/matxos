package cat.matxos.control.dao;


import cat.matxos.control.pojo.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlTimesDao extends JpaRepository<Read, Read.ReadKey> {

    //9999999999999 -> means out!

    @Query(value = "select control, count(*) as count from time_reads where race=?1 and time!=9999999999999 group by control", nativeQuery = true)
    String[] getTimesCount(String race);

    @Query(value = "select * from time_reads where race=?1 and time=9999999999999 and control=?2", nativeQuery = true)
    List<Read> getOutsControl(String race, String control);

    @Query(value = "select * from time_reads where race=?1 and time!=9999999999999 and control=?2", nativeQuery = true)
    List<Read> getReadsControl(String race, String control);

    @Query(value = "select * from time_reads where race=?1  and time!=9999999999999 and control in (?2) group by bib", nativeQuery = true)
    List<Read> getReadsIn(String race, String[] control);

    @Query(value = "select * from time_reads where race=?1  and time=9999999999999 and control in (?2) group by bib", nativeQuery = true)
    List<Read> getOutsIn(String race, String[] control);
}
