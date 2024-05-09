package lab.uxm.blockchain_re.domains.user.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "USER_RES_02 : 회원정보 수정 응답 DTO")
public class UserUpdateDto {
  private long id;
  private String name;
  private String nickname;

  public static UserUpdateDto from(User user){
    return UserUpdateDto.builder()
        .id(user.getId())
        .name(user.getName())
        .nickname(user.getNickname())
        .build();
  }
}
