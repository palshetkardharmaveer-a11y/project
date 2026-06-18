package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() throws InterruptedException {
        if (extent == null) {
            // ✅ Pause before initializing reporter
            Thread.sleep(500);

            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setReportName("DemoWebShop Checkout Report");
            spark.config().setDocumentTitle("Automation Report");

            // ✅ Pause after reporter setup
            Thread.sleep(300);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // ✅ Pause after attaching reporter
            Thread.sleep(300);
        }
        return extent;
    }
}
