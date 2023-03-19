package com.charter.reward;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * RewardYearMonthDto class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Schema(title = "All details about reward month.")
public record RewardYearMonthDto(Integer year, Integer month, Long points) {}
