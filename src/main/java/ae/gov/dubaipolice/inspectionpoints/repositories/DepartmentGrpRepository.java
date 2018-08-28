package ae.gov.dubaipolice.inspectionpoints.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ae.gov.dubaipolice.inspectionpoints.entities.DepartmentGrp;

	
public interface DepartmentGrpRepository extends JpaRepository<DepartmentGrp, Long> {

	/*select * from DP_HR_DEPARTMENTS_GRP dept where dept.SUB_ORGANIZATION_TYPE_CODE in(1,22) and dept.PARENT_ORGANIZATION_TYPE_CODE in (70,90)*/
	
	@Query(value = "select dept from DepartmentGrp dept where dept.subOrganizationTypeCode in (1,22,70,50)"
			+ " and dept.parentOrganizationTypeCode in (70,90) order by depOrder")
	public List<DepartmentGrp> mainSelect();

	
	 //General Deparatments
	 
	@Query(value = "select dept from DepartmentGrp dept where dept.subOrganizationTypeCode in (1,22)"
			+ " and dept.parentOrganizationTypeCode in (70,90) order by depOrder")
	public List<DepartmentGrp> listAllGeneralDepartment();

	
	 //Sub Deparatments
	 
	@Query(value = "select * from weblisher.DP_HR_DEPARTMENTS_GRP dep where SUB_ORGANIZATION_TYPE_CODE = 40 "
			+ "connect by prior DEP.SUB_ORGANIZATION_ID= DEP.PARENT_ORGANIZATION_ID "
			+ "start with DEP.SUB_ORGANIZATION_ID = ?1", nativeQuery = true)
	public List<DepartmentGrp> listAllSubDepartment(Long genDept);

	
	 //Branches
	 
	@Query(value = "select * from weblisher.DP_HR_DEPARTMENTS_GRP dep where SUB_ORGANIZATION_TYPE_CODE = 3 "
			+ "connect by prior DEP.SUB_ORGANIZATION_ID= DEP.PARENT_ORGANIZATION_ID "
			+ "start with DEP.SUB_ORGANIZATION_ID = ?1", nativeQuery = true)
	public List<DepartmentGrp> listAllSection(Long supDept);

	@Transactional
	@Query(value = "select SLM_NAME from GRP_EMPLOYEES_MV where GRP_SERL_NO = ?1", nativeQuery = true)
	public Object getUserNameByGrpId(long grp);

}
