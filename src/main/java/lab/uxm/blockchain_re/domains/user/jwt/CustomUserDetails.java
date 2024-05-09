package lab.uxm.blockchain_re.domains.user.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lab.uxm.blockchain_re.domains.user.dto.auth.CustomUserInfoDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final CustomUserInfoDto user;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> types = new ArrayList<>();
    types.add("TYPE_"+ user.getType().toString());

    return types.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getId().toString();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
