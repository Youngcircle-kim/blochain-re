package lab.uxm.blockchain_re.domains.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lab.uxm.blockchain_re.constant.converters.TypeConverter;
import lab.uxm.blockchain_re.constant.enums.Type;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;
import lab.uxm.blockchain_re.domains.purchase.entity.Purchase;
import lab.uxm.blockchain_re.domains.user_nft.entity.UserNFT;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  //@Enumerated(EnumType.STRING)
  @Convert(converter = TypeConverter.class)
  private Type type;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(unique = true, nullable = false)
  private String wallet;

  @OneToMany(mappedBy = "user")
  private List<Music> musics = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<NFT> nfts = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Purchase> purchases = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<UserNFT> userNfts = new ArrayList<>();
}
