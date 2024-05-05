package lab.uxm.blockchain_re.domains.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lab.uxm.blockchain_re.constant.converters.GenreConverter;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;
import lab.uxm.blockchain_re.domains.purchase.entity.Purchase;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "music")
@Getter
@Setter
public class Music {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String artist;

  @Column(unique = true, nullable = false)
  private String cid1;

  @Column(unique = true, nullable = true)
  private String cid2;

  @Column(unique = true, nullable = true)
  private String cid3;

  @Column(nullable = false)
  private String sha1;

  @Column(nullable = false)
  private String address1;

  @Column(nullable = false)
  //@Enumerated(EnumType.STRING)
  @Convert(converter = GenreConverter.class)
  private Genre genre;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "music")
  private List<NFT> nfts = new ArrayList<>();

  @OneToMany(mappedBy = "music")
  private List<Purchase> purchases = new ArrayList<>();

}
