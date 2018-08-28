package ae.gov.dubaipolice.inspectionpoints.entities;

import java.io.Serializable;
import java.sql.Blob;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the INSPECTION_DOCUMENT database table.
 * 
 */
@Entity
@Table(name="INSPECTION_DOCUMENT")
@NamedQuery(name="InspectionDocument.findAll", query="SELECT i FROM InspectionDocument i")
@Setter
@Getter
public class InspectionDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSPECTION_DOCUMENT_DOCID_GENERATOR", sequenceName="INSPECTION_DOC_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSPECTION_DOCUMENT_DOCID_GENERATOR")
	@Column(name="DOC_ID")
	private Long id;

	@Column(name = "ATTACHMENT")
	private Blob attachment;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String description;

	@Column(name="DOC_NAME")
	private String docName;

	private Long modifiedby;

	@Temporal(TemporalType.DATE)
	@Column(name="NODIFIED_DATE")
	private Date nodifiedDate;

	/*@Column(name="POINT_ID")
	private Long pointId;*/
	
	@ManyToOne
	@JoinColumn(name = "POINT_ID")
	private InspectionMain inspectionMain;

	public InspectionDocument() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((docName == null) ? 0 : docName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inspectionMain == null) ? 0 : inspectionMain.hashCode());
		result = prime * result + ((modifiedby == null) ? 0 : modifiedby.hashCode());
		result = prime * result + ((nodifiedDate == null) ? 0 : nodifiedDate.hashCode());
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
		InspectionDocument other = (InspectionDocument) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (docName == null) {
			if (other.docName != null)
				return false;
		} else if (!docName.equals(other.docName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inspectionMain == null) {
			if (other.inspectionMain != null)
				return false;
		} else if (!inspectionMain.equals(other.inspectionMain))
			return false;
		if (modifiedby == null) {
			if (other.modifiedby != null)
				return false;
		} else if (!modifiedby.equals(other.modifiedby))
			return false;
		if (nodifiedDate == null) {
			if (other.nodifiedDate != null)
				return false;
		} else if (!nodifiedDate.equals(other.nodifiedDate))
			return false;
		return true;
	}

	
	
}