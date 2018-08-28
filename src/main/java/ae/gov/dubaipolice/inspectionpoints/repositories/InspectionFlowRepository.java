package ae.gov.dubaipolice.inspectionpoints.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ae.gov.dubaipolice.inspectionpoints.entities.InspectionFlow;

public interface InspectionFlowRepository extends JpaRepository<InspectionFlow, Integer> {
	
}
