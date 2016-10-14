package cat.altimiras.matxos.matxoclock.api.dao;


import cat.altimiras.matxos.pojo.Read;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadDAO extends JpaRepository<Read, Read.ReadKey> {
}
