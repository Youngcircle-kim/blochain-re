package lab.uxm.blockchain_re.domains.user.controller;

import jakarta.validation.Valid;
import lab.uxm.blockchain_re.domains.user.dto.LoginRequestDto;
import lab.uxm.blockchain_re.domains.user.message.AuthResponseMessage;
import lab.uxm.blockchain_re.domains.user.service.AuthService;
import lab.uxm.blockchain_re.response.ResponseData;
import lab.uxm.blockchain_re.response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthService authService;


  @PostMapping("signin")
  public ResponseEntity postSignIn(
      @Valid @RequestBody LoginRequestDto req
  ){
    String token = this.authService.signIn(req);
    return new ResponseEntity(
        ResponseData
            .res(StatusCode.OK, AuthResponseMessage.SIGN_IN_SUCCESS, token),
        HttpStatus.OK
    );
  }
}
