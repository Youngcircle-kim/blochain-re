package lab.uxm.blockchain_re.constant.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;
import lab.uxm.blockchain_re.constant.enums.Genre;
@Converter
public class GenreConverter implements AttributeConverter<Genre, String> {

  @Override
  public String convertToDatabaseColumn(final Genre genre) {
    if (genre == null){
      return null;
    }
    return genre.getGenre();
  }

  @Override
  public Genre convertToEntityAttribute(final String s) {
    if (s == null){
      return null;
    }
    return Stream.of(Genre.values())
        .filter(g -> g.getGenre().equals(s))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
