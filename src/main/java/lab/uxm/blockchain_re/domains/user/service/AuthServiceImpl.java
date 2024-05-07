package lab.uxm.blockchain_re.domains.user.service;

import java.util.Optional;
import lab.uxm.blockchain_re.domains.user.dto.CustomUserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.LoginRequestDto;
import lab.uxm.blockchain_re.domains.user.dto.SignUpRequestDto;
import lab.uxm.blockchain_re.domains.user.entity.User;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lab.uxm.blockchain_re.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final ModelMapper modelMapper;

  @Override
  public String signIn(LoginRequestDto dto) {
    String email = dto.getEmail();
    String password = dto.getPassword();
    Optional<User> optionalUser = userRepository.findUserByEmail(email);
    if (optionalUser.isEmpty()){
      throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
    }
    User user = optionalUser.get();
    if (!encoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }
    CustomUserInfoDto info = modelMapper.map(user, CustomUserInfoDto.class);

    return jwtUtil.createAccessToken(info);
  }

  @Override
  public long join(SignUpRequestDto dto) {
    String email = dto.getEmail();
    Optional<User> user = userRepository.findUserByEmail(email);

    if (user.isPresent()){
      throw new IllegalArgumentException("이미 존재하는 유저입니다.");
    }
    User newUser = SignUpRequestDto.toEntity(dto);
    newUser.encodePassword(encoder);
    User savedUser = userRepository.save(newUser);
    return savedUser.getId();
  }
}
