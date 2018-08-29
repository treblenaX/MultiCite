package src.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import src.helpers.Enums.*;
import src.helpers.Interfaces.*;

public class ChromeTab {
    private static Logger LOGGER;
    private String _tabName;
    private WebDriver _driver;
    private WebDriverWait _wait;
    private HashMap<String, WebElement> _webMap;
    private HashMap<String, WebElement> _foundInfo;
    private EasyBibStages _state;
    private int _tabID;

    // TODO: Log messages
    public ChromeTab(int tabID) {
        // Initialize some variables
        this._tabID = tabID;
        this.LOGGER = Logger.getLogger(Constants.GLOBAL_LOGGER_NAME);
        this._tabName = "Tab " + _tabID + " ";
        initialize(Constants.HOST_URL);
    }

    private void initialize(String url) {
        LOGGER.info(getFormattedTabName() + "Initializing...");
        // Set up headless chrome options
//        ChromeOptions op = new ChromeOptions();
//        op.addArguments("--headless");
        // Initialize variables
        this._driver = new ChromeDriver();
        this._wait = new WebDriverWait(_driver, Constants.CHROME_TIMEOUT);
        this._webMap = new HashMap<String, WebElement>();
        this._foundInfo = new HashMap<String, WebElement>();
        // Set source url in driver
        this._driver.get(url);
        // Home page state
        this._state = EasyBibStages.HOME_PAGE;
        LOGGER.info( getFormattedTabName() + "Initialized!");
    }

    public void loadMap() {
        if (!_webMap.isEmpty()) {
            _webMap.clear();
        }
        switch (getState()) {
            case HOME_PAGE:
                log("Waiting for home page state...");
                waitSetWeb(HomePageElement.values());
                log("Home page state loaded!");
                break;
            case SOURCE_SELECT:
                log("Waiting for source select state...");
                waitSetWeb(SourceSelectElement.values());
                log("Source select state loaded!");
                break;
            case INFO_REVIEW:
                log("Waiting for info review state...");
                waitSetWeb(InfoReviewElement.values());
                log("Info review state loaded!");
                break;
            case MISSING_INFO:
                log("Waiting for missing info state...");
                waitSetWeb(MissingInfoElement.values());
                log("Missing info state loaded!");
                break;
            case CITE_COMPLETION:
                log("Waiting for cite completion state...");
                waitSetWeb(CiteCompletionElement.values());
                log("Cite completion state loaded!");
                break;

        }
    }

    public String citeAction(String link) {
        search(link);
        sourceSelect();
        infoReview();
        missingInfo();
        return null;
    }

    public void search(String link) {
        loadMap();
        log("Initiating search action...");
        WebElement search = _webMap.get("searchText");
        WebElement cite = _webMap.get("homePageCiteButton");
        search.sendKeys(link);
        cite.click();
        // Change state
        setState(EasyBibStages.SOURCE_SELECT);
        log("Search action completed. State change to source select.");
    }

    private void sourceSelect() {
        loadMap();
        log("Initiating sourceSelect action...");
        WebElement cite = _webMap.get("sourceSelectCiteButton");
        cite.click();
        // Change state
        setState(EasyBibStages.INFO_REVIEW);
        log("sourceSelect action completed. State change to info review.");
    }

    private void infoReview() {
        loadMap();
        log("Initiating infoReview action...");
        HashMap<String, WebElement> temp = new HashMap<String, WebElement>();
        WebElement table = _webMap.get("foundDataList");
        extractInfo(table);
        WebElement submit = _webMap.get("infoReviewContinueButton");
        submit.click();
        // Change state
        setState(EasyBibStages.MISSING_INFO);
        log("infoReview action completed. State change to info review.");
    }

    private void missingInfo() {
        loadMap();
        log("Initiating missingInfo action...");

        // Change state
        setState(EasyBibStages.CITE_COMPLETION);
        log("missingInfo action completed. State change to cite completion.");
    }

    public void revert() {
        log("Initializing revert action...");
        this._driver.get(Constants.HOST_URL);
        setState(EasyBibStages.HOME_PAGE);
        loadMap();
        log("revert action completed. State change to home page.");
    }

    public void kill() {
        log("Killing Chrome tab driver...");
        _driver.quit();
        log("Chrome tab driver killed.");
    }

    //Helper methods
    private void extractInfo(WebElement table) {
        List<WebElement> col = table.findElements(By.tagName("td"));

        int col_count = col.size();
        for (int i = 0; i < col_count; i += 2) {
            WebElement key = col.get(i);
            WebElement value = col.get(i + 1);
            log("Count: " + (i / 2) + ", Key: " + key.getText() + ", Value: " + value.getText());
            putFoundInfo(key, value);
        }
    }

    private void waitSetWeb(EnumElement[] e) {
        for (EnumElement ee : e) {
            By locator = ee.getLocator();

            _wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            _webMap.put(ee.getKey(), _driver.findElement(locator));
        }
    }

    // Logger methods
    private void log(String str) {
        LOGGER.info(getFormattedTabName() + str);
    }

    // Getters and Setters
    public EasyBibStages getState() {
        return this._state;
    }

    private void setState(EasyBibStages state) {
        this._state = state;
    }
    private String getFormattedTabName() {
        return this._tabName + ": ";
    }
    private void putFoundInfo(WebElement col1, WebElement col2) {
        this._foundInfo.put(col1.getText(), col2);
    }
}
