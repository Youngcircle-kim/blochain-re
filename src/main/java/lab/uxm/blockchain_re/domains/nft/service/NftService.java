package lab.uxm.blockchain_re.domains.nft.service;

import jakarta.security.auth.message.AuthException;
import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.domains.nft.dto.request.CreateNFTRequestDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.CreateNftResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.HasMintedResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.PurchaseNFTResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.SellNFTResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.UploadMetadataResponseDto;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;

public interface NftService {
  HasMintedResponseDto hasMinted(long musicId);

  UploadMetadataResponseDto uploadMeta(long musicId) throws IOException, AuthException;
  CreateNftResponseDto createNFT(CreateNFTRequestDto dto);
  SellNFTResponseDto sellNFT(long id, String tx_id) throws AuthException;
  PurchaseNFTResponseDto purchaseNFT(long id, String tx_id) throws IOException, AuthException;
  List<NFT> retrieveNFTList(long musicId);

  List<Object[]> retrieveMyNFT();

  NFT retrieveNFTById(long id);
}
