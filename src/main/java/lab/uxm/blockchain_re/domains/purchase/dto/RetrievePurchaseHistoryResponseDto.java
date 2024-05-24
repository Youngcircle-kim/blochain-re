package lab.uxm.blockchain_re.domains.purchase.dto;

import lab.uxm.blockchain_re.domains.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RetrievePurchaseHistoryResponseDto {
  private long id;
  private String title;
  private String artist;
  private String album;
  private String image;
  public static RetrievePurchaseHistoryResponseDto of(Music m, String album, String image){
    return RetrievePurchaseHistoryResponseDto.builder()
        .id(m.getId())
        .title(m.getTitle())
        .artist(m.getArtist())
        .album(album)
        .image(image)
        .build();
  }
}
