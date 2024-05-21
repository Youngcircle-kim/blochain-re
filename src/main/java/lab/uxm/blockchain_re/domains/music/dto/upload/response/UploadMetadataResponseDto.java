package lab.uxm.blockchain_re.domains.music.dto.upload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UploadMetadataResponseDto {
  private String cid;
}
