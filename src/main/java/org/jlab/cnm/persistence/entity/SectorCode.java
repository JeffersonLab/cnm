package org.jlab.cnm.persistence.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * @author ryans
 */
@Entity
@Table(name = "SECTOR_CODE", schema = "CNM_OWNER")
@NamedQueries({@NamedQuery(name = "SectorCode.findAll", query = "SELECT s FROM SectorCode s")})
public class SectorCode implements Serializable {
  private static final long serialVersionUID = 1L;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2)
  @Column(name = "XX_CODE", nullable = false, length = 2)
  private String xxCode;

  @Size(max = 512)
  @Column(length = 512)
  private String description;

  @Id
  @SequenceGenerator(name = "SectorId", sequenceName = "SECTOR_ID", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SectorId")
  @Basic(optional = false)
  @NotNull
  @Column(name = "SECTOR_CODE_ID", nullable = false, precision = 22, scale = 0)
  private BigInteger sectorCodeId;

  @Basic(optional = true)
  @Size(min = 1, max = 16)
  @Column(nullable = true, length = 16)
  private String grouping;

  @Basic(optional = true)
  @Size(min = 1, max = 24)
  @Column(nullable = true, length = 24)
  private String state;

  @Column(name = "PROPOSED_BY")
  private BigInteger proposedBy;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectorCode")
  private List<LocatorCode> locatorCodeList;

  public String getXxCode() {
    return xxCode;
  }

  public void setXxCode(String xxCode) {
    this.xxCode = xxCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigInteger getSectorCodeId() {
    return sectorCodeId;
  }

  public void setSectorCodeId(BigInteger sectorCodeId) {
    this.sectorCodeId = sectorCodeId;
  }

  public String getGrouping() {
    return grouping;
  }

  public void setGrouping(String grouping) {
    this.grouping = grouping;
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

  public List<LocatorCode> getLocatorCodeList() {
    return locatorCodeList;
  }

  public void setLocatorCodeList(List<LocatorCode> locatorCodeList) {
    this.locatorCodeList = locatorCodeList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (sectorCodeId != null ? sectorCodeId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SectorCode)) {
      return false;
    }
    SectorCode other = (SectorCode) object;
    return (this.sectorCodeId != null || other.sectorCodeId == null)
        && (this.sectorCodeId == null || this.sectorCodeId.equals(other.sectorCodeId));
  }

  @Override
  public String toString() {
    return "org.jlab.cnm.persistence.entity.SectorCode[ sectorCodeId=" + sectorCodeId + " ]";
  }
}
