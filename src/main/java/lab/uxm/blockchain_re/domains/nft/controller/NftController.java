package lab.uxm.blockchain_re.domains.nft.controller;

import jakarta.validation.Valid;
import java.util.List;
import lab.uxm.blockchain_re.domains.nft.dto.request.CreateNFTRequestDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.HasMintedResponseDto;
import lab.uxm.blockchain_re.domains.nft.message.NftResponseMessage;
import lab.uxm.blockchain_re.domains.nft.service.NftService;
import lab.uxm.blockchain_re.response.ResponseData;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("${api.prefix}/nft")
public class NftController {
  private final NftService nftService;
  @GetMapping("/hasMinted")
  public ResponseEntity hasMinted(
      @Valid long musicId
  ){
    HasMintedResponseDto dto = this.nftService.hasMinted(musicId);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.NFT_MINTED_SUCCESS,
            dto
        ),
        HttpStatus.OK
    );
  }

  @GetMapping("/my")
  public ResponseEntity retrieveMyNFT(){
    List<Object[]> nfts = this.nftService.retrieveMyNFT();

    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.MY_NFT_PURCHASE_LIST_SUCCESS,
            nfts
        ),
        HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity retrieveNFTInfo(
      @PathVariable @Valid long id
  ){
    return null;
  }
  @GetMapping("/")
  public ResponseEntity retrieveNFTList(
      @RequestParam(value = "musicId") @Valid long musicId
  ){
    return null;
  }

  @PostMapping("/meta")
  public ResponseEntity uploadMetadata(
      @Valid long musicId
  ){
    return null;
  }

  @PostMapping("/create")
  public ResponseEntity createNFT(
      @Valid CreateNFTRequestDto dto
  ){
    return null;
  }
  @PostMapping("/sell/{id}")
  public ResponseEntity registerNftSale(
      @PathVariable @Valid Long id,
      @Valid String txId
  ){
    return null;
  }
  @PostMapping("/purchase/{id}")
  public ResponseEntity purchaseNft(
      @PathVariable @Valid Long id,
      @Valid String txId
  ){
    return null;
  }
}
