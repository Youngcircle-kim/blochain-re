package lab.uxm.blockchain_re.domains.purchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="music_id")
  private Music music;

  @Builder
  public Purchase(User user, Music music){
    this.user = user;
    this.music = music;
  }

  public static Purchase of(User user, Music music){
    return Purchase.builder()
        .user(user)
        .music(music)
        .build();
  }
}
