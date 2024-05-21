package lab.uxm.blockchain_re.domains.music.service.music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.dto.music.RetrieveMusicChartResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.music.RetrieveMusicInfoDto;
import lab.uxm.blockchain_re.domains.music.dto.music.SearchMusicInfoDto;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.music.message.MusicResponseMessage;
import lab.uxm.blockchain_re.domains.music.repository.MusicRepository;
import lab.uxm.blockchain_re.domains.user.message.UserResponseMessage;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lab.uxm.blockchain_re.utils.IPFSUtil;
import lab.uxm.blockchain_re.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
  private final UserRepository userRepository;
  private final MusicRepository musicRepository;
  private final IPFSUtil ipfsUtil;
  private final JSONUtil jsonUtil;
  @Override
  public List<SearchMusicInfoDto> searchMusic(String search) throws IOException {
    String[] words = search.split(" ");
    List<SearchMusicInfoDto> list = new ArrayList<>();

    for (String word : words){
      List<SearchMusicInfoDto> res = this.musicRepository.findAllByTitleOrArtist(word, word)
          .stream()
          .map(SearchMusicInfoDto::from)
          .toList();
      list.addAll(res);
    }
    if (list.isEmpty()) {
      throw new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_EMPTY);
    }
    for(SearchMusicInfoDto e :list){
      e.setImage(this.ipfsUtil.findImageCid(e.getImage()));
    }

    return list;
  }

  @Override
  public List<RetrieveMusicChartResponseDto> retrieveChart(Genre genre) throws IOException {
    List<Music> list;

    if (genre.getGenre().equals("All")){
      list = this.musicRepository.findAll();
    } else {
      list = this.musicRepository.findAllByGenre(genre);
    }
    List<RetrieveMusicChartResponseDto> res = new ArrayList<>();

    for (Music m : list){
      res.add(RetrieveMusicChartResponseDto.of(m, this.ipfsUtil.findImageCid(m.getCid1())));
    }
    return res;
  }

  @Override
  public RetrieveMusicInfoDto retrieveInfo(long musicId) throws IOException {
    Music music = this.musicRepository.findById(musicId)
        .orElseThrow(() -> new NotFoundException(MusicResponseMessage.SEARCH_MUSIC_EMPTY));

    byte[] cid1File = this.ipfsUtil.retrieveFile(music.getCid1());
    String image = this.ipfsUtil.findImageCid(music.getCid1());

    JSONObject songInfo = this.jsonUtil.byteArrayToJSON(cid1File).getJSONObject("songInfo");

    JSONArray composerIds = this.jsonUtil.getJSONArrayFromSuperJSON(songInfo, "composerId");
    JSONArray songWriterIds = this.jsonUtil.getJSONArrayFromSuperJSON(songInfo, "songWriterId");

    List<String> composerId = new ArrayList<>();
    List<String> songWriterId = new ArrayList<>();

    for (int i = 0; i < composerIds.length(); i++) {
      composerId.add(
          this.userRepository
              .findById(composerIds.getLong(i))
              .orElseThrow(() -> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL))
              .getName());
    }
    for (int i = 0; i < songWriterIds.length(); i++) {
      songWriterId.add(
          this.userRepository
              .findById(songWriterIds.getLong(i))
              .orElseThrow(() -> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL))
              .getName());
    }

    return RetrieveMusicInfoDto.of(music, songInfo, image, composerId, songWriterId);
  }
}
