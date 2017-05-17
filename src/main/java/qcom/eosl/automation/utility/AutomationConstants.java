package qcom.eosl.automation.utility;

public interface AutomationConstants {
    int WAIT_TIME_OUT_SEC = 40;

    String PROPERTIES_FILE_PATH = "src/test/resources/files/automation.properties";

    String DOT = ".";
    String DRIVER_URL_NAME = PropertiesUtil.getProperty("driver.url.name");

    String USERNAME_A = PropertiesUtil.getProperty("UsrA.driver.user");
    String PASSWORD_A = PropertiesUtil.getProperty("PwdA.driver.user");
}
