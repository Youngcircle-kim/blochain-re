package lab.uxm.blockchain_re.exception;

import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import java.util.Objects;
import lab.uxm.blockchain_re.error.ErrorResponseDto;
import lab.uxm.blockchain_re.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  public static final String TRACE = "trace";

  @Value("${error.printStackTrace}")
  private boolean printStackTrace;

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest req
      ){
    return buildErrorResponse(ex, ex.getMessage(), HttpStatus.valueOf(statusCode.value()), req);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception,
      String message,
      HttpStatus httpStatus,
      WebRequest req
  ){
    ErrorResponseDto errorResponseDto =new ErrorResponseDto(httpStatus.value(), message, LocalDateTime.now());
    if(printStackTrace && isTraceOn(req)){
      errorResponseDto.setStackTrace(ExceptionUtils.getStackTrace(exception));
    }
    return new ResponseEntity(ResponseData.res(httpStatus.value(), message), httpStatus);
  }
  private  boolean isTraceOn(WebRequest req){
    String[] value = req.getParameterValues(TRACE);
    return Objects.nonNull(value)
        && value.length > 0
        && value[0].contentEquals("true");
  }
  @Override
  @Hidden
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest req
  ){
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Validation error. Check 'errors' field for details.", LocalDateTime.now());
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
      errorResponseDto.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return ResponseEntity.unprocessableEntity().body(errorResponseDto);
  }
  @ExceptionHandler(NotFoundException.class)
  @Hidden
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleNotFoundException(
      NotFoundException ex,
      WebRequest req
  ){
    log.error(ex.getMessage());
    return buildErrorResponse(ex, ex.getMessage(), HttpStatus.BAD_REQUEST, req);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  @Hidden
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleUsernameNotFoundException(
    UsernameNotFoundException ex,
      WebRequest req
  ){
   log.error("이메일이 존재하지 않습니다.", ex);
   return buildErrorResponse(ex, ex.getMessage(), HttpStatus.BAD_REQUEST, req);
  }

  @ExceptionHandler(BadCredentialsException.class)
  @Hidden
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleBadCredentialsException(
      BadCredentialsException ex,
      WebRequest req
  ){
    log.error("비밀번호가 일치하지 않습니다.", ex);
    return buildErrorResponse(ex, ex.getMessage(), HttpStatus.BAD_REQUEST, req);
  }

  @ExceptionHandler(Exception.class)
  @Hidden
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleAllCaughtException(
      Exception ex,
      WebRequest req
  ){
    log.error("Internal error occurred", ex);
    return buildErrorResponse(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, req);
  }
}
