package lab.uxm.blockchain_re.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(
    info = @Info(
        title = "UXM Blockchain API Docs",
        description = "기존 Smart Contract와 NFT API Doc을 하나로 합친 버전.",
        version = "1.0.0"
    )
)
@Configuration
public class SwaggerConfig {
  private static final String BEARER_TOKEN_PREFIX="Bearer";
  @Bean
  public OpenAPI openAPI(){
    String securityJwtName = "JWT";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
    Components components = new Components()
        .addSecuritySchemes(securityJwtName, new SecurityScheme()
            .name(securityJwtName)
            .type(Type.HTTP)
            .scheme(BEARER_TOKEN_PREFIX)
            .bearerFormat(securityJwtName)
        );
    return new OpenAPI()
        .addSecurityItem(securityRequirement)
        .components(components);
  }
}
