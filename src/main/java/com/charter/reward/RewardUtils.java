package com.charter.reward;

import java.math.BigDecimal;

/**
 * RewardUtils class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
public class RewardUtils {

  private static final Long FIRST_REWARD_LIMIT = 100L;

  private static final Long SECOND_REWARD_LIMIT = 50L;

  /**
   * calculateReward.
   *
   * @param amount a {@link java.math.BigDecimal} object
   * @return a long
   */
  public static long calculateReward(BigDecimal amount) {
    var amountLong = amount.longValue();

    if (amountLong > FIRST_REWARD_LIMIT) {
      return (amountLong - FIRST_REWARD_LIMIT) * 2 + FIRST_REWARD_LIMIT - SECOND_REWARD_LIMIT;
    } else if (amountLong > SECOND_REWARD_LIMIT) {
      return amountLong - SECOND_REWARD_LIMIT;
    }

    return BigDecimal.ZERO.longValue();
  }
}
