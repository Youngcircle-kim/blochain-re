package lab.uxm.blockchain_re.domains.user.dto.auth;

import lab.uxm.blockchain_re.constant.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends UserDto {
  private Long id;

  private String email;

  private Type type;

  private String password;

  private String nickname;

  private String wallet;
}
