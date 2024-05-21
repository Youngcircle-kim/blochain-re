package lab.uxm.blockchain_re.domains.music.controller;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
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
import lab.uxm.blockchain_re.domains.music.message.MusicResponseMessage;
import lab.uxm.blockchain_re.domains.music.message.UploadResponseMessage;
import lab.uxm.blockchain_re.domains.music.service.upload.UploadService;
import lab.uxm.blockchain_re.domains.user.message.UserResponseMessage;
import lab.uxm.blockchain_re.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix}/upload")
public class UploadController {

  private final UploadService uploadService;
  @GetMapping("")
  public ResponseEntity searchUploadedMusic() throws IOException {
    List<RetrieveMusicInfoResponseDto> retrieveMusicInfoResponseDtos = this.uploadService.retrieveMusicInfo();
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            MusicResponseMessage.SEARCH_MUSIC_INFO_SUCCESS,
            retrieveMusicInfoResponseDtos
        ),
        HttpStatus.OK
    );
  }

  @PostMapping("")
  public ResponseEntity uploadMusic(
     final @Valid UploadMusicRequestDto dto
  ) throws Exception {
    UploadMusicResponseDto uploadMusicResponseDto = this.uploadService.uploadMusic(dto);
    return new ResponseEntity(
      ResponseData.res(
          HttpStatus.OK.value(),
          UploadResponseMessage.UPLOAD_MUSIC_SUCCESS,
          uploadMusicResponseDto
      ),
        HttpStatus.OK
    );
  }

  @PostMapping("/check")
  public ResponseEntity checkMusic(
      final CheckDuplicatedMusicRequestDto dto
  ) throws IOException, NoSuchAlgorithmException {
    CheckDuplicatedMusicResponseDto checkDuplicatedMusicResponseDto = this.uploadService.checkMusic(
        dto);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            UploadResponseMessage.CHECK_DUPLICATED_MUSIC_SUCCESS,
            checkDuplicatedMusicResponseDto
        ),
        HttpStatus.OK
    );
  }

  @PostMapping("/meta")
  public ResponseEntity uploadMetadata(
      final @Valid UploadMetadataRequestDto dto
  ) throws IOException {
    UploadMetadataResponseDto uploadMetadataResponseDto = this.uploadService.uploadMetadata(dto);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            UploadResponseMessage.UPLOAD_MUSIC_METADATA_SUCCESS,
            uploadMetadataResponseDto
        ),
        HttpStatus.OK
    );
  }


  @DeleteMapping("/{id}")
  public ResponseEntity deleteUploadedMusic(
      final @PathVariable @Valid Long id
  ) throws AuthException {
    DeleteMusicResponseDto deleteMusicResponseDto = this.uploadService.deleteMusic(id);
    return new ResponseEntity(
        ResponseData.res(
            HttpStatus.OK.value(),
            UploadResponseMessage.DELETE_MUSIC_SUCCESS,
            deleteMusicResponseDto
        ),
        HttpStatus.OK
    );
  }
}
