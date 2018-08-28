package ae.gov.dubaipolice.inspectionpoints.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;
import ae.gov.dubaipolice.inspectionpoints.entities.Inspectionpoint;
import ae.gov.dubaipolice.inspectionpoints.repositories.InspectionPointRepository;

@Service
@Transactional
public class InspectionPointService {
	@Inject
	EntityManager entityManager;
	
	@Autowired
	private InspectionPointRepository InspectionpointRepository;

	public List<Inspectionpoint> findAllInspectionpoints() {
		List<Inspectionpoint> InspectionpointList = InspectionpointRepository.findAll(sortByIdDesc());
		return InspectionpointList != null && InspectionpointList.size() > 0 ? InspectionpointList : new ArrayList<Inspectionpoint>();
	}

	private Sort sortByIdDesc() {
		return new Sort(Sort.Direction.DESC, "id");
	}

	public Inspectionpoint find(Integer id) {
		return InspectionpointRepository.findOne(id);
	}


	public List<Inspectionpoint> listFromInspection(InspectionMain inspection) {
		return InspectionpointRepository.listFromMainInspection(inspection);
	}
	
	public List<Inspectionpoint> listFromInspectionId(Long inspectionId) {
		return InspectionpointRepository.listFromMainInspectionId(inspectionId);
	}

	public List<Inspectionpoint> findAll() {
		return InspectionpointRepository.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findManagerHierarchyWithLevelId(Long userId,Long level) {
		String queryVal = "select connect_by_root SUPERVISOR_FULL_NAME manger,connect_by_root SUPERVISOR_EMPLOYEE_NUMBER base2, GRP_SERL_NO manger_id, SLM_NAME employee_name ,Level  from WEBLISHER.GRP_EMPLOYEES_MV where  GRP_SERL_NO =:userId and level <=:level connect by prior GRP_SERL_NO = SUPERVISOR_EMPLOYEE_NUMBER order by LEVEL, SUPERVISOR_EMPLOYEE_NUMBER, GRP_SERL_NO";
		return (List<Object[]>) entityManager.createNativeQuery(queryVal).setParameter("userId", userId).setParameter("level", level).getResultList();

	}
}
