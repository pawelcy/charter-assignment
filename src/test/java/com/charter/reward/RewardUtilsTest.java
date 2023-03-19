package com.charter.reward;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RewardUtilsTest {

  @DisplayName("Update Should Failed with 404")
  @Test
  void givenCalculateReward_shouldCalculateAboveFirstLimit() {
    var reward = RewardUtils.calculateReward(BigDecimal.valueOf(12000, 2));

    Assertions.assertEquals(90, reward);
  }

  @DisplayName("Update Should Failed with 404")
  @Test
  void givenCalculateReward_shouldCalculateEqualsFirstLimit() {
    var reward = RewardUtils.calculateReward(BigDecimal.valueOf(10000, 2));

    Assertions.assertEquals(50, reward);
  }

  @DisplayName("Update Should Failed with 404")
  @Test
  void givenCalculateReward_shouldCalculateEqualsSecondLimit() {
    var reward = RewardUtils.calculateReward(BigDecimal.valueOf(5000, 2));

    Assertions.assertEquals(0, reward);
  }
}
