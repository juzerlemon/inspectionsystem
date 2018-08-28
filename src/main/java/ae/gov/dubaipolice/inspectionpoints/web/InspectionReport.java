package ae.gov.dubaipolice.inspectionpoints.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

public class InspectionReport {

	private String reportName;
	
	private String reportType;
	
	private String reportCode;
	
	private String reportDesc;
	
	List<InspectionReport> lists=null;
	
	@PostConstruct
	public void init() {
		lists=new ArrayList<>();
		InspectionReport ip1=new InspectionReport();
		ip1.setReportName("jamal1");
		ip1.setReportType("pdf1");
		ip1.setReportCode("1234");
		ip1.setReportDesc("jasper1");
		
		InspectionReport ip2=new InspectionReport();
		ip1.setReportName("jamal2");
		ip1.setReportType("pdf2");
		ip1.setReportCode("123455");
		ip1.setReportDesc("jasper2");
		
		InspectionReport ip3=new InspectionReport();
		ip1.setReportName("jamal3");
		ip1.setReportType("pdf3");
		ip1.setReportCode("123466");
		ip1.setReportDesc("jasper3");
		
		InspectionReport ip4=new InspectionReport();
		ip1.setReportName("jamal4");
		ip1.setReportType("pdf4");
		ip1.setReportCode("123477");
		ip1.setReportDesc("jasper4");
		
		lists.add(ip1);
		lists.add(ip2);
		lists.add(ip3);
		lists.add(ip4);
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
	public List<InspectionReport> getLists() {
		return lists;
	}

	public void setLists(List<InspectionReport> lists) {
		this.lists = lists;
	}
	
	
}
