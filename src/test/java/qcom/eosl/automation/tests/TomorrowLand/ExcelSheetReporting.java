package qcom.eosl.automation.tests.TomorrowLand;

import java.io.IOException;

import org.testng.annotations.BeforeSuite;

import pages.Generic;

public class ExcelSheetReporting {
    @BeforeSuite
    public void beforeSuite() throws IOException {
        Generic.createExcelResultTemplate();
    }
}
