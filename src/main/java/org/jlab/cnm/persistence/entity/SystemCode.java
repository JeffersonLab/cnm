package org.jlab.cnm.persistence.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ryans
 */
@Entity
@Table(name = "SYSTEM_CODE", schema = "CNM_OWNER")
@NamedQueries({
    @NamedQuery(name = "SystemCode.findAll", query = "SELECT s FROM SystemCode s")})
public class SystemCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "S_CODE", nullable = false)
    private char sCode;
    @Size(max = 512)
    @Column(length = 512)
    private String description;
    @Id
    @SequenceGenerator(name = "SystemId", sequenceName = "SYSTEM_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SystemId")    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_CODE_ID", nullable = false, precision = 22, scale = 0)
    private BigInteger systemCodeId;
    @Basic(optional = true)
    @Size(min = 1, max = 24)
    @Column(nullable = true, length = 24)
    private String state;
    @Column(name = "PROPOSED_BY")
    private BigInteger proposedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemCode")
    private List<TypeCode> typeCodeList;

    public char getSCode() {
        return sCode;
    }

    public void setSCode(char sCode) {
        this.sCode = sCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getSystemCodeId() {
        return systemCodeId;
    }

    public void setSystemCodeId(BigInteger systemCodeId) {
        this.systemCodeId = systemCodeId;
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

    public List<TypeCode> getTypeCodeList() {
        return typeCodeList;
    }

    public void setTypeCodeList(List<TypeCode> typeCodeList) {
        this.typeCodeList = typeCodeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemCodeId != null ? systemCodeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemCode)) {
            return false;
        }
        SystemCode other = (SystemCode) object;
        if ((this.systemCodeId == null && other.systemCodeId != null) ||
                (this.systemCodeId != null && !this.systemCodeId.equals(other.systemCodeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jlab.cnm.persistence.entity.SystemCode[ systemCodeId=" + systemCodeId + " ]";
    }
    
}
