package lab.uxm.blockchain_re.domains.music.dto.upload.request;

import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UploadMetadataRequestDto {
  private String title;
  private long artistId;
  private String artist;
  private String album;
  private MultipartFile image;
  private String lyrics;
  private Genre genre;
  private List<Long> composerId;
  private List<Long> songWriterId;
}
