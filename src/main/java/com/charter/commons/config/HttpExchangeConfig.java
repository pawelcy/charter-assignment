package com.charter.commons.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpExchangeConfig class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Configuration
public class HttpExchangeConfig {

  private static final int CAPACITY = 10000;

  /**
   * httpExchangeRepository.
   *
   * @return a {@link org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository} object
   */
  @Bean
  public HttpExchangeRepository httpExchangeRepository() {
    var inMemoryHttpExchangeRepository = new InMemoryHttpExchangeRepository();
    inMemoryHttpExchangeRepository.setCapacity(CAPACITY);

    return inMemoryHttpExchangeRepository;
  }
}
