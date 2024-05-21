package lab.uxm.blockchain_re.domains.music.dto.music;

import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveMusicInfoDto {
  private long id;
  private String title;
  private Long artistId;
  private String artist;
  private String album;
  private String image;
  private String lyrics;
  private Genre genre;
  private JSONArray composerId;
  private List<String> composer;
  private JSONArray songWriterId;
  private List<String> songWriter;
  public static RetrieveMusicInfoDto of(
      Music m,
      JSONObject songInfo,
      String image,
      List<String> composer,
      List<String> songWriter
  ){
    return RetrieveMusicInfoDto.builder()
        .id(m.getId())
        .title(m.getTitle())
        .artistId(songInfo.getLong("artistId"))
        .artist(m.getArtist())
        .album(songInfo.getString("album"))
        .image(image)
        .lyrics(songInfo.getString("lyrics"))
        .genre(m.getGenre())
        .composerId(songInfo.getJSONArray("composerId"))
        .composer(composer)
        .songWriterId(songInfo.getJSONArray("songWriter Id"))
        .songWriter(songWriter)
        .build();
  }
}
