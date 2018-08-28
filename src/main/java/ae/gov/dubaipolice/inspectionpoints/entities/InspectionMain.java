package ae.gov.dubaipolice.inspectionpoints.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the INSPECTION_MAIN database table.
 * 
 */
@Entity
@Table(name="INSPECTION_MAIN")
@NamedQuery(name="InspectionMain.findAll", query="SELECT i FROM InspectionMain i")
@Setter
@Getter
public class InspectionMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSPECTION_MAIN_IPID_GENERATOR",allocationSize = 1, sequenceName="INSPECTION_MAIN_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSPECTION_MAIN_IPID_GENERATOR")
	@Column(name="IP_ID")
	private Long id;

	/*@Column(name="DEPARTMENT_ID")
	private Long departmentId;*/
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private  DepartmentGrp departmentGrp;

	@Column(name="INSPECTION_ID")
	private Integer inspectionId;

	@Column(name="INSPECTION_YEAR")
	private Integer inspectionYear;

	@Column(name="MODIFIED_BY")
	private Long ipEditedby;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_DATE")
	private Date ipEditeddate;

	@Column(name="CREATED_BY")
	private Long ipEnteredby;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date ipEntereddate;

	@Column(name="REPORT_NAME")
	private String reportName;
	
	@Column(name="IP_STATUS")
	private Integer ipStatus;
	
	/*@Column(name="FLOW_ID")
	private Integer flowId;*/
	
	@ManyToOne(fetch=FetchType.LAZY)                                              
	@JoinColumn(name = "FLOW_ID", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private  InspectionFlow inspectionFlow;
	
	@OneToMany(mappedBy = "inspectionMain", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Inspectionpoint> inspectionPoints;

	/*@OneToMany(mappedBy="inspectionMain", cascade=CascadeType.ALL)
	private List<InspectionDocument> inspectionDocuments;*/
	
	
	public InspectionMain() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departmentGrp == null) ? 0 : departmentGrp.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inspectionId == null) ? 0 : inspectionId.hashCode());
		result = prime * result + ((inspectionPoints == null) ? 0 : inspectionPoints.hashCode());
		result = prime * result + ((inspectionYear == null) ? 0 : inspectionYear.hashCode());
		result = prime * result + ((ipEditedby == null) ? 0 : ipEditedby.hashCode());
		result = prime * result + ((ipEditeddate == null) ? 0 : ipEditeddate.hashCode());
		result = prime * result + ((ipEnteredby == null) ? 0 : ipEnteredby.hashCode());
		result = prime * result + ((ipEntereddate == null) ? 0 : ipEntereddate.hashCode());
		result = prime * result + ((reportName == null) ? 0 : reportName.hashCode());
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
		InspectionMain other = (InspectionMain) obj;
		if (departmentGrp == null) {
			if (other.departmentGrp != null)
				return false;
		} else if (!departmentGrp.equals(other.departmentGrp))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inspectionId == null) {
			if (other.inspectionId != null)
				return false;
		} else if (!inspectionId.equals(other.inspectionId))
			return false;
		if (inspectionPoints == null) {
			if (other.inspectionPoints != null)
				return false;
		} else if (!inspectionPoints.equals(other.inspectionPoints))
			return false;
		if (inspectionYear == null) {
			if (other.inspectionYear != null)
				return false;
		} else if (!inspectionYear.equals(other.inspectionYear))
			return false;
		if (ipEditedby == null) {
			if (other.ipEditedby != null)
				return false;
		} else if (!ipEditedby.equals(other.ipEditedby))
			return false;
		if (ipEditeddate == null) {
			if (other.ipEditeddate != null)
				return false;
		} else if (!ipEditeddate.equals(other.ipEditeddate))
			return false;
		if (ipEnteredby == null) {
			if (other.ipEnteredby != null)
				return false;
		} else if (!ipEnteredby.equals(other.ipEnteredby))
			return false;
		if (ipEntereddate == null) {
			if (other.ipEntereddate != null)
				return false;
		} else if (!ipEntereddate.equals(other.ipEntereddate))
			return false;
		if (reportName == null) {
			if (other.reportName != null)
				return false;
		} else if (!reportName.equals(other.reportName))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DepartmentGrp getDepartmentGrp() {
		return departmentGrp;
	}

	public void setDepartmentGrp(DepartmentGrp departmentGrp) {
		this.departmentGrp = departmentGrp;
	}

	public Integer getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Integer inspectionId) {
		this.inspectionId = inspectionId;
	}

	public Integer getInspectionYear() {
		return inspectionYear;
	}

	public void setInspectionYear(Integer inspectionYear) {
		this.inspectionYear = inspectionYear;
	}

	public Long getIpEditedby() {
		return ipEditedby;
	}

	public void setIpEditedby(Long ipEditedby) {
		this.ipEditedby = ipEditedby;
	}

	public Date getIpEditeddate() {
		return ipEditeddate;
	}

	public void setIpEditeddate(Date ipEditeddate) {
		this.ipEditeddate = ipEditeddate;
	}

	public Long getIpEnteredby() {
		return ipEnteredby;
	}

	public void setIpEnteredby(Long ipEnteredby) {
		this.ipEnteredby = ipEnteredby;
	}

	public Date getIpEntereddate() {
		return ipEntereddate;
	}

	public void setIpEntereddate(Date ipEntereddate) {
		this.ipEntereddate = ipEntereddate;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getIpStatus() {
		return ipStatus;
	}

	public void setIpStatus(Integer ipStatus) {
		this.ipStatus = ipStatus;
	}

	public InspectionFlow getInspectionFlow() {
		return inspectionFlow;
	}

	public void setInspectionFlow(InspectionFlow inspectionFlow) {
		this.inspectionFlow = inspectionFlow;
	}

	public List<Inspectionpoint> getInspectionPoints() {
		return inspectionPoints;
	}

	public void setInspectionPoints(List<Inspectionpoint> inspectionPoints) {
		this.inspectionPoints = inspectionPoints;
	}

	

	
}