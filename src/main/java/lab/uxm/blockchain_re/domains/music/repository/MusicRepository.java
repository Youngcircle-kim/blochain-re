package lab.uxm.blockchain_re.domains.music.repository;

import java.util.List;
import java.util.Optional;
import lab.uxm.blockchain_re.constant.enums.Genre;
import lab.uxm.blockchain_re.domains.music.entity.Music;
import lab.uxm.blockchain_re.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
  List<Music> findAllByUser_id(Long id);
  List<Music> findAllByTitleOrArtist(String title, String artist);
  void deleteMusicById(long id);
  List<Music> findAllByGenre(Genre genre);
  boolean existsBySha1(String sha1);
  Optional<Music> findByCid1(String cid1);
  boolean existsById(long id);
}
