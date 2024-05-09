package lab.uxm.blockchain_re.domains.user.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lab.uxm.blockchain_re.constant.enums.Type;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "USER_RES_01 : 회원정보 응답 DTO")
public class UserInfoDto {
  private long id;
  private String email;
  private String name;
  private Type type;
  private String nickname;
  private String wallet;

  public static UserInfoDto from(User user){
    return UserInfoDto
        .builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .type(user.getType())
        .nickname(user.getNickname())
        .wallet(user.getWallet())
        .build();
  }
}
