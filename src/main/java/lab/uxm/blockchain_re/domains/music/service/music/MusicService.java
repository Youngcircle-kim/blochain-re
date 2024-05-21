package lab.uxm.blockchain_re.domains.music.service.music;

import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.dto.music.RetrieveMusicChartResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.music.RetrieveMusicInfoDto;
import lab.uxm.blockchain_re.domains.music.dto.music.SearchMusicInfoDto;

public interface MusicService {
  public List<SearchMusicInfoDto> searchMusic(String search) throws IOException;
  public List<RetrieveMusicChartResponseDto> retrieveChart(Genre genre) throws IOException;
  public RetrieveMusicInfoDto retrieveInfo(long musicId) throws IOException;
}
