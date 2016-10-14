package cat.altimiras.matxos.registration.dao;

import cat.altimiras.matxos.pojo.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentDAO extends JpaRepository<PaymentOrder, Long> {
}
