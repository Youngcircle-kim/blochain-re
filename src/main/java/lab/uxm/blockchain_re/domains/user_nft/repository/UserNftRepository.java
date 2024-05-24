package lab.uxm.blockchain_re.domains.user_nft.repository;

import java.util.Optional;
import lab.uxm.blockchain_re.domains.user_nft.entity.UserNFT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNftRepository extends JpaRepository<UserNFT, Long> {
  Optional<UserNFT> findByUser_IdAndNft_Id(long userId, long nftId);
  Optional<UserNFT> findByNft_Id(long nftId);
}
