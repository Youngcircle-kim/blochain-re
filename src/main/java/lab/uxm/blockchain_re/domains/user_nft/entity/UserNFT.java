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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_nft")
@Getter
@Setter
public class UserNFT {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean is_sale;

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
}
