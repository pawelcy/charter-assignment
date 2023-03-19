package com.charter.customer;

import com.charter.commons.entity.BaseAuditingEntity;
import com.charter.purchase.Purchase;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Customer class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@AttributeOverride(column = @Column(name = "cus_created_by", nullable = false), name = "createdBy")
@AttributeOverride(
    column = @Column(name = "cus_created_date", nullable = false),
    name = "createdDate")
@AttributeOverride(column = @Column(name = "cus_id"), name = "id")
@AttributeOverride(
    column = @Column(name = "cus_last_modified_by", nullable = false),
    name = "lastModifiedBy")
@AttributeOverride(
    column = @Column(name = "cus_last_modified_date", nullable = false),
    name = "lastModifiedDate")
@AttributeOverride(column = @Column(name = "cus_version", nullable = false), name = "version")
@Entity(name = "Customer")
@Table(name = "p_customer")
public class Customer extends BaseAuditingEntity {

  @Column(name = "cus_age", nullable = false)
  private Integer age;

  @Column(name = "cus_email_address", nullable = false)
  private String emailAddress;

  @Column(name = "cus_firstname", nullable = false)
  private String firstName;

  @Column(name = "cus_lastname", nullable = false)
  private String lastName;

  @OneToMany(
      mappedBy = "customer",
      cascade = {CascadeType.ALL},
      orphanRemoval = true)
  private final List<Purchase> purchaseList = new ArrayList<>();

  /**
   * Getter for the field <code>age</code>.
   *
   * @return a {@link java.lang.Integer} object
   */
  public Integer getAge() {
    return this.age;
  }

  /**
   * Setter for the field <code>age</code>.
   *
   * @param age a {@link java.lang.Integer} object
   * @return a {@link com.charter.customer.Customer} object
   */
  public Customer setAge(Integer age) {
    this.age = age;

    return this;
  }

  /**
   * Getter for the field <code>emailAddress</code>.
   *
   * @return a {@link java.lang.String} object
   */
  public String getEmailAddress() {
    return this.emailAddress;
  }

  /**
   * Setter for the field <code>emailAddress</code>.
   *
   * @param emailAddress a {@link java.lang.String} object
   * @return a {@link com.charter.customer.Customer} object
   */
  public Customer setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;

    return this;
  }

  /**
   * Getter for the field <code>firstName</code>.
   *
   * @return a {@link java.lang.String} object
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Setter for the field <code>firstName</code>.
   *
   * @param firstName a {@link java.lang.String} object
   * @return a {@link com.charter.customer.Customer} object
   */
  public Customer setFirstName(String firstName) {
    this.firstName = firstName;

    return this;
  }

  /**
   * Getter for the field <code>lastName</code>.
   *
   * @return a {@link java.lang.String} object
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Setter for the field <code>lastName</code>.
   *
   * @param lastName a {@link java.lang.String} object
   * @return a {@link com.charter.customer.Customer} object
   */
  public Customer setLastName(String lastName) {
    this.lastName = lastName;

    return this;
  }

  /**
   * Getter for the field <code>purchaseList</code>.
   *
   * @return a {@link java.util.List} object
   */
  public List<Purchase> getPurchaseList() {
    return List.copyOf(this.purchaseList);
  }

  /**
   * addPurchase.
   *
   * @param purchase a {@link com.charter.purchase.Purchase} object
   */
  public void addPurchase(Purchase purchase) {
    this.purchaseList.add(purchase);

    purchase.setCustomer(this);
  }

  /**
   * removePurchase.
   *
   * @param purchase a {@link com.charter.purchase.Purchase} object
   */
  public void removePurchase(Purchase purchase) {
    this.purchaseList.remove(purchase);

    purchase.setCustomer(null);
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
        .append("age", this.age)
        .append("createdBy", this.createdBy)
        .append("createdDate", this.createdDate)
        .append("emailAddress", this.emailAddress)
        .append("firstName", this.firstName)
        .append("id", this.id)
        .append("lastModifiedBy", this.lastModifiedBy)
        .append("lastModifiedDate", this.lastModifiedDate)
        .append("lastName", this.lastName)
        .append("version", this.version)
        .toString();
  }
}
