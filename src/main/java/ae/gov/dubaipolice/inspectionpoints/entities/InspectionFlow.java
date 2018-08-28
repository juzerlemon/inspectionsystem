package ae.gov.dubaipolice.inspectionpoints.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the INSPECTION_FLOW database table.
 * 
 */
@Entity
@Table(name="INSPECTION_FLOW")
@NamedQuery(name="InspectionFlow.findAll", query="SELECT i FROM InspectionFlow i")
@Setter
@Getter
public class InspectionFlow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSPECTION_FLOW_FLOWID_GENERATOR", sequenceName="INSPECTION_FLOW_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSPECTION_FLOW_FLOWID_GENERATOR")
	@Column(name="FLOW_ID")
	private Integer id;

	@Column(name="CURRENT_FLOW")
	private Integer currentFlow;

	@Column(name="NEXT_FLOW")
	private Integer nextFlow;

	@Column(name="PREVIOUS_FLOW")
	private Integer previousFlow;

	public InspectionFlow() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentFlow == null) ? 0 : currentFlow.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nextFlow == null) ? 0 : nextFlow.hashCode());
		result = prime * result + ((previousFlow == null) ? 0 : previousFlow.hashCode());
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
		InspectionFlow other = (InspectionFlow) obj;
		if (currentFlow == null) {
			if (other.currentFlow != null)
				return false;
		} else if (!currentFlow.equals(other.currentFlow))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nextFlow == null) {
			if (other.nextFlow != null)
				return false;
		} else if (!nextFlow.equals(other.nextFlow))
			return false;
		if (previousFlow == null) {
			if (other.previousFlow != null)
				return false;
		} else if (!previousFlow.equals(other.previousFlow))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurrentFlow() {
		return currentFlow;
	}

	public void setCurrentFlow(Integer currentFlow) {
		this.currentFlow = currentFlow;
	}

	public Integer getNextFlow() {
		return nextFlow;
	}

	public void setNextFlow(Integer nextFlow) {
		this.nextFlow = nextFlow;
	}

	public Integer getPreviousFlow() {
		return previousFlow;
	}

	public void setPreviousFlow(Integer previousFlow) {
		this.previousFlow = previousFlow;
	}
	
	

	
}