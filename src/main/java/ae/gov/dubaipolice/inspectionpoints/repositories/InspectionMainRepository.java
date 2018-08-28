package ae.gov.dubaipolice.inspectionpoints.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ae.gov.dubaipolice.inspectionpoints.entities.DepartmentGrp;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;

public interface InspectionMainRepository extends JpaRepository<InspectionMain, Long> {
	
	@Query("SELECT g FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND "
			+ "(UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) AND (g.ipEntereddate between :fromDate and :toDate ) ORDER BY g.id desc" )
	public List<InspectionMain> search(@Param("inspectionId")Integer inspectionId,@Param("reportName")String reportName,@Param("searchYear")Integer searchYear ,@Param("searchgeneralDepartment")DepartmentGrp searchgeneralDepartment, @Param("fromDate")Date fromDate ,@Param("toDate")Date toDate  );
	
	
	@Query("SELECT g FROM InspectionMain g WHERE (:inspectionId=null or g.inspectionId= :inspectionId) AND "
			+ "(UPPER(g.reportName) like UPPER(concat('%',:reportName,'%'))) AND (:searchYear=null or g.inspectionYear= :searchYear) AND (:searchgeneralDepartment=null or g.departmentGrp= :searchgeneralDepartment) ORDER BY g.id desc" )
	public List<InspectionMain> searchInspection(@Param("inspectionId")Integer inspectionId,@Param("reportName")String reportName,@Param("searchYear")Integer searchYear ,@Param("searchgeneralDepartment")DepartmentGrp searchgeneralDepartment);
	
	@Query("SELECT d FROM InspectionMain d WHERE d.id = :inspectionid ORDER BY d.id")
	public InspectionMain listFromMainInspectionId(@Param("inspectionid")Long inspectionid);
	
	
	
}
