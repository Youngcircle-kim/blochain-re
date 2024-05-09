package lab.uxm.blockchain_re.config;

import lab.uxm.blockchain_re.domains.user.jwt.CustomUserDetailService;
import lab.uxm.blockchain_re.domains.user.jwt.JwtAuthFilter;
import lab.uxm.blockchain_re.exception.CustomAccessDeniedHandler;
import lab.uxm.blockchain_re.exception.CustomAuthenticationEntryPoint;
import lab.uxm.blockchain_re.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final CustomUserDetailService customUserDetailService;
  private final JwtUtil jwtUtil;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  private static final String[] AUTH_WHITELIST = {
      "api/v1/auth/**", "/api-docs/**", "/swagger-ui/**", "/swagger-ui-custom.html"
      ,"/swagger-ui.html"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //CSRF, CORS
    http.csrf((csrf) -> csrf.disable());
    http.cors(Customizer.withDefaults());

    // 세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 X
    http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS
    ));

    //Form login, Basic Http 비활성화
    http.formLogin((form) -> form.disable());
    http.httpBasic(AbstractHttpConfigurer::disable);

    //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
    http.addFilterBefore(new JwtAuthFilter(customUserDetailService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
    http.exceptionHandling(
        (exceptionHandling) -> exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler)
    );

    //권한 규칙 작성
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated()
    );
    return http.build();
  }
}
