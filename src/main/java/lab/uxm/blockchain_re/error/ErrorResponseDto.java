package lab.uxm.blockchain_re.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

  private final int statusCode;
  private final String responseMessage;
  private final LocalDateTime time;
  private String stackTrace;
  private List<ValidationError> validErrors;

  @Data
  @RequiredArgsConstructor
  private static class ValidationError{
    private final String field;
    private final String message;
  }

  public void addValidationError(String field, String message){
    if (Objects.isNull(validErrors)){
      validErrors = new ArrayList<>();
    }
    validErrors.add(new ValidationError(field, message));
  }
}
