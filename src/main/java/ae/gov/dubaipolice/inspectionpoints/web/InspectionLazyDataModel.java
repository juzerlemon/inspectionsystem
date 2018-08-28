package ae.gov.dubaipolice.inspectionpoints.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ae.gov.dubaipolice.inspectionpoints.entities.DepartmentGrp;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;
import ae.gov.dubaipolice.inspectionpoints.services.InspectionMainService;
import lombok.Getter;
import lombok.Setter;

public class InspectionLazyDataModel extends LazyDataModel<InspectionMain> {
	private static final long serialVersionUID = 1L;
	private InspectionMainService inspectionMainService; 
	private InspectionMain lazyInspectionMain;
	private DepartmentGrp searchgeneralDepartment;
	private Date fromDate;
	private Date toDate;
	
	public InspectionLazyDataModel(InspectionMainService inspectionMainService, InspectionMain lazyInspectionMain,DepartmentGrp searchgeneralDepartment, Date fromDate, Date toDate) {
		this.inspectionMainService=inspectionMainService;
         this.lazyInspectionMain=lazyInspectionMain;
         this.searchgeneralDepartment=searchgeneralDepartment;
         this.fromDate=fromDate;
         this.toDate=toDate;
		this.setRowCount(inspectionMainService.inspectionMainCount(lazyInspectionMain,fromDate,toDate));
	}

	 @Override
	    public List<InspectionMain> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) {
		 List<InspectionMain> list = inspectionMainService.searchInspectionList(lazyInspectionMain,first, pageSize,fromDate,toDate);
	        return list;
	    }
	 
	 @Override
	    public InspectionMain getRowData(String rowKey) {
	       if(rowKey != null){
	    	   return inspectionMainService.findOneMainInspection(Long.parseLong(rowKey));
	       }
	        return null;
	    }
	 
	    @Override
	    public Object getRowKey(InspectionMain sugg) {
	        return sugg.getId();
	    }
	 
	
}
