package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.google.common.collect.Ordering;;

/**
 * Generic methods which contains helper methods for other classes
 */
public class Generic {
    private static String testResultsPath = System.getProperty("user.dir") + "/ExcelReport/";

    private static byte[] key =
            { 0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79 };

    public static void waitForElementPresent(String id, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public static void selectByVisibleTextFromDropDown(WebElement element, String option) {
        Select sel = new Select(element);
        sel.selectByVisibleText(option);
    }

    public static void selectByIndexFromDropDown(WebElement element, int index) {
        Select sel = new Select(element);
        sel.selectByIndex(index);
    }

    public static void maximizeWindow(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public static void clickLinkText(String link, WebDriver driver) {
        driver.findElement(By.linkText(link)).click();
    }

    public static void scroll(WebElement element, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void scrollUp(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo( document.body.scrollHeight,0)");
    }

    public static void scrollRight(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(2000,0)", "");
    }

    public static void scroll(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void clickLinkWithWait(String link, WebDriver driver) {
        for (int i = 0; i < 10; i++) {
            try {
                clickLinkText(link, driver);
                break;
            } catch (Exception e) {
            }
        }
    }

    public static void mouseOverOnWebElement(WebElement webElement, WebDriver driver) {
        try {
            waitForElementToBeVisible(webElement, driver);
            webElement.sendKeys(Keys.PAGE_DOWN);
            waitForElementToBeVisible(webElement, driver);
            hoverOnWebElement(webElement, driver);
            waitForElementToBeVisible(webElement, driver);
            hoverOnWebElement(webElement, driver);
            waitForElementToBeVisible(webElement, driver);
        } catch (Exception e) {
            Reporter.log("Exception while hover over");
        }
    }

    public static void hoverOnWebElement(WebElement webElement, WebDriver driver) {
        waitForElementToBeVisible(webElement, driver);
        Actions action = new Actions(driver);
        action.moveToElement(webElement).build().perform();
    }

    public static void doubleClick(WebElement webElement, WebDriver driver) {
        try {
            Actions action = new Actions(driver).doubleClick(webElement);
            action.moveToElement(webElement).build().perform();
        } catch (StaleElementReferenceException e) {
            Reporter.log("Element is not attached to the page document "
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            Reporter.log("Element " + webElement + " was not found in DOM "
                    + e.getStackTrace());
        } catch (Exception e) {
            Reporter.log("Element " + webElement + " was not clickable "
                    + e.getStackTrace());
        }
    }

    public static void clearExistingAndEnterNewText(WebElement element, String newText) {
        element.clear();
        Reporter.log(newText);
        element.sendKeys(newText);
    }

    public static String getPageTitle(WebDriver driver) {
        return driver.getTitle().trim();
    }

    public static void waitForElementToBeClickable(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(WebElement element, WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public static void waitForElementToBeVisible(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static boolean isElementDisplayed(String xpathStr, WebDriver driver) {
        boolean isElementDisplayed = true;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(xpathStr)));
            driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        } catch (Exception e) {
            isElementDisplayed = false;
            driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        }
        return isElementDisplayed;
    }

    public static boolean isClickable(WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 90);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getWindowHandle(WebDriver driver) {
        return driver.getWindowHandle();
    }

    public static void waitForElementToVisibleInList(List<WebElement> element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfAllElements(element));
    }

    /**
     * Selects an option from a from drop down list
     */
    public static void selectDropDownListOption(List<WebElement> element, String option) {

        String dropDownText = null;
        for (WebElement ele : element) {
            dropDownText = ele.getText();
            if (dropDownText.equalsIgnoreCase(option)) {
                ele.click();
            }
        }
    }

    /**
     * Converts String[] to ArrayList
     * 
     * @param option
     * @return
     * @throws InterruptedException
     */
    public static ArrayList<String> expectedDropdownOptions(String[] option) throws InterruptedException {
        ArrayList<String> options = new ArrayList<String>();
        for (int i = 0; i < option.length; i++) {
            options.add(option[i]);
        }
        return options;
    }

    public static String getDefaultValueDropDown(WebElement element) {
        Select dropDown = new Select(element);
        String defaultValue = dropDown.getAllSelectedOptions().get(0).getText().trim();

        return defaultValue;
    }

    public static int getMonthsBetweenDates(Date actualDate, Date selectDate) throws ParseException {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(actualDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(selectDate);
        int yearDiff = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int monthsBetween = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH) + 12 * yearDiff;
        return monthsBetween;
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Date date = new Date();
        String systemDate = dateFormat.format(date);
        return systemDate;
    }

    public static String getPreviousDateOfDateMentioned(String date, int days) throws ParseException {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Date dateReq = dateFormat.parse(date);
        cal.setTime(dateReq);
        cal.add(Calendar.DATE, -days);
        String previousDay = dateFormat.format(cal.getTime());
        return previousDay;
    }

    public static String getPreviousDateOfToday(int days) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, ((-1) * days));
        String previousDate = dateFormat.format(cal.getTime());
        return previousDate;
    }

    public static String getFutureDateOfToday(int days) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        String futureDate = dateFormat.format(cal.getTime());
        return futureDate;
    }

    // Select a check Box
    public static void selectCheckBox(WebElement element) {
        if (!element.isSelected()) {
            element.click();
        }
    }

    // Deselect a check Box
    public static void deSelectCheckBox(WebElement element) {
        if (element.isSelected()) {
            element.click();
        }
    }

    public static boolean isElementsDisplayed(List<WebElement> element) {
        boolean match = false;
        for (WebElement row : element) {
            if (row.isDisplayed()) {
                match = true;
            }
        }
        return match;
    }

    public static String getNextDate(String date, int noOfDays) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Date dateReq = dateFormat.parse(date);
        cal.setTime(dateReq);
        cal.add(Calendar.DATE, noOfDays);
        String nextDay = dateFormat.format(cal.getTime());
        return nextDay;
    }

    public static void waitForElementToBeInvisible(String xpath, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("xpath")));
    }

    public static void alertAccept(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public static String alerttext(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public static void waitForTitle(String title, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.titleIs(title));
    }

    public static void waitForTitleContaining(String title, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.titleContains(title));
    }

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Date dateReq = dateFormat.parse(date);
        return dateReq;
    }

    public static List<String> getListOfStringFromListOfWebElements(List<WebElement> elements) {
        List<String> listOfString = new ArrayList<String>();
        for (int i = 0; i < elements.size(); i++) {
            if (!elements.get(i).getText().trim().isEmpty()) {
                listOfString.add(elements.get(i).getText().trim().split("-")[0]);
            }
        }
        return listOfString;
    }

    public static List<Integer> getListofIntegerFromListOfWebElements(List<WebElement> elements) {
        List<Integer> listOfString = new ArrayList<Integer>();
        for (int i = 0; i < elements.size(); i++) {
            if (!elements.get(i).getText().trim().isEmpty()) {
                listOfString.add(Integer.parseInt(elements.get(i).getText().trim().split("-")[0]));
            }
        }
        return listOfString;
    }

    public static boolean isListOfStringsSorted(List<String> lstValues, String sortOrder) {
        boolean sorted = false;
        if (sortOrder.equals("Ascending")) {
            sorted = Ordering.natural().isOrdered(lstValues);
        } else if (sortOrder.equals("Descending")) {
            sorted = Ordering.natural().reverse().isOrdered(lstValues);
        }
        return sorted;
    }

    public static boolean isListOfNumbersSorted(List<Integer> lstValues, String sortOrder) {
        boolean sorted = false;
        if (sortOrder.equals("Ascending")) {
            sorted = Ordering.natural().isOrdered(lstValues);
        } else if (sortOrder.equals("Descending")) {
            sorted = Ordering.natural().reverse().isOrdered(lstValues);
        }
        return sorted;
    }

    public static void waitForAttibuteToContain(WebElement element, String attribute, String value, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public static String splitEmpNameContainingBrace(String actualEmpName) {
        if (actualEmpName.contains("(")) {
            String[] name = actualEmpName.split(Pattern.quote("("));
            actualEmpName = name[0].trim();
        }
        return actualEmpName;
    }

    public static String splitEmpNameContainingMultipleSpace(String actualEmpName) {
        if (actualEmpName.contains("  ")) {
            String[] name = actualEmpName.split(Pattern.quote("  "));
            actualEmpName = name[0].trim() + " " + name[1].trim();
        }
        return actualEmpName;
    }

    public static String trimCurrentCycleEndDateWithEscapeSequence(String newcycleEndDate) {
        if (newcycleEndDate.contains("/")) {
            String[] date = newcycleEndDate.split(Pattern.quote("/"));
            newcycleEndDate = date[0].trim() + date[1].trim() + date[2].trim();
        }
        return newcycleEndDate;
    }

    public static void createExcelResultTemplate() throws IOException {
        String strDate;
        String resultFilePath;
        String fileName;

        strDate = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
        fileName = "TomorrowLandResults_" + strDate + ".xls";

        resultFilePath = testResultsPath + fileName;
        System.out.println("testResultsPath :  " + resultFilePath);

        System.setProperty("Result_File_Path", resultFilePath);

        FileOutputStream fileOut = new FileOutputStream(new File(resultFilePath));
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("ResultSheet");

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Times New Roman");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        style.setFillForegroundColor(new HSSFColor.LIGHT_YELLOW().getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        Row row = worksheet.createRow(0);
        row.createCell(0).setCellValue("BROWSER_NAME");
        row.createCell(1).setCellValue("METHOD_NAME");
        row.createCell(2).setCellValue("RESULT");
        row.createCell(3).setCellValue("REASON_FOR_FAILURE");

        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
            worksheet.autoSizeColumn(i);
        }

        workbook.write(fileOut);
        workbook.close();
        fileOut.flush();
        fileOut.close();
    }

    public static void UpdateResults(String browserName, String methodName, String result, String reasonForFailure)
            throws IOException {
        int rowNum;
        File file = new File(System.getProperty("Result_File_Path"));
        InputStream inp = new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(inp);
        Sheet sheet = workbook.getSheet("ResultSheet");
        rowNum = sheet.getLastRowNum();
        rowNum = rowNum + 1;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(browserName);
        row.createCell(1).setCellValue(methodName);
        row.createCell(2).setCellValue(result);
        row.createCell(3).setCellValue(reasonForFailure);
        // Formatting the Cells
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Calibri");
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(System.getProperty("Result_File_Path"));
        // FileOutputStream fileOut = new FileOutputStream(testResultsPath);
        workbook.write(fileOut);
        workbook.close();
        fileOut.flush();
        fileOut.close();
    }

    public static String convertTo24HrFormat(String time) throws ParseException {
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        Date date = date12Format.parse(time);
        String timeIn24HrFormat = date24Format.format(date);
        return timeIn24HrFormat;
    }

    public static String getMd5HexacodeOfFile(String crunchifyFile) {
        File myFile = new File(crunchifyFile);
        return crunchifyGetMd5ForFile(myFile);
    }

    public static String crunchifyGetMd5ForFile(File crunchifyFile) {
        String crunchifyValue = null;
        FileInputStream crunchifyInputStream = null;
        try {
            crunchifyInputStream = new FileInputStream(crunchifyFile);
            crunchifyValue = DigestUtils.md5Hex(IOUtils.toByteArray(crunchifyInputStream));
        } catch (IOException e) {
            Reporter.log("Error while creating MD5: " + e);
        } finally {
            IOUtils.closeQuietly(crunchifyInputStream);
        }
        return crunchifyValue;
    }

    public static void createFolderStructure(String folderStructure) {
        File file = new File(folderStructure);
        // If the folder doesn't exist then create it
        if (!file.exists()) {
            if (file.mkdirs()) {
                Reporter.log("Directory is created!");
            } else {
                Reporter.log("Failed to create directory!");
            }
        }
    }

    public static String getDecryptedPassword(String encryptedPassword) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String decryptedString = null;
        try {
            decryptedString = new String(cipher.doFinal(Base64
                    .decodeBase64(encryptedPassword)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return decryptedString;
    }

    public static void downloadFileUsingRobotClass(WebDriver driver)
            throws InterruptedException, AWTException, IOException {
        Thread.sleep(8000);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        Thread.sleep(5000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(5000);
    }

    public static void deleteUploadedFile(String filePath) {
        File filez = new File(filePath);
        deleteFile(filez);
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public static void uploadFileUsingSendKeys(String filePath, WebElement element, WebDriver driver)
            throws IOException, InterruptedException {
        LocalFileDetector detector = new LocalFileDetector();
        File file = detector.getLocalFile(filePath);
        // ((RemoteWebDriver) driver).setFileDetector(detector);
        waitForElementToBeVisible(element, driver);
        element.sendKeys(file.getAbsolutePath());
    }

    public static void copyFileToDirectory(String filePath, String destDirectory) throws IOException {
        File file = new File(filePath);
        File destinationDir = new File(destDirectory);
        if (file.exists()) {
            FileUtils.copyFileToDirectory(file, destinationDir);
        }
    }

    public static String getFileName(String renamedFilePath) {
        return renamedFilePath.substring(renamedFilePath.lastIndexOf("/") + 1, renamedFilePath.length());
    }

    public static String getCurrentDateTimeStr() {
        DateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = new Date();
        Date date2 = new Date();
        String currentTime = dateFormat1.format(date1);
        String todaysDate = dateFormat2.format(date2);

        String day = todaysDate.substring(todaysDate.indexOf("/") + 1, todaysDate.lastIndexOf("/"));
        String month = todaysDate.substring(0, todaysDate.indexOf("/"));
        String Hour = currentTime.substring(0, currentTime.indexOf(":"));
        String Minute = currentTime.substring(currentTime.indexOf(":") + 1, currentTime.lastIndexOf(":"));
        String Second = currentTime.substring(currentTime.lastIndexOf(":") + 1, currentTime.length());

        String SSN_String = day + month + "_" + Hour + Minute + Second;
        return SSN_String;
    }

    public static String getMd5OfUploadedFile(String uploadFilePath) {
        String md5HexaCode = getMd5HexacodeOfFile(uploadFilePath);
        deleteUploadedFile(uploadFilePath);
        return md5HexaCode;
    }

    public static void waitForEvent(long waitTime) throws InterruptedException {
        Thread.sleep(waitTime);
    }
}
