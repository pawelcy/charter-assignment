package com.charter.commons.config;

import com.charter.commons.cors.CorsProperties;
import java.time.Duration;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private static final String DEFAULT_PATH_MAPPING = "/**";

  private final CorsProperties corsProperties;

  /**
   * Constructor for WebMvcConfig.
   *
   * @param corsProperties a {@link com.charter.commons.cors.CorsProperties} object
   */
  public WebMvcConfig(CorsProperties corsProperties) {
    this.corsProperties = corsProperties;
  }

  /** {@inheritDoc} */
  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {
    var propertyMapper = PropertyMapper.get();

    var corsRegistration = corsRegistry.addMapping(DEFAULT_PATH_MAPPING);
    propertyMapper
        .from(this.corsProperties::allowCredentials)
        .whenNonNull()
        .to(corsRegistration::allowCredentials);
    propertyMapper
        .from(this.corsProperties::allowedHeaders)
        .whenNonNull()
        .to(corsRegistration::allowedHeaders);
    propertyMapper
        .from(this.corsProperties::allowedMethods)
        .whenNonNull()
        .to(corsRegistration::allowedMethods);
    propertyMapper
        .from(this.corsProperties::allowedOrigins)
        .whenNonNull()
        .to(corsRegistration::allowedOrigins);
    propertyMapper
        .from(this.corsProperties::allowedOriginPatterns)
        .whenNonNull()
        .to(corsRegistration::allowedOriginPatterns);
    propertyMapper
        .from(this.corsProperties::exposedHeaders)
        .whenNonNull()
        .to(corsRegistration::exposedHeaders);
    propertyMapper
        .from(this.corsProperties::maxAge)
        .whenNonNull()
        .as(Duration::toMillis)
        .to(corsRegistration::maxAge);
  }
}
