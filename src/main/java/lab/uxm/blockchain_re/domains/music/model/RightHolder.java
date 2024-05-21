package lab.uxm.blockchain_re.domains.music.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RightHolder {
  private long userId;
  private String walletAddress;
  private double proportion;

  @Builder
  public RightHolder(long userId, String walletAddress, double proportion) {
    this.userId = userId;
    this.walletAddress = walletAddress;
    this.proportion = proportion;
  }
}
