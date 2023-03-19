package com.charter.commons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract BaseAuditingEntity class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditingEntity {

  @Column(name = "created_by", nullable = false, updatable = false)
  @CreatedBy
  protected String createdBy;

  @Column(name = "created_date", nullable = false, updatable = false)
  @CreatedDate
  protected LocalDateTime createdDate;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "last_modified_by", nullable = false)
  @LastModifiedBy
  protected String lastModifiedBy;

  @Column(name = "last_modified_date", nullable = false)
  @LastModifiedDate
  protected LocalDateTime lastModifiedDate;

  @Column(name = "version")
  @Version
  protected Long version;

  /**
   * Getter for the field <code>id</code>.
   *
   * @return a {@link java.lang.Long} object
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Setter for the field <code>id</code>.
   *
   * @param id a {@link java.lang.Long} object
   */
  public void setId(Long id) {
    this.id = id;
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

    var other = (BaseAuditingEntity) obj;

    return new EqualsBuilder().append(this.id, other.id).isEquals();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(this.id).toHashCode();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("createdBy", this.createdBy)
        .append("createdDate", this.createdDate)
        .append("id", this.id)
        .append("lastModifiedBy", this.lastModifiedBy)
        .append("lastModifiedDate", this.lastModifiedDate)
        .append("version", this.version)
        .toString();
  }
}
