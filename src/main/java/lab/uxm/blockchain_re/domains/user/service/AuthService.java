package lab.uxm.blockchain_re.domains.user.service;

import lab.uxm.blockchain_re.domains.user.dto.auth.LoginRequestDto;
import lab.uxm.blockchain_re.domains.user.dto.auth.SignUpRequestDto;

public interface AuthService {
  public String signIn(LoginRequestDto dto);
  public long join(SignUpRequestDto dto);
}
