package qcom.eosl.automation.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserConfiguration implements SeleniumExecutionConstants {

    private static WebDriver driver;
    private static FirefoxProfile profile;

    /**
     * Creates browser instance for launching the application in the corresponding browser.
     */
    public static WebDriver getBrowser() {
        if (SeleniumExecutionConstants.BROWSER_NAME.equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (SeleniumExecutionConstants.BROWSER_NAME.equals("ie")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")
                    + "\\src\\test\\resources\\drivers\\IEDriverServer.exe");
            DesiredCapabilities returnCapabilities = DesiredCapabilities.internetExplorer();
            returnCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
            driver = new InternetExplorerDriver(returnCapabilities);
        } else if (SeleniumExecutionConstants.BROWSER_NAME.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                    + "\\src\\test\\resources\\drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else {
            profile = new FirefoxProfile();
            profile.setPreference("network.proxy.type", 0);
            driver = new FirefoxDriver(profile);
        }
        return driver;
    }
}
