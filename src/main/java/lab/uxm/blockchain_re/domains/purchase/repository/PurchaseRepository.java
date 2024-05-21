package lab.uxm.blockchain_re.domains.purchase.repository;

import lab.uxm.blockchain_re.domains.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
