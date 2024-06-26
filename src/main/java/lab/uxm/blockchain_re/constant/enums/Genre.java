package lab.uxm.blockchain_re.constant.enums;

import lombok.Getter;

@Getter
public enum Genre {
  All("All", "0") ,
  RAndB("R&B", "1"),
  HipHop("Hip-Hop", "2"),
  Ballad("Ballad", "3"),
  Pop("POP", "4"),
  Jazz("Jazz","5"),
  Rock("Rock", "6");
  private String genre;
  private String code;

  private Genre(String genre, String code){
    this.genre = genre;
    this.code = code;
  }
}
