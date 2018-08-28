package ae.gov.dubaipolice.inspectionpoints.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ae.gov.dubaipolice.base.entities.DpEmpMvGrp;
@Transactional
public interface DpGrpUserRepository extends JpaRepository<DpEmpMvGrp, Long>{
	@Query("SELECT d FROM DpEmpMvGrp d Where d.grpNumber = :grpNo")
	public abstract DpEmpMvGrp getFromGrpNo(@Param("grpNo") Long paramLong);

	@Query("select d.slmMilitaryFName from DpEmpMvGrp d where d.grpNumber=:grpNo")
	public abstract String findUserStaffType(@Param("grpNo") Long paramLong);

	@Query(value = "SELECT d.* FROM WEBLISHER.GRP_EMPLOYEES_MV d Where d.NATIONAL_IDENTIFIER = :nationalIdentifier", nativeQuery = true)
	public abstract DpEmpMvGrp getFromNationalIdentifier(@Param("nationalIdentifier") String paramString);
	
	@Query(value="SELECT r FROM DpEmpMvGrp r WHERE UPPER(r.slmNameE) like UPPER(concat('%',?1,'%'))")
	public List<DpEmpMvGrp> findContainUser(String query);

}
