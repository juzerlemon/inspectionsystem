package ae.gov.dubaipolice.inspectionpoints.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ae.gov.dubaipolice.inspectionpoints.entities.InspectionDocument;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;
import ae.gov.dubaipolice.inspectionpoints.repositories.InspectionDocumentRepository;

@Service
@Transactional
public class InspectionDocumentService {
	@Autowired
	private InspectionDocumentRepository inspectionDocumentRepository;

	public InspectionDocument find(Long id) {
		return inspectionDocumentRepository.findOne(id);
	}

	public List<InspectionDocument> listFromProcedure(InspectionMain inspection) {
		List<InspectionDocument> documents = inspectionDocumentRepository.listFromMainInspection(inspection.getId());
		return documents != null && documents.size() >0 ? documents : new ArrayList<InspectionDocument>();
	}
	
}
