package com.charter.commons.cors;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

/**
 * CorsProperties class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "custom.cors")
public record CorsProperties(
    Boolean allowCredentials,
    String[] allowedHeaders,
    String[] allowedMethods,
    String[] allowedOrigins,
    String[] allowedOriginPatterns,
    String[] exposedHeaders,
    @DurationUnit(ChronoUnit.SECONDS) Duration maxAge) {}
