package lab.uxm.blockchain_re.domains.user.repository;

import java.util.Optional;
import lab.uxm.blockchain_re.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(long id);
  Optional<User> findUserByEmail(String email);
}
