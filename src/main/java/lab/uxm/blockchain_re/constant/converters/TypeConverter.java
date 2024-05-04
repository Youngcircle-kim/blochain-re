package lab.uxm.blockchain_re.constant.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;
import lab.uxm.blockchain_re.constant.enums.Type;

@Converter
public class TypeConverter implements AttributeConverter<Type, String> {

  @Override
  public String convertToDatabaseColumn(final Type type) {
    if (type == null){
      return null;
    }
    return type.getType();
  }

  @Override
  public Type convertToEntityAttribute(final String typeString) {
    if (typeString == null){
      return null;
    }
    return Stream.of(Type.values())
        .filter(t -> t.getType().equals(typeString))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
