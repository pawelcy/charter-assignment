package com.charter.reward;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * RewardController class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@RequestMapping("/api/v1/reward")
@RestController
@Tag(description = "All endpoints relating to reward.", name = "Reward endpoints.")
@Validated
public class RewardController {

  private final RewardService rewardService;

  /**
   * Constructor for RewardController.
   *
   * @param rewardService a {@link com.charter.reward.RewardService} object
   */
  public RewardController(RewardService rewardService) {
    this.rewardService = rewardService;
  }

  /**
   * getList.
   *
   * @return a {@link java.util.List} object
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Return reward list.", summary = "Return reward list.")
  @ResponseStatus(HttpStatus.OK)
  public @NotNull @Valid List<@NotNull RewardDto> getList() {
    return this.rewardService.getList();
  }
}
