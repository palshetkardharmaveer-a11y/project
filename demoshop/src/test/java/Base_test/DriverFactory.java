package Base_test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver getDriver(String browserName) {
        WebDriver driver;

        switch (browserName.toLowerCase()) {
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("autofill.address_enabled", false);
                edgePrefs.put("profile.autofill_profile_enabled", false);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);
                driver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.addPreference("signon.rememberSignons", false);
                ffOptions.addPreference("extensions.formautofill.available", "off");
                ffOptions.addPreference("extensions.formautofill.addresses.enabled", false);
                ffOptions.addPreference("extensions.formautofill.creditCards.enabled", false);
                driver = new FirefoxDriver(ffOptions);
                break;

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("autofill.address_enabled", false);
                chromePrefs.put("profile.autofill_profile_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                driver = new ChromeDriver(chromeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");

        try {
            Thread.sleep(2000); // ✅ wait for page to load properly
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return driver;
    }
}
