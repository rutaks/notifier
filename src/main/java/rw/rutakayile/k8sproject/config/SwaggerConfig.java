package rw.rutakayile.k8sproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .components(
            new Components()
                .addSecuritySchemes(
                    "api-key",
                    new SecurityScheme()
                        .description("Please remember to start the token with 'Bearer '")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name(AUTHORIZATION_HEADER)))
        .info(
            new Info()
                .title("K8 Project")
                .version("v1")
                .description("The main API of the K8s project")
                .termsOfService("")
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")));
  }
}
