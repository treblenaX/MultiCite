package src.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import src.helpers.ChromeTab;
import src.helpers.Constants;
import src.helpers.Enums;
import src.helpers.LinksBoxInputStream;
import src.helpers.Enums.EasyBibStages;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainPageController {
    @FXML private TextArea __linksBox;
    @FXML private Button __citeButton;

    private String _text;
    private boolean _isInitialized = false;
    private ArrayList<String> _linksList;
    private ArrayList<ChromeTab> _activeTabs;
    private Stage _primaryStage;

    public MainPageController() {
        this._isInitialized = initialize();
        System.out.println("MainWindow: " + _isInitialized);
    }

    private boolean initialize() {
        // Initialize elements
        if (!this._isInitialized) {
            this.__citeButton = new Button();
            this.__linksBox = new TextArea();
            this._text = null;
            this._linksList = new ArrayList<String>();
            this._activeTabs = new ArrayList<ChromeTab>();

            // TODO: Create loading window

            return true;
        }
        return false;
    }

    // Button actions
    public void citeButtonAction() {
        // Refresh the windows back to the home page
        refreshWindows();
        // Sorts the links in the link box into the ArrayList
        readLinksBox();
        // Loops the links
        chromeLoop();
    }

    // Chrome Tab methods
    private void chromeLoop() {
        int numOfLinks = _linksList.size();

        while (numOfLinks > 0) {
            int pointer = numOfLinks - 1;
            if (numOfLinks >= Constants.MAX_TABS_OPEN) {
                for (int i = 0; i < Constants.MAX_TABS_OPEN; i++) {
                    System.out.println("Active Tab: " + i + " URL: " + _linksList.get(pointer));
                    _activeTabs.get(i).citeAction(_linksList.get(pointer));
                    pointer--;
                }
                numOfLinks -= Constants.MAX_TABS_OPEN;
            } else {
                for (int i = 0; i < numOfLinks; i++) {
                    System.out.println("Active Tab: " + i + " URL: " + _linksList.get(pointer));
                    _activeTabs.get(i).citeAction(_linksList.get(numOfLinks));
                    pointer--;
                }
                numOfLinks = 0;
            }
        }
    }

    public void preloadWindows() {
        System.out.println("STAGE---------------" + this._primaryStage);
        if (_activeTabs.isEmpty()) {
            for (int i = 0; i < Constants.MAX_TABS_OPEN; i++) {
                _activeTabs.add(i, new ChromeTab(i, _primaryStage));
            }
        }
    }

    private void refreshWindows() {
        if (!_activeTabs.isEmpty()) {
            for (int i = 0; i < Constants.MAX_TABS_OPEN; i++) {
                if (_activeTabs.get(i).getState() != EasyBibStages.HOME_PAGE) {
                    _activeTabs.get(i).revert();
                }
            }
        }
    }

    // Helpers
    private void printTextToConsole() {
        System.out.println("Text Box: " + getText());
    }

    private void readLinksBox() {
        refreshLinksList();
        // Reads the links box and sets the links into an ArrayList
        String temp = "";

        InputStream stream = new LinksBoxInputStream(getLinksBox());
        int c;
        try {
            while((c = stream.read()) != -1) {
                temp += (char) c;
            }
            stream.close();
        } catch (IOException e) {
        }
        setText(temp);

        // Fill up ArrayList with links
        Scanner scan = new Scanner(getText());
        while (scan.hasNextLine()) {
            _linksList.add(scan.nextLine());
        }
    }

    private void refreshLinksList() {
        if (!_linksList.isEmpty()) {
            _linksList.clear();
        }
    }

    // Getters and Setters
    public TextArea getLinksBox() {
        return this.__linksBox;
    }

    public void setText(String text) {
        this._text = text;
    }

    public String getText() {
        return this._text;
    }

    public void setStage(Stage stage) {
        this._primaryStage = stage;
    }
}
