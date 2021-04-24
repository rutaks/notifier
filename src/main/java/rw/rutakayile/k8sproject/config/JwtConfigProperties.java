package rw.rutakayile.k8sproject.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.secret")
public class JwtConfigProperties {
  private int time;
  private String key;
}
