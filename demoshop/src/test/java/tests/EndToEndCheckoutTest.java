package tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.*;
import utils.ExcelUtils;

public class EndToEndCheckoutTest {
    WebDriver driver;
    RegistrationPage registrationPage;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OrderConfirmationPage orderConfirmationPage;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUp() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("extensions.formautofill.available", "off");
        options.addPreference("extensions.formautofill.addresses.enabled", false);
        options.addPreference("extensions.formautofill.creditCards.enabled", false);

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");
    }

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() throws InterruptedException {
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/Book1.xlsx";
        return ExcelUtils.getTableArray(excelPath, "Sheet3");
    }

    @Test(dataProvider = "checkoutData")
    public void testEndToEndCheckout(String firstName, String lastName, String email,
                                     String password, String billingAddress, String city,
                                     String zip, String country, String phone,
                                     String shippingMethod, String paymentMethod) throws InterruptedException {

        test = extent.createTest("Checkout Test for: " + email);

        try {
            // Step 1: Registration
            driver.get("https://demowebshop.tricentis.com/register");
            Thread.sleep(1000);
            registrationPage = new RegistrationPage(driver);
            registrationPage.registerUser(firstName, lastName, email, password);
            Thread.sleep(1500);
            test.pass("Registration successful for: " + email);

            // Step 2: Login
            driver.get("https://demowebshop.tricentis.com/login");
            Thread.sleep(1000);
            loginPage = new LoginPage(driver);
            loginPage.login(email, password);
            Thread.sleep(1500);
            test.pass("Login successful for: " + email);

            // Step 3: Add Product
            productPage = new ProductPage(driver);
            productPage.addBookToCart("Computing and Internet");
            Thread.sleep(1500);
            test.pass("Product added to cart.");

            // Step 4: Cart
            cartPage = new CartPage(driver);
            cartPage.goToCart();
            Thread.sleep(1000);
            cartPage.proceedToCheckout();
            Thread.sleep(1500);
            test.pass("Proceeded to checkout.");

            // Step 5: Checkout Flow Execution
            checkoutPage = new CheckoutPage(driver);
            checkoutPage.fillCheckoutFlow(
                    firstName, 
                    lastName, 
                    email, 
                    password, 
                    billingAddress, 
                    city, 
                    zip, 
                    country, 
                    phone, 
                    shippingMethod, 
                    paymentMethod);
            Thread.sleep(2000);
            test.pass("Checkout flow completed with Excel Sheet3 Data.");

            // Step 6: Order Confirmation
            orderConfirmationPage = new OrderConfirmationPage(driver);
            Thread.sleep(1500);
            String successMsg = orderConfirmationPage.getConfirmationMessage();
            String orderNum = orderConfirmationPage.getOrderNumber();

            Assert.assertTrue(orderConfirmationPage.isOrderConfirmed(),
                    "Checkout failed for user: " + email + " | Message: " + successMsg);

            test.pass("Order confirmed. Message: " + successMsg + " | Order Number: " + orderNum);

        } catch (Exception e) {
            test.fail("Test failed for user: " + email + " | Error: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
