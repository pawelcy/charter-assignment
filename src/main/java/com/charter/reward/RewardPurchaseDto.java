package com.charter.reward;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RewardPurchaseDto class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
public record RewardPurchaseDto(BigDecimal amount, LocalDateTime dateTime) {}
