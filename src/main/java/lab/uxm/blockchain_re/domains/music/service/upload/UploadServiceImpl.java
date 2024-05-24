package lab.uxm.blockchain_re.domains.music.service.upload;

import jakarta.security.auth.message.AuthException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Type;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.CheckDuplicatedMusicRequestDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.UploadMetadataRequestDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.UploadMusicRequestDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.CheckDuplicatedMusicResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.DeleteMusicResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.RetrieveMusicInfoResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.UploadMetadataResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.UploadMusicResponseDto;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.music.message.MusicResponseMessage;
import lab.uxm.blockchain_re.domains.music.model.MusicMetadata;
import lab.uxm.blockchain_re.domains.music.model.PayProperty;
import lab.uxm.blockchain_re.domains.music.model.RightHolder;
import lab.uxm.blockchain_re.domains.music.model.SongInfo;
import lab.uxm.blockchain_re.domains.music.repository.MusicRepository;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService{
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final IPFSUtil ipfsUtil;
  private final JSONUtil jsonUtil;
  private final Web3jContractProvider web3jContractProvider;
  private final UserServiceImpl userService;

  @Override
  public List<RetrieveMusicInfoResponseDto> retrieveMusicInfo() throws IOException {
    long userId = this.userService.getUserId();
    List<Music> musics = this.musicRepository.findAllByUser_id(userId);

    List<RetrieveMusicInfoResponseDto> retrieveMusicInfoResponses = musics.stream().map(
        music -> modelMapper.map(music, RetrieveMusicInfoResponseDto.class)
    ).toList();

    for (int i = 0; i < musics.size(); i++){
      // get image file
      byte[] imageByte = ipfsUtil.retrieveFile(musics.get(i).getCid3());
      String base64Image = Base64.getEncoder().encodeToString(imageByte);

      // get album title
      byte[] metadataByte = ipfsUtil.retrieveFile(musics.get(i).getCid1());
      JSONObject metadataJSON = jsonUtil.byteArrayToJSON(metadataByte);
      JSONObject songInfo = jsonUtil.getChildJSONFromSuperJSON(metadataJSON, "songInfo");
      String album = songInfo.getString("album");

      retrieveMusicInfoResponses.get(i).setImage(base64Image);
      retrieveMusicInfoResponses.get(i).setAlbum(album);
    }

    return retrieveMusicInfoResponses;
  }

  @Override
  public UploadMusicResponseDto uploadMusic(UploadMusicRequestDto dto)
      throws Exception {
    long userId = this.userService.getUserId();
    User user = this.userRepository.findUserById(userId)
        .orElseThrow(() -> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL));
    if (user.getType() != Type.Producer){
      throw new AuthException(AuthResponseMessage.NO_GRANT_USER);
    }

    List<Long> holder = dto.getHolder();
    List<RightHolder> rightHolders = new ArrayList<>();
    List<String> addresses = new ArrayList<>();
    List<Double> proportions = new ArrayList<>();

    for(long l : holder){
      if (this.userRepository.findById(l).isEmpty()) throw new AuthException(AuthResponseMessage.NO_GRANT_USER);

      User holderUser = this.userRepository.findById(l).get();
      rightHolders.add(RightHolder.builder()
          .userId(holderUser.getId())
          .walletAddress(holderUser.getWallet())
          .build());
    }

    for(int i = 0; i < rightHolders.size(); i++){
      RightHolder rightHolder = rightHolders.get(i);
      rightHolder.setProportion(dto.getRate().get(i));
      addresses.add(rightHolder.getWalletAddress());
      proportions.add(dto.getRate().get(i) * 10000);
    }

    byte[] buffer = dto.getFile().getBytes();
    String sha1 = this.ipfsUtil.sha1Convert(buffer);

    byte[] encrypted = this.ipfsUtil.encryptAES(this.ipfsUtil.compress(buffer));
    String cid3 = this.ipfsUtil.addFileOnIPFS(encrypted);

    Music music = Music.builder()
        .user(this.userRepository.findById(userId)
            .orElseThrow(
                ()-> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL)
            ))
        .title(dto.getTitle())
        .genre(dto.getGenre())
        .artist(dto.getArtist())
        .cid1(dto.getCid1())
        .cid2("")
        .cid3(cid3)
        .sha1(sha1)
        .address1(dto.getAddress())
        .build();

    Music saved = this.musicRepository.save(music);

    PayProperty copyright = PayProperty.builder()
        .songId(saved.getId())
        .rightHolders(rightHolders)
        .build();

    JSONArray rightHolder = new JSONArray(rightHolders);
    JSONObject payProperty = new JSONObject()
        .put("songId",saved.getId())
        .put("rightHolders", rightHolder);

    log.info("payment : {}", new JSONObject().put("payProperty", payProperty).toString());

    String cid2 = this.ipfsUtil.addCopyrightIPFS(new JSONObject().put("payProperty", payProperty).toString());
    updateCid2(music.getId(), cid2);

    return UploadMusicResponseDto.from(music);
  }

  @Override
  public UploadMetadataResponseDto uploadMetadata(UploadMetadataRequestDto dto) throws IOException {
    long userId = this.userService.getUserId();
    String imgCid = this.ipfsUtil.addImageFileOnIPFS(dto.getImage().getOriginalFilename(),
        dto.getImage().getBytes());

    SongInfo songInfo = SongInfo.of(dto, imgCid);
    MusicMetadata metadata = MusicMetadata.of(userId, songInfo);

    String cid = this.ipfsUtil.addMetadataFileOnIPFS(metadata);

    return new UploadMetadataResponseDto(cid);
  }

  @Override
  public DeleteMusicResponseDto deleteMusic(long musicId) throws AuthException {
    long userId = this.userService.getUserId();
    Music music = this.musicRepository.findById(musicId)
        .orElseThrow(
            () -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL)
        );
    if (userId == music.getUser().getId()){
      throw new AuthException(AuthResponseMessage.NO_GRANT_USER);
    }
    this.musicRepository.deleteMusicById(musicId);


    return new DeleteMusicResponseDto(musicId);
  }

  @Override
  public CheckDuplicatedMusicResponseDto checkMusic(CheckDuplicatedMusicRequestDto dto)
      throws IOException, NoSuchAlgorithmException {
    byte[] buffer = dto.getFile().getBytes();
    String sha1 = this.ipfsUtil.sha1Convert(buffer);
    boolean isDuplicated = this.musicRepository.existsBySha1(sha1);
    return new CheckDuplicatedMusicResponseDto(isDuplicated);
  }

  @Transactional(readOnly = true)
  public void updateCid2(Long id, String cid){
    Music music = this.musicRepository.findById(id)
        .orElseThrow(
            ()-> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_INFO_FAIL));
    music.setCid2(cid);
  }
}
