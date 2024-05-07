package lab.uxm.blockchain_re.domains.user.service;

import lab.uxm.blockchain_re.domains.user.dto.LoginRequestDto;
import lab.uxm.blockchain_re.domains.user.dto.SignUpRequestDto;

public interface AuthService {
  public String signIn(LoginRequestDto dto);
  public long join(SignUpRequestDto dto);
}
