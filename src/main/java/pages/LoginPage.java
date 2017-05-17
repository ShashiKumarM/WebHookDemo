package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "username")
    private WebElement txtUserName;

    @FindBy(id = "password")
    private WebElement txtPassword;

    @FindBy(id = "loginBtn")
    private WebElement btnLogin;

    // Testing Suite for Login Page
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 90), this);
    }

    public void enterUserName(String userName) {
        Generic.waitForElementToBeVisible(txtUserName, driver);
        txtUserName.click();
        txtUserName.sendKeys(userName);
    }

    public void enterPassword(String password) {
        Generic.waitForElementToBeVisible(txtPassword, driver);
        txtPassword.click();
        txtPassword.sendKeys(password);
    }

    public void clickLoginBtn() {
        btnLogin.click();
    }

    public EOSLPage logIntoTomorrowLand(String username, String password) throws InterruptedException {
        String decryptedPassword = Generic.getDecryptedPassword(password);
        Thread.sleep(3000);
        enterUserName(username);
        enterPassword(decryptedPassword);
        clickLoginBtn();
        Generic.waitForTitle("End of Service Life (EOSL)", driver);
        return new EOSLPage(driver);
    }

}
