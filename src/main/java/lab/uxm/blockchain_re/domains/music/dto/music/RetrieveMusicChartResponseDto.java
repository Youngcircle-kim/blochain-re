package lab.uxm.blockchain_re.domains.music.dto.music;

import java.util.List;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveMusicChartResponseDto {
  private long id;
  private String title;
  private String image;
  private String artist;

  public static RetrieveMusicChartResponseDto of(Music m, String cid){
    return RetrieveMusicChartResponseDto.builder()
        .id(m.getId())
        .title(m.getTitle())
        .image(cid)
        .artist(m.getArtist())
        .build();
  }
}
