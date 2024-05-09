package lab.uxm.blockchain_re.domains.user.service;

import lab.uxm.blockchain_re.domains.user.dto.user.SearchUserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserInfoUpdateRequestDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserUpdateDto;

public interface UserService {
  public UserInfoDto findUserInfo();
  public UserUpdateDto updateUserInfo(UserInfoUpdateRequestDto dto);

  public SearchUserInfoDto searchUser(String email);
}
