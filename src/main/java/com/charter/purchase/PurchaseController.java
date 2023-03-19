package com.charter.purchase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * PurchaseController class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@RequestMapping("/api/v1/purchase")
@RestController
@Tag(description = "All endpoints relating to purchase.", name = "Purchase endpoints.")
@Validated
public class PurchaseController {

  private final PurchaseService purchaseService;

  /**
   * Constructor for PurchaseController.
   *
   * @param purchaseService a {@link com.charter.purchase.PurchaseService} object
   */
  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  /**
   * getPage.
   *
   * @param customerIdList a {@link java.util.List} object
   * @param pageable a {@link org.springframework.data.domain.Pageable} object
   * @return a {@link org.springframework.data.domain.Page} object
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Return purchase page.", summary = "Return purchase page.")
  @PageableAsQueryParam
  @ResponseStatus(HttpStatus.OK)
  public @NotNull @Valid Page<@NotNull PurchaseDto> getPage(
      @Parameter(example = "[1, 2]") @RequestParam(name = "customerId", required = false)
          List<@NotNull @Positive Long> customerIdList,
      @Parameter(hidden = true) Pageable pageable) {
    return this.purchaseService.getPage(customerIdList, pageable);
  }

  /**
   * add.
   *
   * @param purchaseAddDto a {@link com.charter.purchase.PurchaseAddDto} object
   * @return a {@link com.charter.purchase.PurchaseDto} object
   */
  @Operation(description = "Add purchase.", summary = "Add purchase.")
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @Validated
  public @NotNull PurchaseDto add(@Valid @RequestBody PurchaseAddDto purchaseAddDto) {
    return this.purchaseService.add(purchaseAddDto);
  }

  /**
   * update.
   *
   * @param id a {@link java.lang.Long} object
   * @param purchaseAddDto a {@link com.charter.purchase.PurchaseAddDto} object
   * @return a {@link com.charter.purchase.PurchaseDto} object
   */
  @Operation(description = "Update purchase.", summary = "Update purchase.")
  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Validated
  public @NotNull PurchaseDto update(
      @Parameter(example = "1", name = "id", required = true) @PathVariable("id") Long id,
      @Valid @RequestBody PurchaseAddDto purchaseAddDto) {
    return this.purchaseService.update(id, purchaseAddDto);
  }

  /**
   * delete.
   *
   * @param id a {@link java.lang.Long} object
   */
  @DeleteMapping("/{id}")
  @Operation(description = "Delete purchase.", summary = "Delete purchase.")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @Parameter(example = "1", name = "id", required = true) @PathVariable("id") Long id) {
    this.purchaseService.delete(id);
  }
}
