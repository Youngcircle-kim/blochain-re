package lab.uxm.blockchain_re.domains.music.dto.music;

import lab.uxm.blockchain_re.domains.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchMusicInfoDto {
  private long id;
  private String title;
  private String image;

  public static SearchMusicInfoDto from(Music m){
    return SearchMusicInfoDto.builder()
        .id(m.getId())
        .title(m.getTitle())
        .image(m.getCid1())
        .build();
  }
}
