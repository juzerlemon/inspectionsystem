package ae.gov.dubaipolice.inspectionpoints.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ae.gov.dubaipolice.inspectionpoints.entities.InspectionDocument;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;

public interface InspectionDocumentRepository extends JpaRepository<InspectionDocument, Long> {

	@Query("SELECT s FROM InspectionDocument s WHERE s.inspectionMain.id = :inspectionid ORDER BY s.id")
	public List<InspectionDocument> listFromMainInspection(@Param("inspectionid") Long inspectionid);
	
	
}
