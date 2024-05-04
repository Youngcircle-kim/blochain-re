package lab.uxm.blockchain_re.utils;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * [This class provide method about JWT]
 */
@Slf4j
@Component
public class JwtUtil {
  private final Key key;
  private final long accessTokenExpTime;

  public JwtUtil(
      @Value("${jwt.secret}") String secretKey,
      @Value("${jwt.expiration_time}") long accessTokenExpTime
  ) {
    byte[] bytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(bytes);
    this.accessTokenExpTime = accessTokenExpTime;
  }

  /**
   * Generate Access Token
   * @param user
   * @return Access Token String
   * */
  //public String createAccessToken(){

  //}
}
