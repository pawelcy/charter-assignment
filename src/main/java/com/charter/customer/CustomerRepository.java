package com.charter.customer;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * CustomerRepository interface.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  /**
   * findAllWithCustomer.
   *
   * @param dateTime a {@link java.time.LocalDateTime} object
   * @return a {@link java.util.List} object
   */
  @Query("select c from Customer c join fetch c.purchaseList pl where pl.dateTime >= :dateTime")
  List<Customer> findAllWithCustomer(LocalDateTime dateTime);
}
