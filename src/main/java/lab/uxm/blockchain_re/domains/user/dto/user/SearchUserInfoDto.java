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
@Schema(title = "USER_RES_03 : 유저 검색 응답 DTO")
public class SearchUserInfoDto {
  private long id;
  private String nickname;
  public static SearchUserInfoDto from(User user){
    return SearchUserInfoDto.builder()
        .id(user.getId())
        .nickname(user.getNickname())
        .build();
  }
}
