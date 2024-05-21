package lab.uxm.blockchain_re.domains.music.dto.upload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveMusicInfoResponseDto {
  private long id;
  private String title;
  private String album;
  private String artist;

  //image encrypt base64 string
  private String image;
}
