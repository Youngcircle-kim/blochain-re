package lab.uxm.blockchain_re.domains.purchase.service;

import jakarta.security.auth.message.AuthException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lab.uxm.blockchain_re.contract.SettlementContractExtra;
import lab.uxm.blockchain_re.contract.SettlementContractExtra.LogBuyerInfoEventResponse;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.music.message.MusicResponseMessage;
import lab.uxm.blockchain_re.domains.music.repository.MusicRepository;
import lab.uxm.blockchain_re.domains.purchase.dto.BuyMusicResponseDto;
import lab.uxm.blockchain_re.domains.purchase.dto.DownloadMusicResponseDto;
import lab.uxm.blockchain_re.domains.purchase.dto.RetrievePurchaseHistoryResponseDto;
import lab.uxm.blockchain_re.domains.purchase.entity.Purchase;
import lab.uxm.blockchain_re.domains.purchase.repository.PurchaseRepository;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lab.uxm.blockchain_re.domains.user.message.AuthResponseMessage;
import lab.uxm.blockchain_re.domains.user.message.UserResponseMessage;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lab.uxm.blockchain_re.domains.user.service.UserServiceImpl;
import lab.uxm.blockchain_re.provider.Web3jContractProvider;
import lab.uxm.blockchain_re.utils.IPFSUtil;
import lab.uxm.blockchain_re.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService{
  private final IPFSUtil ipfsUtil;
  private final JSONUtil jsonUtil;
  private final PurchaseRepository purchaseRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final Web3jContractProvider web3jContractProvider;
  private final UserServiceImpl userService;

  @Override
  public BuyMusicResponseDto buyMusic(long musicId, String hash) throws IOException, AuthException {
    long userId = this.userService.getUserId();
    this.musicRepository.findById(musicId)
        .orElseThrow(() -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));

    TransactionReceipt receipt
        = this.web3jContractProvider.getTransactionReceiptByHash(hash);

    LogBuyerInfoEventResponse logs = SettlementContractExtra
        .getLogBuyerInfoEventFromLog(receipt.getLogs().get(0));

    String buyer = logs.buyer;
    List<byte[]> songCidList = logs.songCid;

    StringBuilder songCid = new StringBuilder();
    for (byte[] sc : songCidList){
      songCid.append(new String(sc, StandardCharsets.UTF_8));
    }
    String cid1 = songCid.toString();

    User buyerUser = this.userRepository.findByWallet(buyer)
        .orElseThrow(() -> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL));
    Music boughtMusic = this.musicRepository.findByCid1(cid1)
        .orElseThrow(() -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));

    if (userId != buyerUser.getId() || musicId != boughtMusic.getId()){
      throw new AuthException(AuthResponseMessage.NO_GRANT_USER);
    }

    Purchase purchase = this.purchaseRepository.save(Purchase.of(buyerUser, boughtMusic));
    return BuyMusicResponseDto.from(purchase);
  }

  @Override
  public DownloadMusicResponseDto downloadMusic(long musicId, String token) throws Exception {
    long userId = this.userService.getUserId();
    Music music = this.musicRepository.findById(musicId)
        .orElseThrow(() -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));
    if (!this.userRepository.existsById(userId) ||
        !this.purchaseRepository.existsByUser_IdAndMusic_Id(userId, musicId)){
      throw new AuthException(AuthResponseMessage.NO_GRANT_USER);
    }
    String filename = music.getArtist() + "-" + music.getTitle()+".mp3";
    byte[] file = this.ipfsUtil.downloadFile(music);

    return DownloadMusicResponseDto.of(file, filename);
  }

  @Override
  public List<RetrievePurchaseHistoryResponseDto> retrievePurchaseHistory() throws IOException {
    long userId = this.userService.getUserId();
    List<Purchase> purchases = this.purchaseRepository.findAllByUser_Id(userId);
    List<RetrievePurchaseHistoryResponseDto> lst = new ArrayList<>();

    for(int i = 0; i < purchases.size(); i++){
      Music music = this.musicRepository.findById(purchases.get(i).getMusic().getId())
          .orElseThrow(() -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));
      byte[] file = this.ipfsUtil.retrieveFile(music.getCid1());
      JSONObject songInfo = this.jsonUtil
          .getChildJSONFromSuperJSON(this.jsonUtil.byteArrayToJSON(file)
              ,"songInfo");
      String album = this.jsonUtil.getStringFromJSON(songInfo, "album");

      String image = this.ipfsUtil.findImageCid(music.getCid1());
      lst.add(RetrievePurchaseHistoryResponseDto.of(music, album, image));
    }

    return lst;
  }
}
