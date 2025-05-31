package hse.group1.focusflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(@org.springframework.lang.NonNull CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:5173")
      .allowedMethods("*")
      .allowedHeaders("*")
      .allowCredentials(true);
  }
}
