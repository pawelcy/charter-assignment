package com.charter.purchase;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * PurchaseRepository interface.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  /**
   * findAllWithCustomer.
   *
   * @return a {@link java.util.List} object
   */
  @EntityGraph(attributePaths = {"customer"})
  @Query("select p from Purchase p ")
  List<Purchase> findAllWithCustomer();

  /**
   * findAllWithCustomer.
   *
   * @param pageable a {@link org.springframework.data.domain.Pageable} object
   * @return a {@link org.springframework.data.domain.Page} object
   */
  @EntityGraph(attributePaths = {"customer"})
  @Query("select p from Purchase p ")
  Page<Purchase> findAllWithCustomer(Pageable pageable);

  /**
   * findByCustomerIdListWithCustomer.
   *
   * @param customerIdList a {@link java.util.List} object
   * @param pageable a {@link org.springframework.data.domain.Pageable} object
   * @return a {@link org.springframework.data.domain.Page} object
   */
  @Query("select p from Purchase p join p.customer c where c.id in (:customerIdList)")
  Page<Purchase> findByCustomerIdListWithCustomer(List<Long> customerIdList, Pageable pageable);

  /**
   * findByIdWithCustomer.
   *
   * @param id a {@link java.lang.Long} object
   * @return a {@link java.util.Optional} object
   */
  @EntityGraph(attributePaths = {"customer"})
  @Query("select p from Purchase p where p.id = :id")
  Optional<Purchase> findByIdWithCustomer(Long id);
}
