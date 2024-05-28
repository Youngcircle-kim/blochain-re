package lab.uxm.blockchain_re.domains.nft.controller;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.domains.nft.dto.request.CreateNFTRequestDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.HasMintedResponseDto;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;
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
    NFT nft = this.nftService.retrieveNFTById(id);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.RETRIEVE_NFT_INFORMATION_SUCCESS,
            nft
        ),
        HttpStatus.OK
    );
  }
  @GetMapping("/")
  public ResponseEntity retrieveNFTList(
      @RequestParam(value = "musicId") @Valid long musicId
  ){
    List<NFT> nfts = this.nftService.retrieveNFTList(musicId);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.RETRIEVE_NFT_LIST_SUCCESS,
            nfts
        ),
        HttpStatus.OK
    );
  }

  @PostMapping("/meta")
  public ResponseEntity uploadMetadata(
      @Valid long musicId
  ) throws AuthException, IOException {
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.UPLOAD_NFT_METADATA_SUCCESS,
            this.nftService.uploadMeta(musicId)
        ),
        HttpStatus.OK
    );
  }

  @PostMapping("/create")
  public ResponseEntity createNFT(
      @Valid CreateNFTRequestDto dto
  ){
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.CREATE_NFT_SUCCESS,
            this.nftService.createNFT(dto)
        ),
        HttpStatus.OK
    );
  }
  @PostMapping("/sell/{id}")
  public ResponseEntity registerNftSale(
      @PathVariable @Valid Long id,
      @Valid String txId
  ){
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.REGISTER_NFT_SALE_SUCCESS,
            this.registerNftSale(id, txId)
        ),
        HttpStatus.OK
    );
  }
  @PostMapping("/purchase/{id}")
  public ResponseEntity purchaseNft(
      @PathVariable @Valid Long id,
      @Valid String txId
  ) throws AuthException, IOException {
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            NftResponseMessage.PURCHASE_NFT_SUCCESS,
            this.nftService.purchaseNFT(id, txId)
        ),
        HttpStatus.OK
    );
  }
}
