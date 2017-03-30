package cat.matxos.control.dao;


import cat.matxos.control.pojo.Read;
import cat.matxos.pojo.ReadRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.SqlResultSetMapping;
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

    @Query(value = "select t.race as race, t.bib as bib, t.control as control, t.time as time, r.name as name, r.surname1 as surname1, r.surname2 as surname2, r.telfemer as telfemer from time_reads t, registration r where t.race=?1  and t.time!=9999999999999 and t.control in (?2) and t.race=r.race and t.bib=r.bib group by t.bib", nativeQuery = true)
    List<Object[]> getReadsIn(String race, String[] control);

    @Query(value = "select t.race as race, t.bib as bib, t.control as control, t.time as time, r.name as name, r.surname1 as surname1, r.surname2 as surname2, r.telfemer as telfemer from time_reads t, registration r where t.race=?1  and t.time=9999999999999 and t.control in (?2) and t.race=r.race and t.bib=r.bib group by t.bib", nativeQuery = true)
    List<Object[]> getOutsIn(String race, String[] control);

    @Query(value = "select t.* from time_reads t, control c where t.race=?1 and t.time!=9999999999999 and t.control=c.id and c.orderC > (select max( c1.orderC) from control c1, time_reads t1 where t1.race=t.race and t1.bib=t.bib and t1.control=c1.id and t1.time=9999999999999)", nativeQuery = true)
    List<Read> getReadsAfterOut(String race);

    @Query(value = "select t.* from time_reads t where t.race=?1 and t.bib not in (select bib from time_reads where race=t.race  and control='SOR') group by bib;", nativeQuery = true)
    List<Read> getReadsWithNoSOR(String race);


}

