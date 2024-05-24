package lab.uxm.blockchain_re.domains.nft.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateNFTRequestDto {
  private long musicId;
  private String cid;
  private String contractAddr;
  private String txId;
}
