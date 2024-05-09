package lab.uxm.blockchain_re.domains.user.jwt;

import lab.uxm.blockchain_re.domains.user.dto.auth.CustomUserInfoDto;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findById(Long.parseLong(username))
        .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

    CustomUserInfoDto dto = modelMapper.map(user, CustomUserInfoDto.class);
    return new CustomUserDetails(dto);
  }
}
