package lab.uxm.blockchain_re.domains.user.error;

import lab.uxm.blockchain_re.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

  INVALID_USER(HttpStatus.BAD_REQUEST, "Invalid user");

  private final HttpStatus status;
  private final String responseMessage;
}
