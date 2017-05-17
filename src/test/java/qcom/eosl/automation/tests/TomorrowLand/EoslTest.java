package qcom.eosl.automation.tests.TomorrowLand;

import static qcom.eosl.automation.utility.AutomationConstants.PASSWORD_A;
import static qcom.eosl.automation.utility.AutomationConstants.USERNAME_A;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.EOSLPage;
import pages.LoginPage;
import qcom.eosl.automation.utility.Driver;

public class EoslTest {
    private WebDriver driver;
    private LoginPage objLoginPage;
    private EOSLPage objEoslPage;

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethod(String browser) throws Exception {
        System.setProperty("Browser_Name", browser);
        driver = Driver.setUpManagerLogin(browser);
        objLoginPage = new LoginPage(driver);
        objEoslPage = objLoginPage.logIntoTomorrowLand(USERNAME_A, PASSWORD_A);
    }

    /**
     * Validate the site location of the host in EOSL page
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void validateSiteLocationOfHost() throws IOException, InterruptedException {
        Thread.sleep(3000);
        objEoslPage.searchHost("lasher.qualcomm.com");
        Thread.sleep(1000);
        Assert.assertEquals(objEoslPage.getDataFromGrid("lasher.qualcomm.com", "site"), "sandiego",
                "Mismatch in site.");
    }

    @AfterMethod
    public void afterMethod() {
        System.setProperty("Browser_Name", "");
        driver.quit();
    }
}
