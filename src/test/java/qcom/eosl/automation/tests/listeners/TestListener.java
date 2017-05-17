package qcom.eosl.automation.tests.listeners;

import java.io.File;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import pages.Generic;

public class TestListener extends TestListenerAdapter {
    public void onTestFailure(ITestResult result) {
        String reasonForFailure = "";
        Throwable cause = result.getThrowable();
        if (null != cause) {
            reasonForFailure = cause.toString();
        } else {
            reasonForFailure = result.getThrowable().getMessage();
        }

        String methodName = result.getMethod().getMethodName();
        String browserName = System.getProperty("Browser_Name");

        try {
            Generic.UpdateResults(browserName, methodName, "Fail", reasonForFailure);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String browserName = System.getProperty("Browser_Name");

        try {
            Generic.UpdateResults(browserName, methodName, "Pass", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getScreenshotImageName(ITestResult result) {
        int number = 0;
        String fileName = generateScreenshotImageFileName(result, null);
        while (new File(fileName).exists()) {
            fileName = generateScreenshotImageFileName(result, ++number);
        }
        return fileName;
    }

    private String generateScreenshotImageFileName(ITestResult result, Integer number) {
        return "target/"
                + (result.isSuccess() ? "Success" : "Failure")
                + "-" + result.getTestClass().getRealClass().getName()
                + "-" + result.getName()
                + (number == null ? "" : number.toString())
                + ".png";
    }
}
