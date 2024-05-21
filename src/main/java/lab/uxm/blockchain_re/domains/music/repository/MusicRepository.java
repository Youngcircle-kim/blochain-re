package lab.uxm.blockchain_re.domains.music.repository;

import java.util.List;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
  public List<Music> findAllByUser_id(Long id);
  public List<Music> findAllByTitleOrArtist(String title, String artist);
  public void deleteMusicById(long id);
  public List<Music> findAllByGenre(Genre genre);
  public boolean existsBySha1(String sha1);
}
