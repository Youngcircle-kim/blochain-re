package lab.uxm.blockchain_re.domains.purchase.controller;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.domains.purchase.dto.BuyMusicResponseDto;
import lab.uxm.blockchain_re.domains.purchase.dto.DownloadMusicResponseDto;
import lab.uxm.blockchain_re.domains.purchase.dto.RetrievePurchaseHistoryResponseDto;
import lab.uxm.blockchain_re.domains.purchase.message.PurchaseResponseMessage;
import lab.uxm.blockchain_re.domains.purchase.service.PurchaseServiceImpl;
import lab.uxm.blockchain_re.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/purchase")
public class PurchaseController {
  private final PurchaseServiceImpl purchaseService;
  @GetMapping("/{id}")
  public ResponseEntity downloadMusic(
      final @PathVariable @Valid Long id,
      final @RequestParam @Valid String token
  ) throws Exception {
    DownloadMusicResponseDto downloadMusicResponseDto = this.purchaseService.downloadMusic(id,
        token);
    return new ResponseEntity(
        ResponseData.res(
          HttpStatus.OK.value(),
            PurchaseResponseMessage.DOWNLOAD_FILE_SUCCESS,
            downloadMusicResponseDto
        ),
        HttpStatus.OK
    );
  }

  @GetMapping("")
  public ResponseEntity checkPurchaseMusic() throws IOException {
    List<RetrievePurchaseHistoryResponseDto> retrievePurchaseHistoryResponseDtos = this.purchaseService.retrievePurchaseHistory();
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            PurchaseResponseMessage.RETRIEVE_PURCHASE_HISTORY_SUCCESS,
            retrievePurchaseHistoryResponseDtos
        ),
        HttpStatus.OK
    );
  }

  @PostMapping("/{id}")
  public ResponseEntity purchaseMusic(
      final @PathVariable @Valid Long id,
      final @Valid String hash
  ) throws AuthException, IOException {
    BuyMusicResponseDto buyMusicResponseDto = this.purchaseService.buyMusic(id, hash);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            PurchaseResponseMessage.PURCHASE_MUSIC_SUCCESS,
            buyMusicResponseDto
        ),
        HttpStatus.OK
    );
  }
}
