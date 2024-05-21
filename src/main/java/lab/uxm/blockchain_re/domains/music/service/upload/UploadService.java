package lab.uxm.blockchain_re.domains.music.service.upload;

import jakarta.security.auth.message.AuthException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.CheckDuplicatedMusicRequestDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.UploadMetadataRequestDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.request.UploadMusicRequestDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.CheckDuplicatedMusicResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.DeleteMusicResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.RetrieveMusicInfoResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.UploadMetadataResponseDto;
import lab.uxm.blockchain_re.domains.music.dto.upload.response.UploadMusicResponseDto;

public interface UploadService {
  public List<RetrieveMusicInfoResponseDto> retrieveMusicInfo() throws IOException;
  public UploadMusicResponseDto uploadMusic(UploadMusicRequestDto dto) throws Exception;
  public UploadMetadataResponseDto uploadMetadata(UploadMetadataRequestDto dto) throws IOException;
  public DeleteMusicResponseDto deleteMusic(long id) throws AuthException;
  public CheckDuplicatedMusicResponseDto checkMusic(CheckDuplicatedMusicRequestDto dto)
      throws IOException, NoSuchAlgorithmException;
}
