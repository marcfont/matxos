package cat.matxos.registration.dao;

import cat.matxos.pojo.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentDAO extends JpaRepository<PaymentOrder, Long> {
}
