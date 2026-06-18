package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;
 // 📍 Billing Address Input Fields Locators
    private By billingNewAddressSelect = By.id("billing-address-select");
    private By billingFirstName = By.id("BillingNewAddress_FirstName");
    private By billingLastName = By.id("BillingNewAddress_LastName");
    private By billingEmail = By.id("BillingNewAddress_Email");
    private By billingCountryDropdown = By.id("BillingNewAddress_CountryId");
    private By billingCity = By.id("BillingNewAddress_City");
    private By billingAddress1 = By.id("BillingNewAddress_Address1");
    private By billingZipCode = By.id("BillingNewAddress_ZipPostalCode");
    private By billingPhoneNumber = By.id("BillingNewAddress_PhoneNumber");
 // 📍 Continue Buttons
    private By continueBillingBtn = By.xpath("//input[@onclick='Billing.save()']");
    private By continueShippingAddressBtn = By.xpath("//input[@onclick='Shipping.save()']");
    private By continueShippingMethodBtn = By.xpath("//input[@class='button-1 shipping-method-next-step-button']");
    private By continuePaymentMethodBtn = By.xpath("//input[@class='button-1 payment-method-next-step-button']");
    private By continuePaymentInfoBtn = By.xpath("//input[@class='button-1 payment-info-next-step-button']");
    private By confirmOrderBtn = By.xpath("//input[@class='button-1 confirm-order-next-step-button']");
 // 📍 Shipping and Payment Locators
    private By shippingOptions = By.xpath("//input[@name='shippingoption']");
    private By shippingStepHeading = By.xpath("//h2[contains(text(),'Shipping method')]");
    private By shippingMethodLoadingSection = By.id("shipping-method-please-wait");
    private By paymentOptions = By.xpath("//input[@name='paymentmethod']");
public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
 // ✅ Complete Billing Step
    public void completeBillingStep(String fName, String lName, String email, String country, String city, String address, String zip, String phone) {
        try {
            if (driver.findElements(billingNewAddressSelect).size() > 0 && driver.findElement(billingNewAddressSelect).isDisplayed()) {
                Select addressSelect = new Select(driver.findElement(billingNewAddressSelect));
                addressSelect.selectByVisibleText("New Address");
                System.out.println("Selected 'New Address' from billing dropdown.");
            }
        } catch (Exception e) {
            System.out.println("Billing Address dropdown handling skipped.");
        }
 try {
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(billingFirstName));
            firstNameField.clear();
            firstNameField.sendKeys(fName);

            WebElement lastNameField = driver.findElement(billingLastName);
            lastNameField.clear();
            lastNameField.sendKeys(lName);

            WebElement emailField = driver.findElement(billingEmail);
            emailField.clear();
            emailField.sendKeys(email);

            Select countrySelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(billingCountryDropdown)));
            countrySelect.selectByVisibleText(country);

            WebElement cityField = driver.findElement(billingCity);
            cityField.clear();
            cityField.sendKeys(city);

            WebElement addressField = driver.findElement(billingAddress1);
            addressField.clear();
            addressField.sendKeys(address);

            WebElement zipField = driver.findElement(billingZipCode);
            zipField.clear();
            zipField.sendKeys(zip);

            WebElement phoneField = driver.findElement(billingPhoneNumber);
            phoneField.clear();
            phoneField.sendKeys(phone);
            
            System.out.println("Billing Address details filled from Excel Sheet3 successfully.");
        } catch (Exception e) {
            System.out.println("Form filling skipped: " + e.getMessage());
        }

        wait.until(ExpectedConditions.elementToBeClickable(continueBillingBtn)).click();
        System.out.println("Billing Address 'Continue' clicked.");
    }

//  Shipping Address step 
    public void completeShippingAddressStep(String shippingAddressValue) {
        try {
           By shippingAddressSelect = By.id("shipping-address-select");
            if (wait.until(ExpectedConditions.presenceOfElementLocated(shippingAddressSelect)).isDisplayed()) {
                new Select(driver.findElement(shippingAddressSelect))
                        .selectByVisibleText(shippingAddressValue);
                System.out.println("Shipping Address selected: " + shippingAddressValue);
            }
        } catch (TimeoutException | NoSuchElementException e) {
        	System.out.println("Shipping Address dropdown skipped or not present.");
        }
 try {
           
            WebElement shippingAddressBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(continueShippingAddressBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shippingAddressBtn);
            System.out.println("Shipping Address continue clicked.");

            //  Shipping Method section
            wait.until(ExpectedConditions.visibilityOfElementLocated(shippingStepHeading));
            System.out.println("Shipping Method section is visible now.");
 } catch (TimeoutException e) {
            System.out.println("Shipping Address continue button click failed.");
        }
    }
// ✅ Shipping Method Step
    public void completeShippingMethodStep(String shippingOptionValue) {
        try { Thread.sleep(3000); } catch (InterruptedException ie) { }

        wait.until(ExpectedConditions.visibilityOfElementLocated(shippingStepHeading));
        
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(shippingMethodLoadingSection));
        } catch (Exception e) {}

        List<WebElement> shippingList = null;
        for (int i = 0; i < 3; i++) {
            try {
                shippingList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(shippingOptions));
                if (!shippingList.isEmpty() && shippingList.get(0).isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                try { Thread.sleep(1500); } catch (InterruptedException ie) {}
            }
        }
                
        if (shippingList != null && !shippingList.isEmpty()) {
            boolean selected = false;
            for (WebElement option : shippingList) {
                String val = option.getAttribute("value");
                if (val.toLowerCase().contains(shippingOptionValue.toLowerCase())) {
                    // Fixed: arguments
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                // Fixed: arguments
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shippingList.get(0));
            }
            System.out.println("Shipping option selected successfully.");
        } else {
            throw new RuntimeException("Shipping options could not be loaded.");
        }
        
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueShippingMethodBtn));
        // Fixed: arguments
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
        System.out.println("Shipping Method continue clicked.");
    }

    // ✅ Payment Method Step
    public void completePaymentMethodStep(String paymentOptionValue) {
        try { Thread.sleep(2000); } catch (InterruptedException ie) { }
        List<WebElement> paymentList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(paymentOptions));
        if (!paymentList.isEmpty()) {
            boolean paymentSelected = false;
            for (WebElement option : paymentList) {
                String val = option.getAttribute("value");
                if (val.equalsIgnoreCase(paymentOptionValue) || val.toLowerCase().contains(paymentOptionValue.toLowerCase())) {
                    // Fixed: arguments
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    paymentSelected = true;
                    break;
                }
            }
            if (!paymentSelected) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", paymentList.get(0));
        }
        wait.until(ExpectedConditions.elementToBeClickable(continuePaymentMethodBtn)).click();
        System.out.println("Payment Method continue clicked.");
    }

    // ✅ Payment Info Step
    public void completePaymentInfoStep() {
        try { Thread.sleep(2000); } catch (InterruptedException ie) { }
        wait.until(ExpectedConditions.elementToBeClickable(continuePaymentInfoBtn)).click();
        System.out.println("Payment Info continue clicked.");
    }

    // ✅ Confirm Order Step
    public void confirmOrder() {
        try { Thread.sleep(2000); } catch (InterruptedException ie) { }
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn)).click();
        System.out.println("Confirm Order clicked.");
    }
    public void fillCheckoutFlow(String firstName, String lastName, String email,
                                 String password, String billingAddress, String city,
                                 String zip, String country, String phone,
                                 String shippingMethod, String paymentMethod) {
        completeBillingStep(firstName, lastName, email, country, city, billingAddress, zip, phone);
        String shippingAddressValue = firstName + " " + lastName + ", " + billingAddress + ", " + city + " " + zip + ", " + country;
        completeShippingAddressStep(shippingAddressValue);
        completeShippingMethodStep(shippingMethod);
        completePaymentMethodStep(paymentMethod);
        completePaymentInfoStep();
        confirmOrder();
    }
}
