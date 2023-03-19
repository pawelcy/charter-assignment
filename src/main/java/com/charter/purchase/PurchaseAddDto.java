package com.charter.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PurchaseAddDto class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Schema(title = "All details about add purchase.")
public record PurchaseAddDto(
    @JsonProperty(required = true, value = "amount")
        @NotNull
        @PositiveOrZero
        @Schema(description = "Amount of purchase.", example = "120")
        BigDecimal amount,
    @JsonProperty(required = true, value = "customerId")
        @NotNull
        @Positive
        @Schema(description = "Customer Unique identifier", example = "1")
        Long customerId,
    @JsonProperty(required = true, value = "dateTime")
        @NotNull
        @Schema(
            description = "Date time of purchase.",
            example = "2023-03-17T15:31:03",
            type = "string")
        LocalDateTime dateTime,
    @JsonProperty(required = true, value = "description")
        @NotBlank
        @Schema(description = "Description of purchase.", example = "Some purchase.")
        @Size(min = 1, max = 255)
        String description) {}
