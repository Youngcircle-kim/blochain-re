package lab.uxm.blockchain_re.domains.music.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MusicMetadata {
  private long uploaderId;
  private String uploadTime;
  private SongInfo songInfo;

  public static MusicMetadata of(long userId, SongInfo songInfo ){
    return MusicMetadata.builder()
        .uploaderId(userId)
        .uploadTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
        .songInfo(songInfo)
        .build();
  }
}
