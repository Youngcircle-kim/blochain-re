package lab.uxm.blockchain_re.domains.user.controller;

import jakarta.validation.Valid;
import lab.uxm.blockchain_re.domains.user.dto.user.SearchUserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserInfoDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserInfoUpdateRequestDto;
import lab.uxm.blockchain_re.domains.user.dto.user.UserUpdateDto;
import lab.uxm.blockchain_re.domains.user.message.UserResponseMessage;
import lab.uxm.blockchain_re.domains.user.service.UserService;
import lab.uxm.blockchain_re.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
public class UserController {
  private final UserService userService;
  @GetMapping("/")
  public ResponseEntity findUserInfo(){
    UserInfoDto userInfo = this.userService.findUserInfo();
    return new ResponseEntity(
        ResponseData.res(HttpStatus.OK.value()
            , UserResponseMessage.FIND_USER_INFO_SUCCESS
            , userInfo)
        , HttpStatus.OK);
  }
  @PutMapping("/")
  public ResponseEntity updateUserInfo(
    @Valid @RequestBody UserInfoUpdateRequestDto dto
  ){
    UserUpdateDto userUpdateDto = this.userService.updateUserInfo(dto);
    return new ResponseEntity(
        ResponseData.res(HttpStatus.OK.value(),
            UserResponseMessage.UPDATING_USER_INFO_SUCCESS,
            userUpdateDto),
        HttpStatus.OK);

  }
  @GetMapping("")
  public ResponseEntity searchUser(
      @RequestParam String search
  ){
    SearchUserInfoDto searchUserInfoDto = this.userService.searchUser(search);
    return new ResponseEntity(
        ResponseData.res(HttpStatus.OK.value(),
            UserResponseMessage.SEARCH_USER_SUCCESS,
            searchUserInfoDto),
        HttpStatus.OK
    );
  }
}
