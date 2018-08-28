   package ae.gov.dubaipolice.inspectionpoints.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the INSPECTIONPOINT database table.
 * 
 */
@Entity
@NamedQuery(name="Inspectionpoint.findAll", query="SELECT i FROM Inspectionpoint i")
@Setter
@Getter
public class Inspectionpoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSPECTIONPOINT_POINTID_GENERATOR",allocationSize = 1, sequenceName="INSPECTIONPOINT_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSPECTIONPOINT_POINTID_GENERATOR")
	@Column(name="POINT_ID")
	private Long id;

	@Column(name="INSPECTION_ID")
	private Integer inspectionId;

	@Column(name="POINT_NAME")
	private String pointName;
	
	@Column(name="TARGETED")
	private String targeted;
	
	@Column(name="INVESTIGATOR")
	private String investigator;
	
	@Column(name="UNINVESTIGATOR")
	private String unInvestigator;
	
	@Column(name="ACTION_TAKEN")
	private String actionTaken;
	
	@Column(name="RECOMMANDATION")
	private String recommandation;

	@Column(name="IS_LEADER")
	private Integer leaderFlag;
	
	@Column(name="IS_SUPERVISOR")
	private Integer supervisorFlag;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_DATE")
	private Date ipEditedDate;

	@Column(name="MODIFIED_BY")
	private Long ipEditedby;

	@Column(name="CREATED_BY")
	private Long ipEnteredby;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date ipEntereddate;

	private String leader;
	
	@Transient
	private boolean leaderBol;
	
	@Transient
	private boolean supervisorBol;
	
	@ManyToOne
	@JoinColumn(name = "POINT_NO")
	private InspectionMain inspectionMain;

	private String supervisor;

	public Inspectionpoint() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inspectionId == null) ? 0 : inspectionId.hashCode());
		result = prime * result + ((inspectionMain == null) ? 0 : inspectionMain.hashCode());
		result = prime * result + ((ipEditedDate == null) ? 0 : ipEditedDate.hashCode());
		result = prime * result + ((ipEditedby == null) ? 0 : ipEditedby.hashCode());
		result = prime * result + ((ipEnteredby == null) ? 0 : ipEnteredby.hashCode());
		result = prime * result + ((ipEntereddate == null) ? 0 : ipEntereddate.hashCode());
		result = prime * result + ((leader == null) ? 0 : leader.hashCode());
		result = prime * result + ((pointName == null) ? 0 : pointName.hashCode());
		result = prime * result + ((supervisor == null) ? 0 : supervisor.hashCode());
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
		Inspectionpoint other = (Inspectionpoint) obj;
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
		if (inspectionMain == null) {
			if (other.inspectionMain != null)
				return false;
		} else if (!inspectionMain.equals(other.inspectionMain))
			return false;
		if (ipEditedDate == null) {
			if (other.ipEditedDate != null)
				return false;
		} else if (!ipEditedDate.equals(other.ipEditedDate))
			return false;
		if (ipEditedby == null) {
			if (other.ipEditedby != null)
				return false;
		} else if (!ipEditedby.equals(other.ipEditedby))
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
		if (leader == null) {
			if (other.leader != null)
				return false;
		} else if (!leader.equals(other.leader))
			return false;
		if (pointName == null) {
			if (other.pointName != null)
				return false;
		} else if (!pointName.equals(other.pointName))
			return false;
		if (supervisor == null) {
			if (other.supervisor != null)
				return false;
		} else if (!supervisor.equals(other.supervisor))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Integer inspectionId) {
		this.inspectionId = inspectionId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getTargeted() {
		return targeted;
	}

	public void setTargeted(String targeted) {
		this.targeted = targeted;
	}

	public String getInvestigator() {
		return investigator;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}

	public String getUnInvestigator() {
		return unInvestigator;
	}

	public void setUnInvestigator(String unInvestigator) {
		this.unInvestigator = unInvestigator;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getRecommandation() {
		return recommandation;
	}

	public void setRecommandation(String recommandation) {
		this.recommandation = recommandation;
	}

	public Integer getLeaderFlag() {
		return leaderFlag;
	}

	public void setLeaderFlag(Integer leaderFlag) {
		this.leaderFlag = leaderFlag;
	}

	public Integer getSupervisorFlag() {
		return supervisorFlag;
	}

	public void setSupervisorFlag(Integer supervisorFlag) {
		this.supervisorFlag = supervisorFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getIpEditedDate() {
		return ipEditedDate;
	}

	public void setIpEditedDate(Date ipEditedDate) {
		this.ipEditedDate = ipEditedDate;
	}

	public Long getIpEditedby() {
		return ipEditedby;
	}

	public void setIpEditedby(Long ipEditedby) {
		this.ipEditedby = ipEditedby;
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

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public boolean isLeaderBol() {
		return leaderBol;
	}

	public void setLeaderBol(boolean leaderBol) {
		this.leaderBol = leaderBol;
	}

	public boolean isSupervisorBol() {
		return supervisorBol;
	}

	public void setSupervisorBol(boolean supervisorBol) {
		this.supervisorBol = supervisorBol;
	}

	public InspectionMain getInspectionMain() {
		return inspectionMain;
	}

	public void setInspectionMain(InspectionMain inspectionMain) {
		this.inspectionMain = inspectionMain;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	

}