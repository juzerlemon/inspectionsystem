package ae.gov.dubaipolice.inspectionpoints.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;
import ae.gov.dubaipolice.inspectionpoints.entities.Inspectionpoint;

public interface InspectionPointRepository extends JpaRepository<Inspectionpoint, Integer> {
	@Query("SELECT d FROM Inspectionpoint d WHERE d.inspectionMain = :inspectionMain ORDER BY d.id")
	public List<Inspectionpoint> listFromMainInspection(@Param("inspectionMain")InspectionMain inspectionMain);
	
	@Query("SELECT d FROM Inspectionpoint d WHERE d.inspectionMain.id = :pointno ORDER BY d.id")
	public List<Inspectionpoint> listFromMainInspectionId(@Param("pointno")Long pointno);
}
