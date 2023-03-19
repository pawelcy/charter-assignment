package com.charter.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * CustomerDto class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Schema(title = "All details about customer.")
public record CustomerDto(
    @JsonProperty(required = true, value = "age")
        @NotNull
        @PositiveOrZero
        @Schema(description = "Age of customer", example = "53")
        Integer age,
    @Email
        @JsonProperty(required = true, value = "emailAddress")
        @NotBlank
        @Schema(description = "Email address of customer", example = "walter.white@example.com")
        @Size(min = 1, max = 255)
        String emailAddress,
    @JsonProperty(required = true, value = "firstName")
        @NotBlank
        @Schema(description = "First name of customer", example = "Walter")
        @Size(min = 1, max = 255)
        String firstName,
    @JsonProperty(required = true, value = "id")
        @NotNull
        @Positive
        @Schema(description = "Unique identifier", example = "1")
        Long id,
    @JsonProperty(required = true, value = "lastName")
        @NotBlank
        @Schema(description = "Last name of customer", example = "White")
        @Size(min = 1, max = 255)
        String lastName) {}
