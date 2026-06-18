package Base_test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp(Method method) throws InterruptedException {
        extent = ExtentManager.getInstance();
        test = extent.createTest(method.getName());

        // ✅ FirefoxOptions setup
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("signon.rememberSignons", false);   // disable save password
        options.addPreference("extensions.formautofill.available", "off"); // disable autofill
        options.addPreference("extensions.formautofill.addresses.enabled", false); // disable save address
        options.addPreference("extensions.formautofill.creditCards.enabled", false); // disable save cards

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();

        driver.get("https://demowebshop.tricentis.com/");
        Thread.sleep(2000); // ✅ wait for page to load properly
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            Thread.sleep(1000); // ✅ small wait before screenshot/report flush
            if (result.getStatus() == ITestResult.FAILURE) {
                // ✅ Capture screenshot on failure
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String path = "C:\\Users\\hp\\OneDrive\\Desktop\\Selenium_Workspace\\demoshop\\screenshots" + result.getName() + ".png";
                File dest = new File(path);
                dest.getParentFile().mkdirs();
                Files.copy(src.toPath(), dest.toPath());

                test.fail(result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Test passed");
            } else {
                test.skip("Test skipped");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            extent.flush();
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
