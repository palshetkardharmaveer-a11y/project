package utility_files;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot_Utility {

    // Method to capture screenshot
    public static String takeScreenshot(WebDriver driver, String name) throws IOException {
        // Capture screenshot as a File
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Define path where screenshot will be saved
        String path = System.getProperty("user.dir") + "/screenshots/" + name + ".png";

        // Copy screenshot to the defined path
        FileUtils.copyFile(src, new File(path));

        // Return the path for reference
        return path;
    }
}
