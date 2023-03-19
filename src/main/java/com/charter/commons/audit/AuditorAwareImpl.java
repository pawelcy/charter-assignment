package com.charter.commons.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/**
 * AuditorAwareImpl class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
public class AuditorAwareImpl implements AuditorAware<String> {

  private static final String SYSTEM = "SYSTEM";

  /** {@inheritDoc} */
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(SYSTEM);
  }
}
