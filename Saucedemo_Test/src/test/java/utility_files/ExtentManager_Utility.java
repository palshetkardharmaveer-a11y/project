package utility_files;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager_Utility {
	public static ExtentReports extent;
	public static ExtentReports createInstance() {
		String fileName = "./report/TestReport.html";
		ExtentSparkReporter htmlreReporter = new ExtentSparkReporter(fileName);
		
		extent=new ExtentReports();
		extent.attachReporter(htmlreReporter);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Environmet", "QA");
		return extent;
	}

}
