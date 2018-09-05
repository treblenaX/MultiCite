package src.helpers;

import org.openqa.selenium.By;

import src.helpers.Interfaces.EnumElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enums {
    public enum EasyBibStages {
        HOME_PAGE,
        SOURCE_SELECT,
        INFO_REVIEW,
        MISSING_INFO,
        CITE_COMPLETION
    }

    public enum ChoiceBoxEnum {
        CONTRIBUTOR_CHOICE("Author", "Editor", "Translator"),
        MONTH_CHOICE("", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
                "November", "December");

        private List<String> values;

        ChoiceBoxEnum(String... values) {
            this.values = Arrays.asList(values);
        }

        public List<String> getValues() {
            return this.values;
        }
    }

    /** Element Enums */
    public enum HomePageElement implements EnumElement{
        SEARCH_TEXT("searchText", By.name("q")),
        HOME_PAGE_CITE("homePageCiteButton", By.className("searchbutton"));

        private String key;
        private By locator;

        HomePageElement(String key, By locator) {
            this.key = key;
            this.locator = locator;
        }

        public String getKey() {
            return this.key;
        }

        public By getLocator() {
            return this.locator;
        }
    }

    public enum SourceSelectElement implements EnumElement {
        SOURCE_SELECT_CITE_BUTTON("sourceSelectCiteButton", By.className("cite"));

        private String key;
        private By locator;

        SourceSelectElement(String key, By locator) {
            this.key = key;
            this.locator = locator;
        }

        public String getKey() {
            return this.key;
        }

        public By getLocator() {
            return this.locator;
        }
    }

    public enum InfoReviewElement implements EnumElement {
        FOUND_DATA_LIST("foundDataList", By.className("datalist")),
        INFO_REVIEW_CONTINUE_BUTTON("infoReviewContinueButton", By.className("submit-eval"));

        private String key;
        private By locator;

        InfoReviewElement(String key, By locator) {
            this.key = key;
            this.locator = locator;
        }

        public String getKey() {
            return this.key;
        }

        public By getLocator() {
            return this.locator;
        }
    }

    public enum MissingInfoElement implements EnumElement {
        ARTICLE_TITLE("articleTitle", By.name("website[title]")),
        CONTRIBUTORS_SELECT("contributorsSelect", By.name("contributors[function][]")),
        FIRST_NAME("firstName", By.name("contributors[first][]")),
        MIDDLE_NAME("middleName", By.name("contributors[middle][]")),
        LAST_NAME("lastName", By.name("contributors[last][]")),
        SUFFIX("suffix", By.name("contributors[suffix][]")),
        WEBSITE_TITLE("websiteTitle", By.name("pubonline[title]")),
        PUBLISHER("publisher", By.name("pubonline[inst]")),
        URL("url", By.name("pubonline[url]")),
        DAY("day", By.name("pubonline[day]")),
        MONTH("month", By.name("pubonline[month]")),
        YEAR("year", By.name("pubonline[year]")),
        TIME("time", By.name("pubonline[timestamp]")),
        COMPLETE("complete", By.className("complete-citation"));

        private String key;
        private By locator;

        MissingInfoElement(String key, By locator) {
            this.key = key;
            this.locator = locator;
        }

        public String getKey() {
            return this.key;
        }

        public By getLocator() {
            return this.locator;
        }
    }

    public enum CiteCompletionElement implements EnumElement {
        COMPLETE_CITATION("completeCitation", By.className("complete-citation"));


        private String key;
        private By locator;

        CiteCompletionElement(String key, By locator) {
            this.key = key;
            this.locator = locator;
        }

        public String getKey() {
            return this.key;
        }

        public By getLocator() {
            return this.locator;
        }
    }
}
