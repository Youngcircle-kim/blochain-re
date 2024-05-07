package lab.uxm.blockchain_re.domains.user.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lab.uxm.blockchain_re.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final CustomUserDetailService customUserDetailService;
  private final JwtUtil jwtUtil;
  @Override
  /**
   * JWT 토큰 검증 필터
   * */
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");

    // JWT가 헤더에 있는 경우
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
      String token = authorizationHeader.substring(7);

      // JWT 유효성 검증
      if (jwtUtil.validateToken(token)){
        Long userId = jwtUtil.getUserId(token);

        //유저의 토큰 일치 시 userDetails 생성
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userId.toString());

        if (userDetails != null){
          //UserDetails, Password, Role -> 접근 권한 인증 Token 생성
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());

          // 현재 Request의 Security Context에 접근 권한 설정
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }

    filterChain.doFilter(request, response); // next filter
  }
}
