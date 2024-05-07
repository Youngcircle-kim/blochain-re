package lab.uxm.blockchain_re.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.sql.Date;
import java.time.ZonedDateTime;
import lab.uxm.blockchain_re.domains.user.dto.CustomUserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * [This class provide method about JWT util]
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
   * Creating Access Token
   * @param user
   * @return Access Token String
   * */
  public String createAccessToken(CustomUserInfoDto user){
    return createToken(user, accessTokenExpTime);
  }

  /**
   * Creating JWT
   * @param user
   * @param expireTime
   * @return JWT String
   * */
  private String createToken(CustomUserInfoDto user, long expireTime){
    Claims claims = Jwts.claims();
    claims.put("userId", user.getId());
    claims.put("email", user.getEmail());
    claims.put("type", user.getType());

    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(Date.from(tokenValidity.toInstant()))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
  /**
   * Extracting user id from Token
   * @param token
   * @return User ID
   * */
  public Long getUserId(String token){
    return parseClaims(token).get("userId", Long.class);
  }

  /**
   * Extracting JWT Claims
   * @param accessToken
   * @return JWT Claims
   * */
  public Claims parseClaims(String accessToken){
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(accessToken)
          .getBody();
    } catch (ExpiredJwtException e){
      return e.getClaims();
    }
  }

  /**
   * Validation JWT
   * @param token
   * @return IsValidate
   * */
  public boolean validateToken(String token){
    try{
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
      log.info("Invalid JWT token", e);
    } catch (ExpiredJwtException e){
      log.info("Expired JWT Token", e);
    } catch(UnsupportedJwtException e){
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e){
      log.info("JWT claims string is empty", e);
    }
    return false;
  }
}
