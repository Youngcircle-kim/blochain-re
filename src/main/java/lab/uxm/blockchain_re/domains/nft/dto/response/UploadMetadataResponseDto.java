package lab.uxm.blockchain_re.domains.nft.dto.response;

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

  public static UploadMetadataResponseDto from(String cid){
    return UploadMetadataResponseDto.builder()
        .cid(cid)
        .build();
  }
}
