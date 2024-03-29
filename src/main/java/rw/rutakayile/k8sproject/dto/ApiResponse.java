package rw.rutakayile.k8sproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents a Generic API Response.
 *
 * @author Samuel Rutakayile
 * @version 1.0
 * @since 1.0
 */
@Data
public class ApiResponse<T> {
  private HttpStatus status;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;
  private T payload;

  public ApiResponse(HttpStatus status, String message, T payload) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.payload = payload;
  }

  public String convertToJson() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    return mapper.writeValueAsString(this);
  }

  @SneakyThrows
  @Override
  public String toString() {
    return convertToJson();
  }
}
