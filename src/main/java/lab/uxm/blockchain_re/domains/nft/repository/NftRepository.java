package lab.uxm.blockchain_re.domains.nft.repository;

import java.util.List;
import java.util.Optional;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NftRepository extends JpaRepository<NFT, Long> {
  Optional<NFT> findById(long id);
  List<NFT> findAllByUser_idAndMusic_id(long userId, long musicId);
  NFT findByMusic_Id(long musicId);
  @Query("SELECT n FROM NFT n WHERE n.music.id =:musicId AND n.tx_id = :tx_id")
  Optional<NFT> findByMusic_IdAndTx_id(@Param("musicId")long musicId, @Param("tx_id")String tx_id);
  @Query("SELECT n FROM NFT n INNER JOIN UserNFT un WHERE n.id = un.nft.id AND n.music.id = :musicId ")
  List<NFT> findNFTsForSaleByMusicId(@Param("musicId") Long musicId);

  @Query("SELECT n FROM NFT n INNER JOIN UserNFT un where un.is_sale = true")
  List<NFT> findAllNFTsForSale();

  @Query("SELECT n, m.title, m.address1 "
      + "FROM NFT n "
      + "INNER JOIN UserNFT un ON n.id = un.nft.id "
      + "INNER JOIN Music m ON n.music.id = m.id "
      + "WHERE un.user.id = :userId AND un.is_sale = false")
  List<Object[]> findAllForSaleByUserId(@Param("userId") long userId);
}
