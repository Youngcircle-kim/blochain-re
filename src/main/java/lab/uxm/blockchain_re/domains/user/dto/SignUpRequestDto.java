package lab.uxm.blockchain_re.domains.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lab.uxm.blockchain_re.constant.enums.Type;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AUTH_REQ_02 : 회원가입 요청 DTO")
public class SignUpRequestDto {
  @NotNull(message = "이메일 입력은 필수입니다.")
  @Email
  private String email;

  @NotNull(message = "비밀번호 입력은 필수입니다.")
  private String password;

  @NotNull(message = "이름 입력은 필수입니다.")
  private String name;

  @NotNull(message = "권한 설정은 필수입니다.")
  private Type type;

  @NotNull(message = "가명 입력은 필수입니다.")
  private String nickname;

  @NotNull(message = "지갑 주소 입력은 필수입니다.")
  private String wallet;

  public static User toEntity(SignUpRequestDto dto){
    return User.builder()
        .email(dto.getEmail())
        .password(dto.getPassword())
        .name(dto.getName())
        .type(dto.getType())
        .nickname(dto.getNickname())
        .wallet(dto.getWallet())
        .build();
  }
}
