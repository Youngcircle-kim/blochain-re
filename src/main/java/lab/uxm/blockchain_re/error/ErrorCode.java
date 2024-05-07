package lab.uxm.blockchain_re.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  String name();

  HttpStatus getStatus();
  String  getResponseMessage();
}
