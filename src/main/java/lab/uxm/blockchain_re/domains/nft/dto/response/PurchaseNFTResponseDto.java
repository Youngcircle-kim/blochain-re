package lab.uxm.blockchain_re.domains.nft.dto.response;

import lab.uxm.blockchain_re.domains.user_nft.entity.UserNFT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PurchaseNFTResponseDto {
  private long userNFTId;

  public static PurchaseNFTResponseDto from(UserNFT userNFT){
    return PurchaseNFTResponseDto.builder()
        .userNFTId(userNFT.getId())
        .build();
  }
}
