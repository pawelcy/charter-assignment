package com.charter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Application class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
public class Application {

  /**
   * main.
   *
   * @param args an array of {@link java.lang.String} objects
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
