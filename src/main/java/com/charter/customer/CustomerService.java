package com.charter.customer;

import com.charter.commons.exception.NotFoundRuntimeException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * CustomerService class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Service
@Validated
public class CustomerService {

  private final CustomerRepository customerRepository;

  /**
   * Constructor for CustomerService.
   *
   * @param customerRepository a {@link com.charter.customer.CustomerRepository} object
   */
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  /**
   * getPage.
   *
   * @param pageable a {@link org.springframework.data.domain.Pageable} object
   * @return a {@link org.springframework.data.domain.Page} object
   */
  @Transactional(readOnly = true)
  public @NotNull @Valid Page<@NotNull CustomerDto> getPage(@NotNull Pageable pageable) {
    return this.customerRepository
        .findAll(pageable)
        .map(
            customer ->
                new CustomerDto(
                    customer.getAge(),
                    customer.getEmailAddress(),
                    customer.getFirstName(),
                    customer.getId(),
                    customer.getLastName()));
  }

  /**
   * add.
   *
   * @param customerAddDto a {@link com.charter.customer.CustomerAddDto} object
   * @return a {@link com.charter.customer.CustomerDto} object
   */
  @Transactional
  public @NotNull @Valid CustomerDto add(@NotNull CustomerAddDto customerAddDto) {
    var customer =
        new Customer()
            .setAge(customerAddDto.age())
            .setEmailAddress(customerAddDto.emailAddress())
            .setFirstName(customerAddDto.firstname())
            .setLastName(customerAddDto.lastname());

    var savedCustomer = this.customerRepository.save(customer);

    return new CustomerDto(
        savedCustomer.getAge(),
        savedCustomer.getEmailAddress(),
        savedCustomer.getFirstName(),
        savedCustomer.getId(),
        savedCustomer.getLastName());
  }

  /**
   * update.
   *
   * @param id a {@link java.lang.Long} object
   * @param customerAddDto a {@link com.charter.customer.CustomerAddDto} object
   * @return a {@link com.charter.customer.CustomerDto} object
   */
  @Transactional
  public @NotNull @Valid CustomerDto update(
      @NotNull Long id, @NotNull CustomerAddDto customerAddDto) {
    var foundCustomer =
        this.customerRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundRuntimeException(
                        String.format("Customer with id: %d not found", id)));

    foundCustomer
        .setAge(customerAddDto.age())
        .setEmailAddress(customerAddDto.emailAddress())
        .setFirstName(customerAddDto.firstname())
        .setLastName(customerAddDto.lastname());

    return new CustomerDto(
        foundCustomer.getAge(),
        foundCustomer.getEmailAddress(),
        foundCustomer.getFirstName(),
        foundCustomer.getId(),
        foundCustomer.getLastName());
  }

  /**
   * delete.
   *
   * @param id a {@link java.lang.Long} object
   */
  @Transactional
  public void delete(@NotNull Long id) {
    var foundCustomer =
        this.customerRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundRuntimeException(
                        String.format("Customer with id: %d not found", id)));

    this.customerRepository.delete(foundCustomer);
  }
}
