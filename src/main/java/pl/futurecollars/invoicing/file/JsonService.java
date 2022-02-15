package pl.futurecollars.invoicing.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonService<T> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public JsonService() {
    objectMapper.findAndRegisterModules();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public String convertToJson(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public T convertToObject(String line, Class<T> t) {
    try {
      return objectMapper.readValue(line, t);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }
}