package ae.gov.dubaipolice.inspectionpoints.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import ae.gov.dubaipolice.base.model.LoginUser;
import ae.gov.dubaipolice.base.web.LocaleBean;
import ae.gov.dubaipolice.inspectionpoints.entities.DepartmentGrp;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionDocument;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionFlow;
import ae.gov.dubaipolice.inspectionpoints.entities.InspectionMain;
import ae.gov.dubaipolice.inspectionpoints.entities.Inspectionpoint;
import ae.gov.dubaipolice.inspectionpoints.enums.YearList;
import ae.gov.dubaipolice.inspectionpoints.services.InspectionDocumentService;
import ae.gov.dubaipolice.inspectionpoints.services.InspectionMainService;
import ae.gov.dubaipolice.inspectionpoints.services.InspectionPointService;
import ae.gov.dubaipolice.inspectionpoints.utils.InspectionUtils;
import ae.gov.dubaipolice.util.JsfUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Named(value = "inspectionBean")
@Scope("view")
public class InspectionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(InspectionBean.class);

	@Autowired
	private InspectionMainService inspectionMainService;
	@Autowired
	private InspectionPointService inspectionPointService;
	@Autowired
	InspectionDocumentService inspectionDocumentService;
	@Inject
	private LocaleBean localeBean;

	private LoginUser user = null;
	private List<InspectionMain> inspectionMainList;
	private List<Inspectionpoint> inspectionpointList;
	private InspectionMain inspectionMain;
	private Inspectionpoint inspectionpoint;

	private List<DepartmentGrp> generalDepartments;
	private DepartmentGrp generalDepartment;

	private Integer yearVal;
	private boolean isAdd;
	private boolean isView;
	private boolean isDetail;
	private boolean pnl_render;
	private boolean isModified;
	// for file upload start
	private List<InspectionDocument> inspectionDocuments;
	// for file upload End
	private String departmentName;
	private Boolean checkboxVal = false;
	private boolean rowEditorFlag;
	// search
	private String inspectionNumber=null;
	private String reportName=null;
	private Integer searchYear = null;
	private List<DepartmentGrp> searchgeneralDepartments = null;
	private DepartmentGrp searchgeneralDepartment = null;

	private Date fromDate = null;
	private Date toDate = null;
	private boolean pointEvalFlag;
	private boolean approvalFlag;
	private InspectionFlow inspectionFlow;
	
	
	private InspectionLazyDataModel dataModel=null;
	private InspectionMain lazyInspectionMain;

	@PostConstruct
	public void init() {
		user = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		this.isAdd = false;
		this.isView = false;
		this.isDetail = false;
		yearVal = Calendar.getInstance().get(Calendar.YEAR);
		fillYears();
		inspectionFlow=new InspectionFlow();
		/* fillDates(); */
		searchgeneralDepartments = inspectionMainService.findGeneralDepartment();
		lazyInspectionMain=new InspectionMain();
		dataModel=new InspectionLazyDataModel(inspectionMainService,lazyInspectionMain,searchgeneralDepartment,null,null);
	}

	private List<Integer> years;

	/**
	 * populate year
	 */
	protected void fillYears() {
		years = new ArrayList<>();
		for (YearList yearVal : YearList.values()) {
			years.add(yearVal.getYear());
		}
	}

	/**
	 * fill one year time span dates
	 */
	protected void fillDates() {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		setToDate(today);
		cal.add(Calendar.YEAR, -1);
		Date prevYear = cal.getTime();
		setFromDate(prevYear);
		setToDate(today);
	}

	/**
	 * This method is called on click on new botton
	 */

	public void addNew() {
		this.isModified = false;
		this.isAdd = true;
		this.isView = false;
		this.isDetail = false;
		generalDepartments = inspectionMainService.findGeneralDepartment();
		generalDepartment = null;
		this.inspectionMain = new InspectionMain();
		this.inspectionMain.setInspectionPoints(new ArrayList<Inspectionpoint>());
		inspectionMain.setInspectionYear(Calendar.getInstance().get(Calendar.YEAR));
		inspectionpointList = new ArrayList<>();
		inspectionpoint = new Inspectionpoint();

		// for file upload start
		// this.inspectionDocuments = new ArrayList<InspectionDocument>();
		// for file upload end
	}

	/**
	 * This method is used to serach with crateria
	 */
	public void search() {
		Integer inspectionNo;
		try {
			inspectionNo = Integer.parseInt("" + inspectionNumber);
		} catch (NumberFormatException e) {
			inspectionNo = null;
		}
		lazyInspectionMain=new InspectionMain();
		lazyInspectionMain.setInspectionId(inspectionNo);
		lazyInspectionMain.setReportName(reportName);
		lazyInspectionMain.setInspectionYear(searchYear);
		lazyInspectionMain.setDepartmentGrp(searchgeneralDepartment);
		
		if(null!=fromDate && toDate==null) {
			toDate=new Date();
		}
		if(null==fromDate) {
			toDate=null;
		}
		dataModel=new InspectionLazyDataModel(inspectionMainService,lazyInspectionMain,searchgeneralDepartment,fromDate,toDate);
	}

	/**
	 * This method is used to save record and update records
	 */

	public void save() {
		if (isValidate()) {
			// for file upload start
			// inspectionMain.setInspectionDocuments(inspectionDocuments);
			// for file upload end
			if (this.inspectionMain.getId() != null) {
				inspectionMain.setDepartmentGrp(generalDepartment);
				inspectionMain.setIpEditeddate(new Date());
				inspectionMain.setIpEditedby(user.getGrpNumber());
				inspectionMain.setInspectionPoints(inspectionpointList);
				/*inspectionFlow.setCurrentFlow(0);
				inspectionFlow.setNextFlow(0);
				inspectionFlow.setPreviousFlow(0);
				inspectionMain.setInspectionFlow(inspectionFlow);
				inspectionMainService.saveFlow(inspectionFlow);*/
				inspectionMainService.update(inspectionMain);
				JsfUtil.addSuccessMessage(InspectionUtils.getMessageKey("modify_message"));
			} else {
				inspectionMain.setDepartmentGrp(generalDepartment);
				inspectionMain.setInspectionPoints(inspectionpointList);
				inspectionMain.setIpEnteredby(user.getGrpNumber());
				inspectionMain.setIpEntereddate(new Date());
				inspectionFlow.setCurrentFlow(0);
				inspectionFlow.setNextFlow(1);
				inspectionFlow.setPreviousFlow(0);
				inspectionMain.setInspectionFlow(inspectionFlow);
				inspectionMainService.saveFlow(inspectionFlow);
				inspectionMainService.save(inspectionMain);
				JsfUtil.addSuccessMessage(InspectionUtils.getMessageKey("save_message"));
			}
			this.backToList();
		}
	}

	/**
	 * This is used to validate
	 * 
	 * @return
	 */
	private boolean isValidate() {
		return true;
	}

	/**
	 * This method is used when main record is edit
	 * 
	 * @param inspection
	 */
	public void editMainInspection(InspectionMain inspection) {
		this.isAdd = true;
		this.isView = false;
		this.isDetail = false;
		this.generalDepartments = inspectionMainService.findGeneralDepartment();
		this.generalDepartment = inspection.getDepartmentGrp();
		this.inspectionMain = inspection;

		inspectionpointList = inspectionPointService.listFromInspection(inspection);
		if (null != inspectionpointList && !inspectionpointList.isEmpty()) {
			for (Inspectionpoint ip : inspectionpointList) {
				ip.setLeaderBol(intToBoolean(ip.getLeaderFlag()));
				ip.setSupervisorBol(intToBoolean(ip.getSupervisorFlag()));
			}
		}
		inspectionpoint = new Inspectionpoint();
		// this.inspectionDocuments =
		// inspectionDocumentService.listFromProcedure(inspection);
	}

	

	/**
	 * This is used for edit inspection
	 * 
	 * @param inspectionpoint
	 */

	public void editDetailsInspectionPoint(Inspectionpoint inspectionpoint) {
		this.pnl_render = true;
		this.inspectionpoint = null;
		this.inspectionpoint = inspectionpoint;
		JsfUtil.showDialog("insdetailsDialog");
		// inspectionpointList.remove(inspectionpoint);
	}

	/**
	 * This method is used for viewing the records
	 * 
	 * @param guide
	 */

	public void viewMainInspection(InspectionMain inspection) {
		this.isAdd = false;
		this.isView = true;
		this.isDetail = false;
		this.inspectionMain = inspection;
		inspectionpointList = inspectionPointService.listFromInspection(inspection);
		Comparator<Inspectionpoint> c = (s1, s2) -> s1.getPointName().compareTo(s2.getPointName());
		inspectionpointList.sort(c);
		System.out.println(inspectionpointList);

	}

	/**
	 * This is used for add inspection
	 */
	public void addInspectionPoint() {
		if (null == inspectionpoint.getPointName() || inspectionpoint.getPointName().trim().length() < 1) {
			JsfUtil.addErrorMessage(InspectionUtils.getMessageKey("pointmsg"));
			return;
		} else {
			if (this.isModified) {
			this.inspectionpointList.removeIf(s -> s.getPointName().equals(this.inspectionpoint.getPointName()));
			inspectionpoint.setIpEditedby(user.getGrpNumber());
			inspectionpoint.setIpEditedDate(new Date());}
			inspectionpoint.setIpEnteredby(user.getGrpNumber());
			inspectionpoint.setIpEntereddate(new Date());
			inspectionpoint.setInspectionMain(inspectionMain);
			inspectionpoint.setLeaderFlag(inspectionpoint.isLeaderBol() == true ? 1 : 0);
			inspectionpoint.setSupervisorFlag(inspectionpoint.isSupervisorBol() == true ? 1 : 0);
			this.inspectionpointList.add(inspectionpoint);
			Comparator<Inspectionpoint> c = (s1, s2) -> s1.getPointName().compareTo(s2.getPointName());
			inspectionpointList.sort(c);
			inspectionpoint = new Inspectionpoint();
		}
	}

	/**
	 * This is used for delete inspection
	 * 
	 * @param inspection
	 */
	public void deleteInspectionPoint(Inspectionpoint inspection) {
		for (Inspectionpoint ip : inspectionpointList) {
			if (ip.equals(inspection)) {
				inspectionpointList.remove(ip);
				break;
			}
		}
	}

	/**
	 * This is used for edit inspection
	 * 
	 * @param inspectionpoint
	 */

	public void editInspectionPoint(Inspectionpoint inspectionpoint) {
		this.pnl_render = true;
		this.isModified = true;
		this.inspectionpoint = inspectionpoint;
		// inspectionpointList.remove(inspectionpoint);
	}

	public void onRowCancel(RowEditEvent event) {
		Inspectionpoint inspectionpoint = ((Inspectionpoint) event.getObject());
		this.inspectionpoint = inspectionpoint;
	}

	/**
	 * This is used to go back
	 */
	public void backToList() {
		this.init();
	}

	
	public void saveInspectionDeatils() {
		if (this.inspectionMain.getId() != null) {
			if (null == this.inspectionpoint.getPointName() || inspectionpoint.getPointName().trim().length() < 1) {
				JsfUtil.addErrorMessage(InspectionUtils.getMessageKey("pointmsg"));
				return;
			} 
			if (null != this.inspectionpoint && this.inspectionpointList.stream().anyMatch(s -> s.getId() == inspectionpoint.getId())) {
				inspectionpoint.setStatus(1);
			}
			inspectionMain.setDepartmentGrp(generalDepartment);
			inspectionMain.setIpEditeddate(new Date());
			inspectionMain.setIpEditedby(user.getGrpNumber());
			inspectionMain.setInspectionPoints(inspectionpointList);
			this.pointEvalFlag = inspectionpointList.stream().allMatch(s -> s.getStatus() == 1);
			if(this.pointEvalFlag) {
				inspectionMain.setIpStatus(1);
			}
			inspectionMainService.update(inspectionMain);
			JsfUtil.hideDialog("insdetailsDialog");
			Comparator<Inspectionpoint> c = (s1, s2) -> s1.getPointName().compareTo(s2.getPointName());
			inspectionpointList.sort(c);
			
			JsfUtil.addSuccessMessage(InspectionUtils.getMessageKey("modify_message"));
		}
	}

	
	/**
	 * This is used for delete opertion
	 * @param rowDataForDelete
	 */
	public void deleteItem(InspectionMain inspection){
		try{
			inspectionMainService.delete(inspection);
			//inspectionMainList = inspectionMainService.findAllMainIspection();
			lazyInspectionMain=new InspectionMain();
			dataModel=new InspectionLazyDataModel(inspectionMainService,lazyInspectionMain,searchgeneralDepartment,null,null);
			if (Constants.LANG_ARABIC.equals(localeBean.getLanguage())) {
				JsfUtil.addSuccessMessage("تم الحذف بنجاح");
			} else {
				JsfUtil.addErrorMessage("Successfully deleted");
			}
			}catch(Exception ex){
				LOG.error("<--- deleteItem method error --->"+ex.getMessage());
			}
	}
	/**
	 * This method is used when main record is details
	 * 
	 * @param inspection
	 */
	public void detailsMainInspection(InspectionMain inspection) {
		this.pnl_render = false;
		this.isAdd = false;
		this.isView = false;
		this.isDetail = true;
		this.generalDepartments = inspectionMainService.findGeneralDepartment();
		this.generalDepartment = inspection.getDepartmentGrp();
		setDepartmentName(generalDepartment.getSubordinateOrganization());
		this.inspectionMain = inspection;
		inspectionpoint = new Inspectionpoint();
		inspectionpointList = inspectionPointService.listFromInspection(inspection);
		if (null != inspectionpointList && !inspectionpointList.isEmpty()) {
			for (Inspectionpoint ip : inspectionpointList) {
				ip.setLeaderBol(intToBoolean(ip.getLeaderFlag()));
				ip.setSupervisorBol(intToBoolean(ip.getSupervisorFlag()));
				if (ip.getStatus() == null) {
					ip.setStatus(0);
				}
			}
			this.pointEvalFlag = inspectionpointList.stream().allMatch(s -> s.getStatus() == 1);
			//user.setGrpNumber((long)23505);//first level
			//user.setGrpNumber((long)10660);//second level
			//user.setGrpNumber((long)9161);//third level
			
			/*if (this.pointEvalFlag) {
				long level = 0==inspection.getInspectionFlow().getNextFlow()?1:inspection.getInspectionFlow().getNextFlow();
				if (50!=level) {
					List<Object[]> mangerList = inspectionPointService.findManagerHierarchyWithLevelId(inspection.getIpEnteredby(), level);
					Object[] objects = mangerList.get(0==inspection.getInspectionFlow().getNextFlow()?0:inspection.getInspectionFlow().getNextFlow() - 1);
					if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						this.approvalFlag = true;
					} 
				}
			}*/
		}
		// this.inspectionDocuments =
		// inspectionDocumentService.listFromProcedure(inspection);
	}
	/**
	 * This method is used for inspection approval
	 */
	public void requestForApproval() {
		List<Object[]> mangerList=null;
		//user.setGrpNumber((long)23505);//first level
		user.setGrpNumber((long)10660);//second level
		//user.setGrpNumber((long)9161);//third level
		
		//First level approval
		//mangerList = inspectionPointService.findManagerHierarchyWithLevelId((long)16408,(long)1);
		//Second level approval
		//mangerList = inspectionPointService.findManagerHierarchyWithLevelId((long)16408,(long)2);
		//Third level approval
		mangerList = inspectionPointService.findManagerHierarchyWithLevelId((long)16408,(long)3);
		LOG.info("<--manager grp no size-->" + mangerList);
		if (null != mangerList) {
			for (Object[] objects : mangerList) {
				BigDecimal value=(BigDecimal)objects[4];
				switch (Integer.valueOf(value.intValue())) {
				  case 1 :  
					  if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						InspectionFlow iflow=this.inspectionMain.getInspectionFlow();
						iflow.setCurrentFlow(1);
						iflow.setNextFlow(2);
						iflow.setPreviousFlow(0);
						inspectionMainService.saveFlow(iflow);
						this.inspectionMain.setInspectionFlow(iflow);
						inspectionMainService.save(this.inspectionMain);
						}break;
				  
				  case 2 : 
					  if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						InspectionFlow iflow=this.inspectionMain.getInspectionFlow();
						iflow.setCurrentFlow(2);
						iflow.setNextFlow(3);
						iflow.setPreviousFlow(1);
						inspectionMainService.saveFlow(iflow);
						this.inspectionMain.setInspectionFlow(iflow);
						inspectionMainService.save(this.inspectionMain);
						}break;
				  
				  case 3 : 
					  if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						InspectionFlow iflow=this.inspectionMain.getInspectionFlow();
						iflow.setCurrentFlow(3);
						iflow.setNextFlow(3);
						iflow.setPreviousFlow(2);
						inspectionMainService.saveFlow(iflow);
						this.inspectionMain.setInspectionFlow(iflow);
						inspectionMainService.save(this.inspectionMain);
						}break;
				}
				
			}
		}
	}
	/**
	 * This method is used for reject inspection
	 */
	public void requestForReject() {
		List<Object[]> mangerList=null;
		user.setGrpNumber((long)23505);//first level
		//user.setGrpNumber((long)10660);//second level
		//user.setGrpNumber((long)9161);//third level
		
		//Three level approval
		mangerList = inspectionPointService.findManagerHierarchyWithLevelId((long)16408,(long)3);
		LOG.info("<--manager grp no size-->" + mangerList);
		if (null != mangerList) {
			for (Object[] objects : mangerList) {
				BigDecimal value=(BigDecimal)objects[4];
				switch (Integer.valueOf(value.intValue())) {
				  case 1 :  
					  if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						InspectionFlow iflow=this.inspectionMain.getInspectionFlow();
						iflow.setCurrentFlow(50);
						iflow.setNextFlow(0);
						iflow.setPreviousFlow(0);
						inspectionMainService.saveFlow(iflow);
						this.inspectionMain.setInspectionFlow(iflow);
						inspectionMainService.save(this.inspectionMain);
						}break;
				  
				  case 2 : 
					  if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						InspectionFlow iflow=this.inspectionMain.getInspectionFlow();
						iflow.setCurrentFlow(1);
						iflow.setNextFlow(2);
						iflow.setPreviousFlow(1);
						inspectionMainService.saveFlow(iflow);
						this.inspectionMain.setInspectionFlow(iflow);
						inspectionMainService.save(this.inspectionMain);
						}break;
				  
				  case 3 : 
					  if (String.valueOf(user.getGrpNumber()).equals("" + objects[1])) {
						InspectionFlow iflow=this.inspectionMain.getInspectionFlow();
						iflow.setCurrentFlow(2);
						iflow.setNextFlow(3);
						iflow.setPreviousFlow(2);
						inspectionMainService.saveFlow(iflow);
						this.inspectionMain.setInspectionFlow(iflow);
						inspectionMainService.save(this.inspectionMain);
						}break;
				}
				
			}
		}
	}
	
	public LazyDataModel<InspectionMain> getModel(){
        return dataModel;
    }
	
	public void updateLeadefFlag(boolean leaderFlag) {
		inspectionpoint.setLeaderBol(leaderFlag);
		System.out.println("<--leaderFlag Boool value is --->" + leaderFlag);
	}

	public void updateSupervisorFlag(boolean supFlag) {
		inspectionpoint.setSupervisorBol(supFlag);
		System.out.println("<--supFlag Boool value is --->" + supFlag);
	}

	public int boolToInt(boolean booleanVal) {
		return booleanVal ? 1 : 0;
	}

	public boolean intToBoolean(int intVal) {
		return intVal == 1 ? true : false;
	}

	public InspectionMainService getInspectionMainService() {
		return inspectionMainService;
	}

	public void setInspectionMainService(InspectionMainService inspectionMainService) {
		this.inspectionMainService = inspectionMainService;
	}

	public InspectionPointService getInspectionPointService() {
		return inspectionPointService;
	}

	public void setInspectionPointService(InspectionPointService inspectionPointService) {
		this.inspectionPointService = inspectionPointService;
	}

	public InspectionDocumentService getInspectionDocumentService() {
		return inspectionDocumentService;
	}

	public void setInspectionDocumentService(InspectionDocumentService inspectionDocumentService) {
		this.inspectionDocumentService = inspectionDocumentService;
	}

	public LocaleBean getLocaleBean() {
		return localeBean;
	}

	public void setLocaleBean(LocaleBean localeBean) {
		this.localeBean = localeBean;
	}

	public LoginUser getUser() {
		return user;
	}

	public void setUser(LoginUser user) {
		this.user = user;
	}

	public List<InspectionMain> getInspectionMainList() {
		return inspectionMainList;
	}

	public void setInspectionMainList(List<InspectionMain> inspectionMainList) {
		this.inspectionMainList = inspectionMainList;
	}

	public List<Inspectionpoint> getInspectionpointList() {
		return inspectionpointList;
	}

	public void setInspectionpointList(List<Inspectionpoint> inspectionpointList) {
		this.inspectionpointList = inspectionpointList;
	}

	public InspectionMain getInspectionMain() {
		return inspectionMain;
	}

	public void setInspectionMain(InspectionMain inspectionMain) {
		this.inspectionMain = inspectionMain;
	}

	public Inspectionpoint getInspectionpoint() {
		return inspectionpoint;
	}

	public void setInspectionpoint(Inspectionpoint inspectionpoint) {
		this.inspectionpoint = inspectionpoint;
	}

	public List<DepartmentGrp> getGeneralDepartments() {
		return generalDepartments;
	}

	public void setGeneralDepartments(List<DepartmentGrp> generalDepartments) {
		this.generalDepartments = generalDepartments;
	}

	public DepartmentGrp getGeneralDepartment() {
		return generalDepartment;
	}

	public void setGeneralDepartment(DepartmentGrp generalDepartment) {
		this.generalDepartment = generalDepartment;
	}

	public Integer getYearVal() {
		return yearVal;
	}

	public void setYearVal(Integer yearVal) {
		this.yearVal = yearVal;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public boolean isDetail() {
		return isDetail;
	}

	public void setDetail(boolean isDetail) {
		this.isDetail = isDetail;
	}

	public boolean isPnl_render() {
		return pnl_render;
	}

	public void setPnl_render(boolean pnl_render) {
		this.pnl_render = pnl_render;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	public List<InspectionDocument> getInspectionDocuments() {
		return inspectionDocuments;
	}

	public void setInspectionDocuments(List<InspectionDocument> inspectionDocuments) {
		this.inspectionDocuments = inspectionDocuments;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Boolean getCheckboxVal() {
		return checkboxVal;
	}

	public void setCheckboxVal(Boolean checkboxVal) {
		this.checkboxVal = checkboxVal;
	}

	public boolean isRowEditorFlag() {
		return rowEditorFlag;
	}

	public void setRowEditorFlag(boolean rowEditorFlag) {
		this.rowEditorFlag = rowEditorFlag;
	}

	public String getInspectionNumber() {
		return inspectionNumber;
	}

	public void setInspectionNumber(String inspectionNumber) {
		this.inspectionNumber = inspectionNumber;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(Integer searchYear) {
		this.searchYear = searchYear;
	}

	public List<DepartmentGrp> getSearchgeneralDepartments() {
		return searchgeneralDepartments;
	}

	public void setSearchgeneralDepartments(List<DepartmentGrp> searchgeneralDepartments) {
		this.searchgeneralDepartments = searchgeneralDepartments;
	}

	public DepartmentGrp getSearchgeneralDepartment() {
		return searchgeneralDepartment;
	}

	public void setSearchgeneralDepartment(DepartmentGrp searchgeneralDepartment) {
		this.searchgeneralDepartment = searchgeneralDepartment;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isPointEvalFlag() {
		return pointEvalFlag;
	}

	public void setPointEvalFlag(boolean pointEvalFlag) {
		this.pointEvalFlag = pointEvalFlag;
	}

	public boolean isApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(boolean approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public InspectionFlow getInspectionFlow() {
		return inspectionFlow;
	}

	public void setInspectionFlow(InspectionFlow inspectionFlow) {
		this.inspectionFlow = inspectionFlow;
	}

	public InspectionLazyDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(InspectionLazyDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public InspectionMain getLazyInspectionMain() {
		return lazyInspectionMain;
	}

	public void setLazyInspectionMain(InspectionMain lazyInspectionMain) {
		this.lazyInspectionMain = lazyInspectionMain;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	/*
	 * public void uploadFile(FileUploadEvent event) throws IOException { byte[]
	 * fileContent = event.getFile().getContents(); Blob blob = null; try { blob =
	 * new SerialBlob(fileContent); } catch (SerialException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (SQLException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } InspectionDocument
	 * securityDocument = new InspectionDocument();
	 * securityDocument.setAttachment(blob);
	 * securityDocument.setDocName(event.getFile().getFileName());
	 * securityDocument.setCreatedDate(new Date());
	 * securityDocument.setInspectionMain(inspectionMain);
	 * inspectionDocuments.add(securityDocument);
	 * 
	 * }
	 * 
	 * public void deleteAttachment(InspectionDocument inspectionDocument) {
	 * inspectionDocuments.remove(inspectionDocument); }
	 * 
	 * public void download(InspectionDocument inspectionDocument) throws
	 * IOException { Blob blob = inspectionDocument.getAttachment(); byte[] fileData
	 * = null; try { fileData = blob.getBytes(1, (int) blob.length()); } catch
	 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * FacesContext facesContext = FacesContext.getCurrentInstance();
	 * ExternalContext externalContext = facesContext.getExternalContext();
	 * HttpServletResponse response = (HttpServletResponse)
	 * externalContext.getResponse(); response.reset(); //
	 * response.setContentType("application/pdf"); //
	 * response.setHeader("Content-disposition", "inline; filename=\"" + //
	 * attachment.getAttachmentName() + "\"");
	 * response.setHeader("Content-disposition", "attachment; filename=\"" +
	 * inspectionDocument.getDocName()+ "\""); OutputStream output =
	 * response.getOutputStream(); output.write(fileData); output.close();
	 * facesContext.responseComplete(); }
	 */

	/*
	 * public void onRowEdit(RowEditEvent event) { this.rowEditorFlag=true;
	 * Inspectionpoint inspectionpoint=((Inspectionpoint) event.getObject());
	 * this.inspectionpoint=inspectionpoint; AjaxBehaviorEvent evt =
	 * (AjaxBehaviorEvent)event; DataTable table = (DataTable) evt.getSource(); int
	 * activeRow = table.getRowIndex(); //do whatever you want with it
	 * RequestContext.getCurrentInstance().update(
	 * "inspectionForm:tbl_details_inspection:"+activeRow+":testid"); } public void
	 * onRowinitEdit(int rowIndex) { FacesContext context =
	 * FacesContext.getCurrentInstance(); Map map =
	 * context.getExternalContext().getRequestParameterMap(); String pIndex =
	 * (String) map.get("index"); int index = Integer.parseInt(pIndex);
	 * RequestContext.getCurrentInstance().update(
	 * "inspectionForm:tbl_details_inspection:"+rowIndex+":testid");
	 * this.rowEditorFlag=true; }
	 */
	
	
	
}
