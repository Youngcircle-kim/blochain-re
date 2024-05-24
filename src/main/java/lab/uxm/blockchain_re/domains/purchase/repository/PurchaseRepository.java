package lab.uxm.blockchain_re.domains.purchase.repository;

import java.util.List;
import java.util.Optional;
import lab.uxm.blockchain_re.domains.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
  Optional<Purchase> findByUser_IdAndMusic_Id(long user_id, long music_id);
  boolean existsByUser_IdAndMusic_Id(long user_id, long music_id);
  List<Purchase> findAllByUser_Id(long id);
}
