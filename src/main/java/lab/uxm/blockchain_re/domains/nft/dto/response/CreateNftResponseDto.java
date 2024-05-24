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
public class CreateNftResponseDto {
  private long nftId;
  private long userNftId;

  public static CreateNftResponseDto from(UserNFT userNFT){
    return CreateNftResponseDto.builder()
        .nftId(userNFT.getNft().getId())
        .userNftId(userNFT.getId())
        .build();
  }
}
