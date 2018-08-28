package ae.gov.dubaipolice.inspectionpoints.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the DP_HR_DEPARTMENTS_GRP database table.
 * 
 */
@Entity
@Table(name = "DP_HR_DEPARTMENTS_GRP", schema= "weblisher")
public class DepartmentGrp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "DEP_ORDER")
	private Integer depOrder;

	@Column(name = "PARENT_ORGANIZATION_ID")
	private Long parentOrganizationId;

	@Column(name = "PARENT_ORGANIZATION_TYPE_CODE")
	private String parentOrganizationTypeCode;
	@Id
	@Column(name = "SUB_ORGANIZATION_ID")
	private Long subOrganizationId;

	@Column(name = "SUB_ORGANIZATION_TYPE")
	private String subOrganizationType;

	@Column(name = "SUB_ORGANIZATION_TYPE_CODE")
	private String subOrganizationTypeCode;

	@Column(name = "SUBORDINATE_ORGANIZATION")
	private String subordinateOrganization;

	public DepartmentGrp() {
	}

	public Integer getDepOrder() {
		return this.depOrder;
	}

	public void setDepOrder(Integer depOrder) {
		this.depOrder = depOrder;
	}

	public Long getParentOrganizationId() {
		return this.parentOrganizationId;
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getParentOrganizationTypeCode() {
		return this.parentOrganizationTypeCode;
	}

	public void setParentOrganizationTypeCode(String parentOrganizationTypeCode) {
		this.parentOrganizationTypeCode = parentOrganizationTypeCode;
	}

	public Long getSubOrganizationId() {
		return this.subOrganizationId;
	}

	public void setSubOrganizationId(Long subOrganizationId) {
		this.subOrganizationId = subOrganizationId;
	}

	public String getSubOrganizationType() {
		return this.subOrganizationType;
	}

	public void setSubOrganizationType(String subOrganizationType) {
		this.subOrganizationType = subOrganizationType;
	}

	public String getSubOrganizationTypeCode() {
		return this.subOrganizationTypeCode;
	}

	public void setSubOrganizationTypeCode(String subOrganizationTypeCode) {
		this.subOrganizationTypeCode = subOrganizationTypeCode;
	}

	public String getSubordinateOrganization() {
		return this.subordinateOrganization;
	}

	public void setSubordinateOrganization(String subordinateOrganization) {
		this.subordinateOrganization = subordinateOrganization;
	}

	@Transient
	public String getPerantDepartmentName() {
		String name = getSubordinateOrganization();

		if (name != null) {
			if (getSubOrganizationTypeCode().equals("1")) {
				int idx = name.indexOf("-");
				if (idx > 0) {
					name = name.substring(0, idx);
				}
			}  
			  
		
		}      
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depOrder == null) ? 0 : depOrder.hashCode());
		result = prime * result + ((parentOrganizationId == null) ? 0 : parentOrganizationId.hashCode());
		result = prime * result + ((parentOrganizationTypeCode == null) ? 0 : parentOrganizationTypeCode.hashCode());
		result = prime * result + ((subOrganizationId == null) ? 0 : subOrganizationId.hashCode());
		result = prime * result + ((subOrganizationType == null) ? 0 : subOrganizationType.hashCode());
		result = prime * result + ((subOrganizationTypeCode == null) ? 0 : subOrganizationTypeCode.hashCode());
		result = prime * result + ((subordinateOrganization == null) ? 0 : subordinateOrganization.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentGrp other = (DepartmentGrp) obj;
		if (depOrder == null) {
			if (other.depOrder != null)
				return false;
		} else if (!depOrder.equals(other.depOrder))
			return false;
		if (parentOrganizationId == null) {
			if (other.parentOrganizationId != null)
				return false;
		} else if (!parentOrganizationId.equals(other.parentOrganizationId))
			return false;
		if (parentOrganizationTypeCode == null) {
			if (other.parentOrganizationTypeCode != null)
				return false;
		} else if (!parentOrganizationTypeCode.equals(other.parentOrganizationTypeCode))
			return false;
		if (subOrganizationId == null) {
			if (other.subOrganizationId != null)
				return false;
		} else if (!subOrganizationId.equals(other.subOrganizationId))
			return false;
		if (subOrganizationType == null) {
			if (other.subOrganizationType != null)
				return false;
		} else if (!subOrganizationType.equals(other.subOrganizationType))
			return false;
		if (subOrganizationTypeCode == null) {
			if (other.subOrganizationTypeCode != null)
				return false;
		} else if (!subOrganizationTypeCode.equals(other.subOrganizationTypeCode))
			return false;
		if (subordinateOrganization == null) {
			if (other.subordinateOrganization != null)
				return false;
		} else if (!subordinateOrganization.equals(other.subordinateOrganization))
			return false;
		return true;
	}
	
	

}