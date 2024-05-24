package lab.uxm.blockchain_re.domains.nft.service;

import jakarta.security.auth.message.AuthException;
import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.music.message.MusicResponseMessage;
import lab.uxm.blockchain_re.domains.music.repository.MusicRepository;
import lab.uxm.blockchain_re.domains.nft.dto.request.CreateNFTRequestDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.CreateNftResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.HasMintedResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.PurchaseNFTResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.SellNFTResponseDto;
import lab.uxm.blockchain_re.domains.nft.dto.response.UploadMetadataResponseDto;
import lab.uxm.blockchain_re.domains.nft.entity.NFT;
import lab.uxm.blockchain_re.domains.nft.message.NftResponseMessage;
import lab.uxm.blockchain_re.domains.nft.repository.NftRepository;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lab.uxm.blockchain_re.domains.user.message.AuthResponseMessage;
import lab.uxm.blockchain_re.domains.user.message.UserResponseMessage;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lab.uxm.blockchain_re.domains.user.service.UserServiceImpl;
import lab.uxm.blockchain_re.domains.user_nft.entity.UserNFT;
import lab.uxm.blockchain_re.domains.user_nft.repository.UserNftRepository;
import lab.uxm.blockchain_re.provider.Web3jContractProvider;
import lab.uxm.blockchain_re.utils.IPFSUtil;
import lab.uxm.blockchain_re.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NftServiceImpl implements NftService{
  private final NftRepository nftRepository;
  private final UserServiceImpl userService;
  private final UserRepository userRepository;
  private final UserNftRepository userNftRepository;
  private final IPFSUtil ipfsUtil;
  private final JSONUtil jsonUtil;
  private final MusicRepository musicRepository;
  private final Web3jContractProvider web3jContractProvider;
  @Override
  public HasMintedResponseDto hasMinted(long musicId) {
    long userId = this.userService.getUserId();
    List<NFT> nft = this.nftRepository.findAllByUser_idAndMusic_id(userId,
        musicId);
    List<String> tx_ids = nft.stream().map(NFT::getContractAddress).toList();

    return HasMintedResponseDto.from(tx_ids);
  }

  @Override
  public UploadMetadataResponseDto uploadMeta(long musicId) throws IOException, AuthException {
    long userId = this.userService.getUserId();

    Music music = this.musicRepository.findById(musicId).orElseThrow(() -> new NotFoundException(
        MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));

    JSONObject copyrightInfo = this.jsonUtil
        .byteArrayToJSON(this.ipfsUtil.retrieveFile(music.getCid2()));
    JSONObject payProperty = this.jsonUtil.getChildJSONFromSuperJSON(copyrightInfo, "payProperty");
    JSONArray holders = this.jsonUtil.getJSONArrayFromSuperJSON(payProperty, "rightHolders");

    JSONObject uploader = null;
    for (int i = 0; i < holders.length(); i++) {
      JSONObject holder = holders.getJSONObject(i);
      if (userId == holder.getLong("userId")) {
        uploader = holder;
        break;
      }
    }
    if (uploader == null) {
      throw new AuthException(AuthResponseMessage.NO_GRANT_USER);
    }
    int holderNo = -1;
    for (int i = 0; i < holders.length(); i++) {
      JSONObject holder = holders.getJSONObject(i);
      if (userId == holder.getLong("userId")) {
        holderNo = i + 1;
        break;
      }
    }

    JSONObject mainContent = new JSONObject();
    mainContent.put("cid1", music.getCid1());
    mainContent.put("cid2", music.getCid2());
    JSONObject meta = new JSONObject();
    meta.put("name", holderNo + "/" + holders.length());
    meta.put("minter", uploader.getString("walletAddress"));
    meta.put("mainContent", mainContent);
    meta.put("proportion", uploader.getFloat("proportion") * 100);
    meta.put("supply", 1);

    String cid = this.ipfsUtil.addMetadataFileOnIPFS(meta);

    return UploadMetadataResponseDto.from(cid);
  }

  @Override
  public CreateNftResponseDto createNFT(CreateNFTRequestDto dto) {
    long userId = this.userService.getUserId();
    User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
        UserResponseMessage.SEARCH_USER_FAIL));
    Music music = this.musicRepository.findById(dto.getMusicId())
        .orElseThrow(() -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));
    NFT nft = this.nftRepository.save(NFT.of(dto, user, music));
    UserNFT userNFT = this.userNftRepository.save(UserNFT.of(nft, false));

    return CreateNftResponseDto.from(userNFT);
  }

  @Override
  public SellNFTResponseDto sellNFT(long musicId, String tx_id) throws AuthException {
    long userId = this.userService.getUserId();
    NFT nft = this.nftRepository.findByMusic_Id(musicId);

    this.userNftRepository.findByUser_IdAndNft_Id(userId, nft.getId())
        .orElseThrow(() -> new AuthException(AuthResponseMessage.NO_GRANT_USER));

    UserNFT updateUserNFT = this.userNftRepository.findByNft_Id(nft.getId())
        .orElseThrow(() -> new AuthException(AuthResponseMessage.NO_GRANT_USER));
    updateUserNFT.updateTxId(tx_id);

    return SellNFTResponseDto.from(musicId);
  }

  @Override
  public PurchaseNFTResponseDto purchaseNFT(long musicId, String tx_id)
      throws IOException, AuthException {
    long userId = this.userService.getUserId();
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL));
    TransactionReceipt transactionReceiptByHash = this.web3jContractProvider.getTransactionReceiptByHash(
        tx_id);
    NFT nft = this.nftRepository.findByMusic_IdAndTx_id(musicId, tx_id)
        .orElseThrow(() -> new AuthException(AuthResponseMessage.NO_GRANT_USER));
    UserNFT userNFT = this.userNftRepository.findByNft_Id(nft.getId())
        .orElseThrow(() -> new AuthException(AuthResponseMessage.NO_GRANT_USER));

    userNFT.updateUserIdTxId(user, tx_id);

    return PurchaseNFTResponseDto.from(userNFT);
  }

  @Override
  public List<NFT> retrieveNFTList(long musicId) {
    return this.musicRepository.findById(musicId).isPresent() ? this.nftRepository.findNFTsForSaleByMusicId(musicId)
        : this.nftRepository.findAllNFTsForSale();
  }

  @Override
  public List<Object[]> retrieveMyNFT() {
    return this.nftRepository.findAllForSaleByUserId(this.userService.getUserId());
  }

  @Override
  public NFT retrieveNFTById(long id) {
    return this.nftRepository.findById(id).orElseThrow(
        () -> new NotFoundException(NftResponseMessage.FOUND_NFT_FAIL)
    );
  }
}
