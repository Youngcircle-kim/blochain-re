package lab.uxm.blockchain_re.domains.purchase.controller;

import jakarta.validation.Valid;
import lab.uxm.blockchain_re.domains.purchase.service.PurchaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  ){
    return null;
  }

  @GetMapping("")
  public ResponseEntity checkPurchaseMusic(){
    return null;
  }

  @PostMapping("/{id}")
  public ResponseEntity purchaseMusic(
      final @PathVariable @Valid Long id,
      final @Valid String hash
  ){
    return null;
  }
}
