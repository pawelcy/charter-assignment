package com.charter.purchase;

import com.charter.commons.entity.BaseAuditingEntity;
import com.charter.customer.Customer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Purchase class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@AttributeOverride(column = @Column(name = "pur_created_by", nullable = false), name = "createdBy")
@AttributeOverride(
    column = @Column(name = "pur_created_date", nullable = false),
    name = "createdDate")
@AttributeOverride(column = @Column(name = "pur_id"), name = "id")
@AttributeOverride(
    column = @Column(name = "pur_last_modified_by", nullable = false),
    name = "lastModifiedBy")
@AttributeOverride(
    column = @Column(name = "pur_last_modified_date", nullable = false),
    name = "lastModifiedDate")
@AttributeOverride(column = @Column(name = "pur_version", nullable = false), name = "version")
@Entity(name = "Purchase")
@Table(name = "p_purchase")
public class Purchase extends BaseAuditingEntity {

  @Column(name = "pur_amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "pur_date_time", nullable = false)
  private LocalDateTime dateTime;

  @Column(name = "pur_description", nullable = false)
  private String description;

  @JoinColumn(
      foreignKey = @ForeignKey(name = "fk_pur_cus_id"),
      name = "pur_cus_id",
      nullable = false)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Customer customer;

  /**
   * Getter for the field <code>amount</code>.
   *
   * @return a {@link java.math.BigDecimal} object
   */
  public BigDecimal getAmount() {
    return this.amount;
  }

  /**
   * Setter for the field <code>amount</code>.
   *
   * @param amount a {@link java.math.BigDecimal} object
   * @return a {@link com.charter.purchase.Purchase} object
   */
  public Purchase setAmount(BigDecimal amount) {
    this.amount = amount;

    return this;
  }

  /**
   * Getter for the field <code>dateTime</code>.
   *
   * @return a {@link java.time.LocalDateTime} object
   */
  public LocalDateTime getDateTime() {
    return this.dateTime;
  }

  /**
   * Setter for the field <code>dateTime</code>.
   *
   * @param dateTime a {@link java.time.LocalDateTime} object
   * @return a {@link com.charter.purchase.Purchase} object
   */
  public Purchase setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;

    return this;
  }

  /**
   * Getter for the field <code>description</code>.
   *
   * @return a {@link java.lang.String} object
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Setter for the field <code>description</code>.
   *
   * @param description a {@link java.lang.String} object
   * @return a {@link com.charter.purchase.Purchase} object
   */
  public Purchase setDescription(String description) {
    this.description = description;

    return this;
  }

  /**
   * Getter for the field <code>customer</code>.
   *
   * @return a {@link com.charter.customer.Customer} object
   */
  public Customer getCustomer() {
    return this.customer;
  }

  /**
   * Setter for the field <code>customer</code>.
   *
   * @param customer a {@link com.charter.customer.Customer} object
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }

    return new EqualsBuilder().appendSuper(super.equals(obj)).isEquals();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("amount", this.amount)
        .append("createdBy", this.createdBy)
        .append("createdDate", this.createdDate)
        .append("customer", this.customer)
        .append("dateTime", this.dateTime)
        .append("description", this.description)
        .append("id", this.id)
        .append("lastModifiedBy", this.lastModifiedBy)
        .append("lastModifiedDate", this.lastModifiedDate)
        .append("version", this.version)
        .toString();
  }
}
