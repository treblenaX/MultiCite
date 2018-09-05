package src.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import src.pages.MainPage;

import java.net.URL;

public class Constants {
    public static final int MAX_TABS_OPEN = 1;
    public static final int CHROME_TIMEOUT = 60;

    public static final String APPLICATION_NAME = "MultiCite";
    public static final String HOST_URL = "http://easybib.com/style";
    public static final String FOUND_INFO_XPATH = "//section/table/tbody/";

    public static final URL MAIN_PAGE = MainPage.class.getClassLoader().getResource("src/resources/fxml/MainPage.fxml");
}
