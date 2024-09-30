package org.jlab.cnm.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ryans
 */
@Entity
@Table(
    name = "LOCATOR_CODE",
    schema = "CNM_OWNER",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"SECTOR_CODE_ID", "YY_CODE"})})
@NamedQueries({@NamedQuery(name = "LocatorCode.findAll", query = "SELECT l FROM LocatorCode l")})
public class LocatorCode implements Serializable {
  private static final long serialVersionUID = 1L;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2)
  @Column(name = "YY_CODE", nullable = false, length = 2)
  private String yyCode;

  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these
  // annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "LOCATOR_CODE_ID", nullable = false, precision = 22, scale = 0)
  private BigDecimal locatorCodeId;

  @Size(max = 512)
  @Column(length = 512)
  private String description;

  @Basic(optional = true)
  @Size(min = 1, max = 24)
  @Column(nullable = true, length = 24)
  private String state;

  @Column(name = "PROPOSED_BY")
  private BigInteger proposedBy;

  @JoinColumn(name = "SECTOR_CODE_ID", referencedColumnName = "SECTOR_CODE_ID", nullable = false)
  @ManyToOne(optional = false)
  private SectorCode sectorCode;

  public LocatorCode() {}

  public LocatorCode(BigDecimal locatorCodeId) {
    this.locatorCodeId = locatorCodeId;
  }

  public LocatorCode(BigDecimal locatorCodeId, String yyCode, String state) {
    this.locatorCodeId = locatorCodeId;
    this.yyCode = yyCode;
    this.state = state;
  }

  public String getYyCode() {
    return yyCode;
  }

  public void setYyCode(String yyCode) {
    this.yyCode = yyCode;
  }

  public BigDecimal getLocatorCodeId() {
    return locatorCodeId;
  }

  public void setLocatorCodeId(BigDecimal locatorCodeId) {
    this.locatorCodeId = locatorCodeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public BigInteger getProposedBy() {
    return proposedBy;
  }

  public void setProposedBy(BigInteger proposedBy) {
    this.proposedBy = proposedBy;
  }

  public SectorCode getSectorCode() {
    return sectorCode;
  }

  public void setSectorCode(SectorCode sectorCode) {
    this.sectorCode = sectorCode;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (locatorCodeId != null ? locatorCodeId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof LocatorCode)) {
      return false;
    }
    LocatorCode other = (LocatorCode) object;
    return (this.locatorCodeId != null || other.locatorCodeId == null)
        && (this.locatorCodeId == null || this.locatorCodeId.equals(other.locatorCodeId));
  }

  @Override
  public String toString() {
    return "org.jlab.cnm.persistence.entity.LocatorCode[ locatorCodeId=" + locatorCodeId + " ]";
  }
}
