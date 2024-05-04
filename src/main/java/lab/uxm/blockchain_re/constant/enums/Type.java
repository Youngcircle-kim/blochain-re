package lab.uxm.blockchain_re.constant.enums;

import lombok.Getter;

@Getter
public enum Type {
  General("General", "0"),
  Producer("Producer", "1");

  private String type;
  private String code;

  private Type(String type, String code){
    this.type = type;
    this.code = code;
  }
}
