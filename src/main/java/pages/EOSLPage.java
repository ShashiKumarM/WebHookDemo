package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class EOSLPage {
    private WebDriver driver;

    @FindBy(id = "txtSearchHosts")
    private WebElement txtSearchHosts;

    @FindBy(xpath = "//div[contains(@class,'input-group-btn')]/button")
    private WebElement btnSearchHosts;

    @FindBy(xpath = "//table[@template-header='tableheader']//tr/td[1]")
    private List<WebElement> hostListFromGrid;

    @FindBy(xpath = "//table[@template-header='tableheader']//th//span")
    private List<WebElement> gridColumns;

    public EOSLPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 90), this);
    }

    public void searchHost(String hostName) {
        Generic.waitForElementToBeVisible(txtSearchHosts, driver);
        txtSearchHosts.click();
        txtSearchHosts.clear();
        txtSearchHosts.sendKeys(hostName);
        Generic.waitForElementToBeVisible(btnSearchHosts, driver);
        btnSearchHosts.click();
    }

    public String getDataFromGrid(String expectedHostName, String expectedColumnName)
            throws IOException {
        String data = "";
        for (int row = 0; row < hostListFromGrid.size(); row++) {
            if (hostListFromGrid.get(row).isDisplayed()) {
                WebElement expectedHostWebElement = hostListFromGrid.get(row);
                String actualHostName = expectedHostWebElement.getText().trim();
                if (actualHostName.equalsIgnoreCase(expectedHostName)) {
                    WebElement expectedHostWebElementRow = expectedHostWebElement.findElement(By.xpath("./.."));
                    List<WebElement> expectedHostWebElementColumnList =
                            expectedHostWebElementRow.findElements(By.tagName("td"));
                    for (int col = 0; col < gridColumns.size(); col++) {
                        if (gridColumns.get(col).getText().equalsIgnoreCase(expectedColumnName)) {
                            WebElement cellData = expectedHostWebElementColumnList.get(col);
                            data = cellData.getText().trim();
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return data;
    }

}
