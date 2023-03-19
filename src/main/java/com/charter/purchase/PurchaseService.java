package com.charter.purchase;

import com.charter.commons.exception.NotFoundRuntimeException;
import com.charter.customer.CustomerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * PurchaseService class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Service
@Validated
public class PurchaseService {

  private final CustomerRepository customerRepository;

  private final PurchaseRepository purchaseRepository;

  /**
   * Constructor for PurchaseService.
   *
   * @param customerRepository a {@link com.charter.customer.CustomerRepository} object
   * @param purchaseRepository a {@link com.charter.purchase.PurchaseRepository} object
   */
  public PurchaseService(
      CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
    this.customerRepository = customerRepository;
    this.purchaseRepository = purchaseRepository;
  }

  /**
   * getPage.
   *
   * @param customerIdList a {@link java.util.List} object
   * @param pageable a {@link org.springframework.data.domain.Pageable} object
   * @return a {@link org.springframework.data.domain.Page} object
   */
  @Transactional(readOnly = true)
  public @NotNull @Valid Page<@NotNull PurchaseDto> getPage(
      List<@NotNull @Positive Long> customerIdList, @NotNull Pageable pageable) {
    return (customerIdList == null || customerIdList.isEmpty()
            ? this.purchaseRepository.findAllWithCustomer(pageable)
            : this.purchaseRepository.findByCustomerIdListWithCustomer(customerIdList, pageable))
        .map(
            purchase ->
                new PurchaseDto(
                    purchase.getAmount(),
                    purchase.getCustomer().getId(),
                    purchase.getDateTime(),
                    purchase.getDescription(),
                    purchase.getId()));
  }

  /**
   * add.
   *
   * @param purchaseAddDto a {@link com.charter.purchase.PurchaseAddDto} object
   * @return a {@link com.charter.purchase.PurchaseDto} object
   */
  @Transactional
  public @NotNull PurchaseDto add(@Valid @NotNull PurchaseAddDto purchaseAddDto) {
    var customerId = purchaseAddDto.customerId();

    var foundCustomer =
        this.customerRepository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new NotFoundRuntimeException(
                        String.format("Customer with id: %d not found", customerId)));

    var purchase =
        new Purchase()
            .setAmount(purchaseAddDto.amount())
            .setDateTime(purchaseAddDto.dateTime())
            .setDescription(purchaseAddDto.description());

    foundCustomer.addPurchase(purchase);

    var savedPurchase = this.purchaseRepository.save(purchase);

    return new PurchaseDto(
        savedPurchase.getAmount(),
        savedPurchase.getCustomer().getId(),
        savedPurchase.getDateTime(),
        savedPurchase.getDescription(),
        savedPurchase.getId());
  }

  /**
   * update.
   *
   * @param id a {@link java.lang.Long} object
   * @param purchaseAddDto a {@link com.charter.purchase.PurchaseAddDto} object
   * @return a {@link com.charter.purchase.PurchaseDto} object
   */
  @Transactional
  public @NotNull PurchaseDto update(
      @NotNull Long id, @Valid @NotNull PurchaseAddDto purchaseAddDto) {
    var foundPurchase =
        this.purchaseRepository
            .findByIdWithCustomer(id)
            .orElseThrow(
                () ->
                    new NotFoundRuntimeException(
                        String.format("Purchase with id: %d not found", id)));

    var currentCustomer = foundPurchase.getCustomer();

    var customerId = purchaseAddDto.customerId();

    if (!Objects.equals(currentCustomer.getId(), customerId)) {
      var foundCustomer =
          this.customerRepository
              .findById(customerId)
              .orElseThrow(
                  () ->
                      new NotFoundRuntimeException(
                          String.format("Customer with id: %d not found", customerId)));

      currentCustomer.removePurchase(foundPurchase);

      foundCustomer.addPurchase(foundPurchase);
    }

    foundPurchase
        .setAmount(purchaseAddDto.amount())
        .setDateTime(purchaseAddDto.dateTime())
        .setDescription(purchaseAddDto.description());

    return new PurchaseDto(
        foundPurchase.getAmount(),
        foundPurchase.getCustomer().getId(),
        foundPurchase.getDateTime(),
        foundPurchase.getDescription(),
        foundPurchase.getId());
  }

  /**
   * delete.
   *
   * @param id a {@link java.lang.Long} object
   */
  @Transactional
  public void delete(@NotNull Long id) {
    var foundPurchase =
        this.purchaseRepository
            .findByIdWithCustomer(id)
            .orElseThrow(
                () ->
                    new NotFoundRuntimeException(
                        String.format("Purchase with id: %d not found", id)));

    this.purchaseRepository.delete(foundPurchase);
  }
}
