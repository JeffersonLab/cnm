package org.jlab.cnm.persistence.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author ryans
 */
@Entity
@Table(
    name = "TYPE_CODE",
    schema = "CNM_OWNER",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"SYSTEM_CODE_ID", "VV_CODE"})})
@NamedQueries({@NamedQuery(name = "TypeCode.findAll", query = "SELECT t FROM TypeCode t")})
public class TypeCode implements Serializable {
  private static final long serialVersionUID = 1L;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2)
  @Column(name = "VV_CODE", nullable = false, length = 2)
  private String vvCode;

  @Size(max = 512)
  @Column(length = 512)
  private String description;

  @Id
  @SequenceGenerator(name = "TypeId", sequenceName = "TYPE_ID", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TypeId")
  @Basic(optional = false)
  @NotNull
  @Column(name = "TYPE_CODE_ID", nullable = false, precision = 22, scale = 0)
  private BigInteger typeCodeId;

  @Basic(optional = true)
  @Size(min = 1, max = 16)
  @Column(nullable = true, length = 16)
  private String grouping;

  @Size(max = 20)
  @Column(length = 20)
  private String state;

  @Lob
  @Column(name = "JSON_ATTRIBUTES")
  private String jsonAttributes;

  @Column(name = "PROPOSED_BY")
  private BigInteger proposedBy;

  @JoinColumn(name = "SYSTEM_CODE_ID", referencedColumnName = "SYSTEM_CODE_ID", nullable = false)
  @ManyToOne(optional = false)
  private SystemCode systemCode;

  public String getVvCode() {
    return vvCode;
  }

  public void setVvCode(String vvCode) {
    this.vvCode = vvCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigInteger getTypeCodeId() {
    return typeCodeId;
  }

  public void setTypeCodeId(BigInteger typeCodeId) {
    this.typeCodeId = typeCodeId;
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

  public String getJsonAttributes() {
    return jsonAttributes;
  }

  public void setJsonAttributes(String jsonAttributes) {
    this.jsonAttributes = jsonAttributes;
  }

  public BigInteger getProposedBy() {
    return proposedBy;
  }

  public void setProposedBy(BigInteger proposedBy) {
    this.proposedBy = proposedBy;
  }

  public SystemCode getSystemCode() {
    return systemCode;
  }

  public void setSystemCode(SystemCode systemCode) {
    this.systemCode = systemCode;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (typeCodeId != null ? typeCodeId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TypeCode)) {
      return false;
    }
    TypeCode other = (TypeCode) object;
    return (this.typeCodeId != null || other.typeCodeId == null)
        && (this.typeCodeId == null || this.typeCodeId.equals(other.typeCodeId));
  }

  @Override
  public String toString() {
    return "org.jlab.cnm.persistence.entity.TypeCode[ typeCodeId=" + typeCodeId + " ]";
  }
}
