package lab.uxm.blockchain_re.domains.music.dto.upload.response;

import lab.uxm.blockchain_re.domains.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UploadMusicResponseDto {
  private long id;

  public static UploadMusicResponseDto from(Music music){
    return UploadMusicResponseDto
        .builder()
        .id(music.getId())
        .build();
  }
}
