package lab.uxm.blockchain_re.domains.user.service;

import lab.uxm.blockchain_re.domains.user.dto.LoginRequestDto;

public interface AuthService {
  public String signIn(LoginRequestDto dto);
}
