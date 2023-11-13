package org.jlab.cnm.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ryans
 */
@Entity
@Table(name = "ELEMENT_CODE", schema = "CNM_OWNER")
@NamedQueries({
    @NamedQuery(name = "ElementCode.findAll", query = "SELECT e FROM ElementCode e")})
public class ElementCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ZZ_CODE", nullable = false, length = 2)
    private String zzCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ELEMENT_CODE_ID", nullable = false, precision = 22, scale = 0)
    private BigDecimal elementCodeId;
    @Size(max = 512)
    @Column(length = 512)
    private String description;
    @Basic(optional = true)
    @Size(min = 1, max = 24)
    @Column(nullable = true, length = 24)
    private String state;
    @Column(name = "PROPOSED_BY")
    private BigInteger proposedBy;

    public ElementCode() {
    }

    public ElementCode(BigDecimal elementCodeId) {
        this.elementCodeId = elementCodeId;
    }

    public ElementCode(BigDecimal elementCodeId, String zzCode, String state) {
        this.elementCodeId = elementCodeId;
        this.zzCode = zzCode;
        this.state = state;
    }

    public String getZzCode() {
        return zzCode;
    }

    public void setZzCode(String zzCode) {
        this.zzCode = zzCode;
    }

    public BigDecimal getElementCodeId() {
        return elementCodeId;
    }

    public void setElementCodeId(BigDecimal elementCodeId) {
        this.elementCodeId = elementCodeId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elementCodeId != null ? elementCodeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementCode)) {
            return false;
        }
        ElementCode other = (ElementCode) object;
        return (this.elementCodeId != null || other.elementCodeId == null) &&
                (this.elementCodeId == null || this.elementCodeId.equals(other.elementCodeId));
    }

    @Override
    public String toString() {
        return "org.jlab.cnm.persistence.entity.ElementCode[ elementCodeId=" + elementCodeId + " ]";
    }
    
}
