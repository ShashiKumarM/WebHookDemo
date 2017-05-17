package qcom.eosl.automation.utility;

import static qcom.eosl.automation.utility.AutomationConstants.DRIVER_URL_NAME;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Driver {

    /**
     * Sets Up Browser Instance and launches it
     * 
     * @throws Exception
     */
    public static synchronized WebDriver setUpManagerLogin(String browser) throws Exception {
        WebDriver driver = null;
        DesiredCapabilities cap = null;
        String urlName = DRIVER_URL_NAME;
        if (browser.equals("Firefox")) {
            cap = DesiredCapabilities.firefox();
            cap.setPlatform(Platform.ANY);
            // driver = new FirefoxDriver();
        }
        // URL url = new URL("http://tomorrowland-jenkins.qualcomm.com:4444/wd/hub");
        URL url = new URL("http://localhost:4444/wd/hub");
        driver = new RemoteWebDriver(url, cap);
        driver.get(urlName);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }
}
