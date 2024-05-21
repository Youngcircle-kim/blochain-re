package lab.uxm.blockchain_re.domains.music.dto.upload.request;

import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
public class UploadMusicRequestDto {
  private String title;
  private String artist;
  private Genre genre;
  private List<Long> holder;
  private List<Double> rate;
  private String cid1;
  private String address;
  private MultipartFile file;

  @Builder
  public UploadMusicRequestDto(
      String title,
      String artist,
      Genre genre,
      List<Long> holder,
      List<Double> rate,
      String cid1, String address,
      MultipartFile file
  ) {
    this.title = title;
    this.artist = artist;
    this.genre = genre;
    this.holder = holder;
    this.rate = rate;
    this.cid1 = cid1;
    this.address = address;
    this.file = file;
  }
}
