package com.charter.commons.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {
  private final BuildProperties buildProperties;

  /**
   * Constructor for OpenApiConfig.
   *
   * @param buildProperties a {@link org.springframework.boot.info.BuildProperties} object
   */
  public OpenApiConfig(BuildProperties buildProperties) {
    this.buildProperties = buildProperties;
  }

  /**
   * openApi.
   *
   * @return a {@link io.swagger.v3.oas.models.OpenAPI} object
   */
  @Bean
  public OpenAPI openApi() {
    var info =
        new Info().title(this.buildProperties.getName()).version(this.buildProperties.getVersion());

    return new OpenAPI().info(info);
  }
}
