package lab.uxm.blockchain_re.domains.nft.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HasMintedResponseDto {
  private boolean hasMinted;
  private List<String> tx_id;

  public static HasMintedResponseDto from(List<String> tx_ids){
    return HasMintedResponseDto.builder()
        .hasMinted(!tx_ids.isEmpty())
        .tx_id(tx_ids)
        .build();
  }
}
