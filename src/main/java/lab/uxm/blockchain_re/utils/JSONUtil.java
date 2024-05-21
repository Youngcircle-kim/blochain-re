package lab.uxm.blockchain_re.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Slf4j
@RequiredArgsConstructor
@Component
public class JSONUtil {
  /**
   * This method make byte array to JSON object
   * @param byteArray byte[]
   * @return json JSONObject
   * */
  public JSONObject byteArrayToJSON(byte[] byteArray){
    return new JSONObject(new String(byteArray, StandardCharsets.UTF_8));
  }

  /**
   * This method get JSON object from super JSON object
   * @param superJsonObject JSONObject
   * @param attr String
   * @return JSONObject
   * */
  public JSONObject getChildJSONFromSuperJSON(JSONObject superJsonObject, String attr){
    return superJsonObject.getJSONObject(attr);
  }

  /**
   * This method get JSON array from super JSON object
   * @param superJsonObject JSONObject
   * @param attr String
   * @return JSONArray
   * */
  public JSONArray getJSONArrayFromSuperJSON(JSONObject superJsonObject, String attr){
    return superJsonObject.getJSONArray(attr);
  }
}
