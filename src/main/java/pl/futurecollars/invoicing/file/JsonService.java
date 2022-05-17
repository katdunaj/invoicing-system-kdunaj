package pl.futurecollars.invoicing.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JsonService<T> {

  private final ObjectMapper objectMapper;

  @Nullable
  public String convertToJson(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Nullable
  public T convertToObject(String line, Class<T> t) {
    try {
      return objectMapper.readValue(line, t);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}