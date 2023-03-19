package com.charter.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomerController class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@RequestMapping("/api/v1/customer")
@RestController
@Tag(description = "All endpoints relating to customer.", name = "Customer endpoints.")
@Validated
public class CustomerController {

  private final CustomerService customerService;

  /**
   * Constructor for CustomerController.
   *
   * @param customerService a {@link com.charter.customer.CustomerService} object
   */
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  /**
   * getPage.
   *
   * @param pageable a {@link org.springframework.data.domain.Pageable} object
   * @return a {@link org.springframework.data.domain.Page} object
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Return customer page.", summary = "Return customer page.")
  @PageableAsQueryParam
  @ResponseStatus(HttpStatus.OK)
  public @NotNull @Valid Page<@NotNull CustomerDto> getPage(
      @Parameter(hidden = true) Pageable pageable) {
    return this.customerService.getPage(pageable);
  }

  /**
   * add.
   *
   * @param customerAddDto a {@link com.charter.customer.CustomerAddDto} object
   * @return a {@link com.charter.customer.CustomerDto} object
   */
  @Operation(description = "Add customer.", summary = "Add customer.")
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @Validated
  public @NotNull CustomerDto add(@Valid @RequestBody CustomerAddDto customerAddDto) {
    return this.customerService.add(customerAddDto);
  }

  /**
   * update.
   *
   * @param id a {@link java.lang.Long} object
   * @param customerAddDto a {@link com.charter.customer.CustomerAddDto} object
   * @return a {@link com.charter.customer.CustomerDto} object
   */
  @Operation(description = "Update customer.", summary = "Update customer.")
  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Validated
  public @NotNull CustomerDto update(
      @Parameter(example = "1", name = "id", required = true) @PathVariable("id") Long id,
      @Valid @RequestBody CustomerAddDto customerAddDto) {
    return this.customerService.update(id, customerAddDto);
  }

  /**
   * delete.
   *
   * @param id a {@link java.lang.Long} object
   */
  @DeleteMapping("/{id}")
  @Operation(description = "Delete customer.", summary = "Delete customer.")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @Parameter(example = "1", name = "id", required = true) @PathVariable("id") Long id) {
    this.customerService.delete(id);
  }
}
