package lab.uxm.blockchain_re.domains.user.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "USER_REQ_01 : 유저 정보 수정 요청 DTO")
public class UserInfoUpdateRequestDto {
  @NotNull
  @Schema(description = "Default 값은 원래 이름.")
  private String name;

  @NotNull
  @Schema(description = "Default 값은 원래 가명.")
  private String nickname;

  @NotNull
  @Schema(description = "Default 값은 원래 비밀번호.")
  private String password;
}
