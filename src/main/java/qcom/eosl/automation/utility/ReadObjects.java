package qcom.eosl.automation.utility;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Class Name : ReadObjects Author : Shashi
 */
public class ReadObjects {
    private static final String SELENIUM_SERVER_DEFAULT = System.getProperty("selenium.server.host");
    private static final String SELENIUM_SERVER_PORT_AND_URI = ":4444/wd/hub";
    private static final String HTTP_PREFIX = "http://";
    private static final String TARGET_BROWSER_KEY = System.getProperty("selenium.target.browser");
    private static final String TARGET_BROWSER_CHROME = "chrome";
    private static final String TARGET_BROWSER_FIREFOX = "firefox";
    private static final String TARGET_BROWSER_IE = "ie";
    private static final String TARGET_HOST_KEY = System.getProperty("selenium.target.host");

    // Get Selenium Server URL--- Target Machine Name
    public String getSeleniumServerUrl() {
        return HTTP_PREFIX + SELENIUM_SERVER_DEFAULT
                + SELENIUM_SERVER_PORT_AND_URI;
    }

    // Get Firefox Driver
    public WebDriver getFireFoxDriver() throws Exception {
        FirefoxProfile profile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.ANY);
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        WebDriver driver = new RemoteWebDriver(new URL(getSeleniumServerUrl()), capabilities);
        return driver;
    }

    /**
     * Get the IE Browser Selenium WebDriver used for remote control.
     */
    public WebDriver getIEDriver() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities
                .internetExplorer();
        capabilities
                .setCapability(
                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
        WebDriver driver = new RemoteWebDriver(new URL(getSeleniumServerUrl()),
                capabilities);
        return driver;
    }

    /**
     * Get the Chrome Browser Selenium WebDriver used for remote control.
     */
    public WebDriver getChromeDriver() throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL(getSeleniumServerUrl()), capabilities);
        return driver;
    }

    // Get WebDriver based on the Browser
    public WebDriver getDriver() throws Exception {
        WebDriver driver = null;
        String browser = TARGET_BROWSER_KEY;
        if (browser.equals(TARGET_BROWSER_FIREFOX)) {
            driver = getFireFoxDriver();
        } else if (browser.equals(TARGET_BROWSER_IE)) {
            driver = getIEDriver();
        } else if (browser.equals(TARGET_BROWSER_CHROME)) {
            driver = getChromeDriver();
        }
        return driver;
    }

    public String getPropertyValues() throws IOException {
        String targetUrl = TARGET_HOST_KEY;
        return targetUrl;
    }
}
