package lab.uxm.blockchain_re.domains.user_nft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_nft")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNFT {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private boolean is_sale;

  @Column(nullable = true)
  private String sell_tx;

  @Column(nullable = true)
  private  String purchase_tx;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nft_id")
  private NFT nft;
  @Builder
  public UserNFT(boolean is_sale, String sell_tx, String purchase_tx, User user, NFT nft){
    this.is_sale = is_sale;
    this.sell_tx = sell_tx;
    this.purchase_tx = purchase_tx;
    this.user = user;
    this.nft = nft;
  }
  public void updateTxId(String txId){
    this.sell_tx = txId;
    this.purchase_tx = null;
    this.is_sale = true;
  }
  public void updateUserIdTxId(User user, String tx_id){
    this.user = user;
    this.sell_tx = null;
    this.purchase_tx = tx_id;
    this.is_sale = false;
  }
  public static UserNFT of(NFT nft, boolean is_sale){
    return UserNFT.builder()
        .is_sale(is_sale)
        .user(nft.getUser())
        .nft(nft)
        .sell_tx(nft.getTx_id())
        .purchase_tx(null)
        .build();
  }
}
