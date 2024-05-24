package lab.uxm.blockchain_re.domains.purchase.dto;

import lab.uxm.blockchain_re.domains.purchase.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BuyMusicResponseDto {
  private long id;

  public static BuyMusicResponseDto from(Purchase purchase){
    return BuyMusicResponseDto.builder()
        .id(purchase.getId())
        .build();
  }
}
