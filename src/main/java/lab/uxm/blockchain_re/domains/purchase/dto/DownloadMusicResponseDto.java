package lab.uxm.blockchain_re.domains.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DownloadMusicResponseDto {
  private byte[] file;
  private String fileName;
  public static DownloadMusicResponseDto of(byte[] file, String fileName){
    return DownloadMusicResponseDto.builder()
        .file(file)
        .fileName(fileName)
        .build();
  }
}
