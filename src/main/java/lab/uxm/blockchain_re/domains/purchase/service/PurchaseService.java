package lab.uxm.blockchain_re.domains.purchase.service;

import jakarta.security.auth.message.AuthException;
import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.domains.purchase.dto.BuyMusicResponseDto;
import lab.uxm.blockchain_re.domains.purchase.dto.DownloadMusicResponseDto;
import lab.uxm.blockchain_re.domains.purchase.dto.RetrievePurchaseHistoryResponseDto;

public interface PurchaseService {
  public BuyMusicResponseDto buyMusic(long musicId, String hash) throws IOException, AuthException;
  public DownloadMusicResponseDto downloadMusic(long musicId, String token) throws Exception;
  public List<RetrievePurchaseHistoryResponseDto> retrievePurchaseHistory() throws IOException;
}
