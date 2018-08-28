package ae.gov.dubaipolice.inspectionpoints.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ae.gov.dubaipolice.inspectionpoints.entities.DepartmentGrp;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionFlow;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;
import ae.gov.dubaipolice.inspectionpoints.entities.Inspectionpoint;
import ae.gov.dubaipolice.inspectionpoints.repositories.DepartmentGrpRepository;
import ae.gov.dubaipolice.inspectionpoints.repositories.InspectionDocumentRepository;
import ae.gov.dubaipolice.inspectionpoints.repositories.InspectionFlowRepository;
import ae.gov.dubaipolice.inspectionpoints.repositories.InspectionMainRepository;
import ae.gov.dubaipolice.inspectionpoints.repositories.InspectionPointRepository;

@Service
@Transactional
public class InspectionMainService {

	
	@Autowired
	private InspectionMainRepository inspectionMainRepository;
	@Autowired
	private InspectionPointRepository inspectionPointRepository;
	@Autowired
	private DepartmentGrpRepository departmentGrpRepository;
	
	@Autowired
	private InspectionDocumentRepository inspectionDocumentRepository;
	
	@Autowired
	private InspectionFlowRepository inspectionFlowRepository;
	
	@Inject
	EntityManager entityManager;
		

	public List<InspectionMain> findAllMainIspection() {
		List<InspectionMain> inspectionMainList = inspectionMainRepository.findAll(sortByIdDesc());
		return inspectionMainList != null && inspectionMainList.size() > 0 ? inspectionMainList : new ArrayList<InspectionMain>();
	}
	
	
	public List<InspectionMain> findAllInspection() {
		return inspectionMainRepository.findAll();
	}
	
	public InspectionMain findOneMainInspection(Long key) {
		return inspectionMainRepository.findOne(key);
	}
	
	public List<Inspectionpoint> findAllPoint() {
		return inspectionPointRepository.findAll();
	}

	private Sort sortByIdDesc() {	
		return new Sort(Sort.Direction.DESC, "id");
	}

	public InspectionMain find(Long id) {
		return inspectionMainRepository.findOne(id);
	}

	public InspectionMain update(InspectionMain mainInspection) {
		List<Inspectionpoint> inspections = inspectionPointRepository.listFromMainInspection(mainInspection);
		for (Inspectionpoint ins : mainInspection.getInspectionPoints())
			if (ins.getId() != null)
				for (Inspectionpoint inspcsn : inspections)
					if (ins.getId().equals(inspcsn.getId())) {
						inspections.remove(inspcsn);
						break;
					}
		inspectionPointRepository.delete(inspections);
		
		/*List<InspectionDocument> documents = inspectionDocumentRepository.listFromMainInspection(mainInspection.getId());
		List<InspectionDocument> documentList = new ArrayList<InspectionDocument>();
		for (InspectionDocument document : mainInspection.getInspectionDocuments()) {
			for (InspectionDocument documentExist : documents) {
				if (document.getId() != null && document.getId().equals(documentExist.getId())) {
					documents.remove(documentExist);
					break;
				}
			}
			if (document.getId() == null) {
				documentList.add(document);
			}
		}
		inspectionDocumentRepository.delete(documents);
		mainInspection.setInspectionDocuments(documentList);*/
		
		return inspectionMainRepository.saveAndFlush(mainInspection);
	}

	public void save(InspectionMain inspectionMain) {
		inspectionMainRepository.saveAndFlush(inspectionMain);
	}
	
	
	public void saveFlow(InspectionFlow inspectionFlow) {
		inspectionFlowRepository.saveAndFlush(inspectionFlow);
	}

	public List<InspectionMain> search(Integer inspectionNumber, String reportName, Integer searchYear, DepartmentGrp searchgeneralDepartment, Date fromDate, Date toDate) {
		if(null!=fromDate || null!=toDate) {
			return inspectionMainRepository.search(inspectionNumber,reportName,searchYear,searchgeneralDepartment,fromDate,toDate);
		}else {
			return inspectionMainRepository.searchInspection(inspectionNumber,reportName,searchYear,searchgeneralDepartment);
		}
	}

	public List<Inspectionpoint> InspectionMainWithId(Long inspectionId) {
		InspectionMain inspectionMain=inspectionMainRepository.listFromMainInspectionId(inspectionId);
		return inspectionMain!=null?inspectionMain.getInspectionPoints():null;
	}
	
	public List<DepartmentGrp> findGeneralDepartment(){
		return departmentGrpRepository.listAllGeneralDepartment();
	}


	public void delete(InspectionMain inspection) {
		inspectionMainRepository.delete(inspection);
		
	}
	
	 public int inspectionMainCount(InspectionMain inspectionMain, Date fromDate, Date toDate) {
		    String queryValue="";
		    if(null!=fromDate) {
		    	queryValue = "SELECT count(g.id) FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND (UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) AND (g.ipEntereddate between :fromDate and :toDate ) ORDER BY g.id desc";
		    }else {
		    	queryValue = "SELECT count(g.id) FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND (UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) ORDER BY g.id desc";
		    }
			javax.persistence.Query query = entityManager.createQuery(queryValue);
			query.setParameter("inspectionId", inspectionMain.getId());
			query.setParameter("reportName", inspectionMain.getReportName());
			query.setParameter("searchYear", inspectionMain.getInspectionYear());
			query.setParameter("searchgeneralDepartment", inspectionMain.getDepartmentGrp());
			if (null!=fromDate) {
				query.setParameter("fromDate", fromDate);
				query.setParameter("toDate", toDate);
			}
		/*query.setParameter("case_id", airwingMission.getId());
		 query.setParameter("desc", airwingMission.getDesc());*/
		 return ((Long)query.getSingleResult()).intValue();
	    }
	 
	 public List<InspectionMain> searchInspectionList(InspectionMain inspectionMain, int start,int size,Date fromDate, Date toDate){
			String queryValue="";
			if (null!=fromDate) {
				queryValue = "SELECT g FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND (UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) AND (g.ipEntereddate between :fromDate and :toDate ) ORDER BY g.id desc";
			}else {
				queryValue = "SELECT g FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND (UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) ORDER BY g.id desc";
			}
				
				javax.persistence.Query query = entityManager.createQuery(queryValue);
				query.setParameter("inspectionId", inspectionMain.getId());
				query.setParameter("reportName", inspectionMain.getReportName());
				query.setParameter("searchYear", inspectionMain.getInspectionYear());
				query.setParameter("searchgeneralDepartment", inspectionMain.getDepartmentGrp());
				if (null!=fromDate) {
					query.setParameter("fromDate", fromDate);
					query.setParameter("toDate", toDate);
				}
		        query.setFirstResult(start);
		        query.setMaxResults(size);
				return query.getResultList();
			}
		
	 
	 
	/* @Query("SELECT g FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND "
				+ "(UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) AND (g.ipEntereddate between :fromDate and :toDate ) ORDER BY g.id desc" )
		public List<InspectionMain> search(@Param("inspectionId")Integer inspectionId,@Param("reportName")String reportName,@Param("searchYear")Integer searchYear ,@Param("searchgeneralDepartment")DepartmentGrp searchgeneralDepartment, @Param("fromDate")Date fromDate ,@Param("toDate")Date toDate  );
		*/
	
}
