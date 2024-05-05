package lab.uxm.blockchain_re.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * [This class is global response format]
 * [including method that writing response]
 * */
@Data
@AllArgsConstructor
@Builder
public class ResponseData<T> {
  private int statusCode;
  private String responseMessage;
  private T data;

  public ResponseData(final int statusCode, final String responseMessage){
    this.statusCode = statusCode;
    this.responseMessage = responseMessage;
    this.data = null;
  }
  public static<T> ResponseData<T> res(final int statusCode, final String responseMessage){
    return res(statusCode, responseMessage, null);
  }
  public static<T>  ResponseData<T> res(final int statusCode, final String responseMessage, final T data){
      return ResponseData.<T>builder()
          .statusCode(statusCode)
          .responseMessage(responseMessage)
          .data(data)
          .build();
  }
}
