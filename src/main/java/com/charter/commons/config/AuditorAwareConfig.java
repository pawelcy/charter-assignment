package com.charter.commons.config;

import com.charter.commons.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * AuditorAwareConfig class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Configuration
public class AuditorAwareConfig {

  /**
   * auditorAware.
   *
   * @return a {@link org.springframework.data.domain.AuditorAware} object
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }
}
