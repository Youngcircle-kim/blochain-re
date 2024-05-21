package lab.uxm.blockchain_re.domains.music.model;

import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.UploadMetadataRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SongInfo {
  private String title;
  private long artistId;
  private String artist;
  private String album;
  private String imageCid;
  private Genre genre;
  private String lyrics;
  private List<Long> composerId;
  private List<Long> songWriterId;

  public static SongInfo of(UploadMetadataRequestDto dto, String imgCid){
    return SongInfo.builder()
        .title(dto.getTitle())
        .artistId(dto.getArtistId())
        .artist(dto.getArtist())
        .album(dto.getAlbum())
        .imageCid(imgCid)
        .genre(dto.getGenre())
        .lyrics(dto.getLyrics())
        .composerId(dto.getComposerId())
        .songWriterId(dto.getSongWriterId())
        .build();
  }
}

