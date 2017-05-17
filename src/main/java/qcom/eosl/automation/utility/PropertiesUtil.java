package qcom.eosl.automation.utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = getProperties(AutomationConstants.PROPERTIES_FILE_PATH);

    public static synchronized String getProperty(String key) {
        String property = System.getProperty(key);
        if (property == null) {
            property = System.getenv(key);
        }
        if (property == null) {
            property = PROPERTIES.getProperty(key);
        }
        return property;
    }

    private static Properties getProperties(String filePath) {
        try (InputStream in = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("Unable To read properties from " + filePath, e);
        }
    }
}