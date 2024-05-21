package lab.uxm.blockchain_re.domains.music.controller;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.dto.music.RetrieveMusicChartResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.music.RetrieveMusicInfoDto;
import lab.uxm.blockchain_re.domains.music.dto.music.SearchMusicInfoDto;
import lab.uxm.blockchain_re.domains.music.message.MusicResponseMessage;
import lab.uxm.blockchain_re.domains.music.service.music.MusicService;
import lab.uxm.blockchain_re.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/music")
public class MusicController {
  private final MusicService musicService;

  @GetMapping("")
  public ResponseEntity searchMusic(
      @Valid @RequestParam String search
  ) throws IOException {
    List<SearchMusicInfoDto> list = this.musicService.searchMusic(search);
    return new ResponseEntity<>(
        ResponseData.res(
            HttpStatus.OK.value(),
            MusicResponseMessage.SEARCH_MUSIC_INFO_SUCCESS,
            list
        ),
        HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity musicInfo(
      @Valid @PathVariable long id
  ) throws IOException {
    RetrieveMusicInfoDto retrieveMusicInfoDto = this.musicService.retrieveInfo(id);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            MusicResponseMessage.RETRIEVE_MUSIC_INFO_SUCCESS,
            retrieveMusicInfoDto
        ),
        HttpStatus.OK
    );
  }

  @GetMapping("/")
  public ResponseEntity musicChart(
      @Valid @RequestParam Genre genre
  ) throws IOException {
    List<RetrieveMusicChartResponseDto> responseDtos = this.musicService.retrieveChart(genre);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            MusicResponseMessage.RETRIEVE_MUSIC_CHART_SUCCESS,
            responseDtos
        ),
        HttpStatus.OK
    );
  }
}
