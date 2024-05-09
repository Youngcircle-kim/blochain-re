package lab.uxm.blockchain_re.domains.user.service;

import java.util.Optional;
import lab.uxm.blockchain_re.domains.user.dto.user.SearchUserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserInfoUpdateRequestDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserUpdateDto;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lab.uxm.blockchain_re.domains.user.message.UserResponseMessage;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private  final PasswordEncoder passwordEncoder;

  @Override
  public UserInfoDto findUserInfo() {
    long userId = getUserId();
    Optional<User> userById = userRepository.findUserById(userId);
    if (userById.isEmpty()){
      throw new NotFoundException(UserResponseMessage.NOT_FOUND_USER);
    }
    return UserInfoDto.from(userById.get());
  }

  @Override
  public UserUpdateDto updateUserInfo(UserInfoUpdateRequestDto dto) {
    long userId = getUserId();
    Optional<User> userById = userRepository.findUserById(userId);
    if (userById.isEmpty()){
      throw new NotFoundException(UserResponseMessage.NOT_FOUND_USER);
    }
    userById.get().modifyInfo(dto, passwordEncoder);

    return UserUpdateDto.from(userById.get());
  }

  @Override
  public SearchUserInfoDto searchUser(String search) {
    User user = this.userRepository.findUserByEmail(search)
        .orElseThrow(() -> new NotFoundException(UserResponseMessage.SEARCH_USER_FAIL));

    return SearchUserInfoDto.from(user);
  }

  private long getUserId(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return Long.parseLong(authentication.getName());
  }
}
