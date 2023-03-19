package com.charter.reward;

import com.charter.customer.CustomerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * RewardService class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Service
@Validated
public class RewardService {

  private static final Integer PREV_MONTH = 2;

  private final CustomerRepository customerRepository;

  /**
   * Constructor for RewardService.
   *
   * @param customerRepository a {@link com.charter.customer.CustomerRepository} object
   */
  public RewardService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  /**
   * getList.
   *
   * @return a {@link java.util.List} object
   */
  @Transactional(readOnly = true)
  public @NotNull @Valid List<@NotNull RewardDto> getList() {
    var previousDateTime = LocalDateTime.now().minusMonths(PREV_MONTH);

    var startPeriodDateTime =
        LocalDateTime.of(previousDateTime.getYear(), previousDateTime.getMonth(), 1, 0, 0, 0, 0);

    var customerList = this.customerRepository.findAllWithCustomer(startPeriodDateTime);

    record YearMonth(int year, int month) {}

    return customerList.parallelStream()
        .map(
            customer -> {
              var rewardMonthDtoList =
                  customer.getPurchaseList().parallelStream()
                      .collect(
                          Collectors.groupingBy(
                              purchase ->
                                  new YearMonth(
                                      purchase.getDateTime().getYear(),
                                      purchase.getDateTime().getMonthValue()),
                              Collectors.mapping(
                                  purchase -> RewardUtils.calculateReward(purchase.getAmount()),
                                  Collectors.summingLong(reward -> reward))))
                      .entrySet()
                      .parallelStream()
                      .map(
                          entry ->
                              new RewardYearMonthDto(
                                  entry.getKey().year(), entry.getKey().month(), entry.getValue()))
                      .toList();

              var totalPoints =
                  rewardMonthDtoList.parallelStream().mapToLong(RewardYearMonthDto::points).sum();

              return new RewardDto(customer.getId(), rewardMonthDtoList, totalPoints);
            })
        .toList();
  }
}
