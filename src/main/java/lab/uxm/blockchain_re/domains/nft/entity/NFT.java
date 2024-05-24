package lab.uxm.blockchain_re.domains.nft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.nft.dto.request.CreateNFTRequestDto;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lab.uxm.blockchain_re.domains.user_nft.entity.UserNFT;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nft")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NFT {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String cid;

  @Column(nullable = false)
  private String contractAddress;

  @Column(nullable = false)
  private String tx_id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "music_id")
  private Music music;

  @OneToMany(mappedBy = "nft")
  private List<UserNFT> userNfts = new ArrayList<>();
  @Builder
  public NFT(String cid, String contractAddress,String tx_id, User user, Music music){
    this.cid = cid;
    this.contractAddress = contractAddress;
    this.tx_id = tx_id;
    this.user = user;
    this.music = music;
  }
  public static NFT of(CreateNFTRequestDto dto,User user, Music music){
    return NFT.builder()
        .cid(dto.getCid())
        .contractAddress(dto.getContractAddr())
        .tx_id(dto.getTxId())
        .user(user)
        .music(music)
        .build();
  }
}
