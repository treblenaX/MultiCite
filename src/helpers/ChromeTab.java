package src.helpers;

import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.controllers.WindowController;
import src.helpers.Enums.*;
import src.helpers.Interfaces.*;

public class ChromeTab {
    private static Logger LOGGER;
    private String _tabName;
    private WebDriver _driver;
    private WebDriverWait _wait;
    private HashMap<String, WebElement> _webMap;
    private HashMap<String, String> _foundInfo;
    private HashMap<String, String> _finalFoundInfo;
    private EasyBibStages _state;
    private int _tabID;
    private Stage _stage;
    private String _url;

    public ChromeTab(int tabID, Stage stage) {
        // Initialize some variables
        this._tabID = tabID;
        this._stage = stage;
        System.out.println(this._stage);
        this.LOGGER = Logger.getLogger(ChromeTab.class.getSimpleName() + " " + _tabID);
        initialize(Constants.HOST_URL);
    }

    private void initialize(String url) {
        LOGGER.info(getFormattedTabName() + "Initializing...");
//         Set up headless chrome options
        ChromeOptions op = new ChromeOptions();
        op.addArguments("--headless --window-size=1280,720");
        // Initialize variables
        this._driver = new ChromeDriver();
        this._wait = new WebDriverWait(_driver, Constants.CHROME_TIMEOUT);
        this._webMap = new HashMap<String, WebElement>();
        this._foundInfo = new HashMap<String, String>();
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
        setUrl(link);
        search();
        sourceSelect();
        infoReview();
        missingInfo();
        return null;
    }

    /** Helper Methods */
    private void extractInfo(WebElement table) {
        List<WebElement> col = table.findElements(By.tagName("td"));

        int col_count = col.size();
        for (int i = 0; i < col_count; i += 2) {
            WebElement key = col.get(i);
            WebElement value = col.get(i + 1);
            log("Count: " + (i / 2) + ", Key: " + takeOutDone(key.getText()) + ", Value: " + value.getText());
            sortPutFoundInfo(takeOutDone(key.getText().toLowerCase()), value.getText());
        }
    }

    private String takeOutDone(String key) {
        Scanner scan = new Scanner(key);
        String temp = "";
        if (scan.hasNext()) {
            if (scan.next().equals("done")) {
                while (scan.hasNext()) {
                    temp += scan.next();
                }
                return temp;
            }
        }
        return temp;
    }

    private void sortPutFoundInfo(String key, String value) {
        if (key.contains("article")) {
            putFoundInfo(MissingInfoElement.ARTICLE_TITLE.getKey(), value);
        } else if (key.contains("contributors")) {
            putFoundInfo(MissingInfoElement.CONTRIBUTORS_SELECT.getKey(), value);
        } else if (key.contains("first")) {
            putFoundInfo(MissingInfoElement.FIRST_NAME.getKey(), value);
        } else if (key.contains("middle")) {
            putFoundInfo(MissingInfoElement.MIDDLE_NAME.getKey(), value);
        } else if (key.contains("last")) {
            putFoundInfo(MissingInfoElement.LAST_NAME.getKey(), value);
        } else if (key.contains("suffix")) {
            putFoundInfo(MissingInfoElement.SUFFIX.getKey(), value);
        } else if (key.contains("website")) {
            putFoundInfo(MissingInfoElement.WEBSITE_TITLE.getKey(), value);
        } else if (key.contains("url")) {
            putFoundInfo(MissingInfoElement.URL.getKey(), value);
        } else if (key.contains("day")) {
            putFoundInfo(MissingInfoElement.DAY.getKey(), value);
        } else if (key.contains("month")) {
            putFoundInfo(MissingInfoElement.MONTH.getKey(), value);
        } else if (key.contains("year")) {
            putFoundInfo(MissingInfoElement.YEAR.getKey(), value);
        } else if (key.contains("time")) {
            putFoundInfo(MissingInfoElement.TIME.getKey(), value);
        }
    }

    public void search() {
        loadMap();
        log("Initiating search action...");
        WebElement search = _webMap.get(HomePageElement.SEARCH_TEXT.getKey());
        WebElement cite = _webMap.get(HomePageElement.HOME_PAGE_CITE.getKey());
        search.sendKeys(getUrl());
        cite.click();
        // Change state
        setState(EasyBibStages.SOURCE_SELECT);
        log("Search action completed. State change to source select.");
    }

    private void sourceSelect() {
        loadMap();
        log("Initiating sourceSelect action...");
        WebElement cite = _webMap.get(SourceSelectElement.SOURCE_SELECT_CITE_BUTTON.getKey());
        cite.click();
        // Change state
        setState(EasyBibStages.INFO_REVIEW);
        log("sourceSelect action completed. State change to info review.");
    }

    private void infoReview() {
        loadMap();
        log("Initiating infoReview action...");
        WebElement table = _webMap.get(InfoReviewElement.FOUND_DATA_LIST.getKey());
        extractInfo(table);
        WebElement submit = _webMap.get(InfoReviewElement.INFO_REVIEW_CONTINUE_BUTTON.getKey());
        submit.click();
        // Change state
        setState(EasyBibStages.MISSING_INFO);
        log("infoReview action completed. State change to info review.");
    }

    private void missingInfo() {
        loadMap();
        log("Initiating missingInfo action...");
        try {
            _finalFoundInfo = WindowController.addMissingInfoPage(_stage, getUrl(), _foundInfo);
        } catch (IOException e) {
            log("ERROR creating missing info page:");
            e.printStackTrace();
        }
        replaceWeb(MissingInfoElement.values());
        WebElement complete = _webMap.get(MissingInfoElement.COMPLETE.getKey());
        complete.click();
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

    private void replaceWeb(EnumElement[] e) {
        for (EnumElement ee: e) {
            if (!ee.equals(MissingInfoElement.COMPLETE)) {
                String key = ee.getKey();
                if (!(ee.equals(MissingInfoElement.CONTRIBUTORS_SELECT) || ee.equals(MissingInfoElement.MONTH))) {
                    _webMap.get(key).clear();
                }
                _webMap.get(key).sendKeys(_finalFoundInfo.get(key));
            }
        }
    }

    private void waitSetWeb(EnumElement[] e) {
        for (EnumElement ee : e) {
            By locator = ee.getLocator();

            _wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            _webMap.put(ee.getKey(), _driver.findElement(locator));
        }
    }

    /** Logger Methods */
    private void log(String str) {
        LOGGER.info(str);
    }

    /** Getters and Setters */
    public EasyBibStages getState() {
        return this._state;
    }
    public void setState(EasyBibStages state) {
        this._state = state;
    }
    public String getFormattedTabName() {
        return this._tabName + ": ";
    }
    public void putFoundInfo(String col1, String col2) {
        this._foundInfo.put(col1, col2);
    }
    public void setUrl(String url) {
        this._url = url;
    }
    public String getUrl() {
        return this._url;
    }
}
