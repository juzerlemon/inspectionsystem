package ae.gov.dubaipolice.inspectionpoints.web;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;



import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


@Setter
@Getter
@Named(value = "reportsBean")
@Scope("view")
public class ReportsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(ReportsBean.class);

	private JasperPrint jasperPrint=null;
	private ServletOutputStream servletOutputStream=null;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportToFileFormat() {
	List<InspectionReport> lists=new ArrayList<>();
	Map<String,Object> parameterMap=new HashMap<>();
	
	InspectionReport t=new InspectionReport();
	t.setReportName("jamal");
	parameterMap.put("reportname", t);
		
	
	
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
	
	parameterMap.put("Dataset1", lists);
	
	Exporter exporter = null; 
	exporter =  (Exporter) new JRPdfExporter();
	/*switch (reportType){*/
	/*case ProjectConstants.REPORT_TYPE_PDF:
	
	break;
	case ProjectConstants.REPORT_TYPE_XLS:
	JRXlsExporter jRXlsExporter = new JRXlsExporter();
	SimpleXlsExporterConfiguration ec = new SimpleXlsExporterConfiguration();
	ec.setCreateCustomPalette( Boolean.TRUE );
	jRXlsExporter.setConfiguration(ec);
	exporter =  jRXlsExporter;
	reportName+=".xls";
	break;
	case ProjectConstants.REPORT_TYPE_PPTX:
	exporter =  new JRPptxExporter();
	reportName+=".pptx";
	break;
	case ProjectConstants.REPORT_TYPE_DOCX:
	exporter =  new JRDocxExporter();
	reportName+=".docx";
	break;
	case ProjectConstants.REPORT_TYPE_ODT:
	exporter =  new JROdtExporter();
	reportName+=".odt";
	break;*/
	/*}*/
	try {
	createReport(parameterMap,"/jasperreport/inspection_report.jasper","test.pdf");
	ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
	if(exporterInput!=null && exporter!=null) {
	exporter.setExporterInput(exporterInput);
	OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(servletOutputStream);
	if(exporterOutput!=null){ 
	exporter.setExporterOutput(exporterOutput); 
	exporter.exportReport();
	servletOutputStream.close();
	exporterOutput.close();
	}
	}
	FacesContext.getCurrentInstance().responseComplete();
	} catch (JRException | IOException e) {
	// TODO Auto-generated catch block
	((Throwable) e).printStackTrace();
	}
	}
	private void createReport(Map<String,Object> parameterMap,String jasperFile,String fileName) throws JRException, IOException {
	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	InputStream inputStream = servletContext.getResourceAsStream(jasperFile);
	if(inputStream!=null) {
	jasperPrint = JasperFillManager.fillReport(inputStream, parameterMap,new JREmptyDataSource());
	HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
	.getResponse();

	response.addHeader("Content-disposition", "attachment; filename=" + fileName);

	servletOutputStream = response.getOutputStream();
	inputStream.close();
	}

	}

	
	
}
