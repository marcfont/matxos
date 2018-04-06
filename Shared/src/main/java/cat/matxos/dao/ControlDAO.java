package cat.matxos.dao;

import cat.matxos.pojo.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ControlDAO extends JpaRepository<Control, Control.ControlKey> {

    @Query(value = "SELECT * FROM control WHERE id = ?1 AND race = ?2 ORDER BY time DESC LIMIT 1", nativeQuery = true)
    List<Control> findById(String id, String race);

    @Query(value = "SELECT * FROM control WHERE race = ?1 order by orderC asc", nativeQuery = true)
    List<Control> findAll(String race);

    @Query(value = "SELECT * FROM control WHERE race = ?1 AND orderC > (SELECT orderC from control WHERE race=?1 AND id=?2) order by orderC asc", nativeQuery = true)
    List<Control> findAfter(String race, String control);

    @Query(value = "SELECT * FROM control WHERE race = ?1 AND orderC < (SELECT orderC from control WHERE race=?1 AND id=?2) order by orderC asc", nativeQuery = true)
    List<Control> findBefore(String race, String control);

    @Query(value = "SELECT * FROM control WHERE race = ?1 AND orderC <= (SELECT orderC from control WHERE race=?1 AND id=?2) order by orderC asc", nativeQuery = true)
    List<Control> findBeforeOrEqual(String race, String control);
}