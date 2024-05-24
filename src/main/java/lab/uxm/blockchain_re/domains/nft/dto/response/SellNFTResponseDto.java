package lab.uxm.blockchain_re.domains.nft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SellNFTResponseDto {
  private long musicId;

  public static SellNFTResponseDto from(long musicId){
    return SellNFTResponseDto.builder()
        .musicId(musicId)
        .build();
  }
}
