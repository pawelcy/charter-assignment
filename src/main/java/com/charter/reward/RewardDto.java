package com.charter.reward;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * RewardDto class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Schema(title = "All details about reward.")
public record RewardDto(
    @JsonProperty(required = true, value = "customerId")
        @NotNull
        @Positive
        @Schema(description = "Customer Unique identifier", example = "1")
        Long customerId,
    @JsonProperty(required = true, value = "rewardMonths")
        List<RewardYearMonthDto> rewardYearMonthDtoList,
    @JsonProperty(required = true, value = "totalPoints")
        @NotNull
        @PositiveOrZero
        @Schema(description = "Total points of reward", example = "1")
        Long totalPoints) {}
