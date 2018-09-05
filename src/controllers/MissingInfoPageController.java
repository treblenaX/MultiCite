package src.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.helpers.Enums.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

public class MissingInfoPageController {
    private static final Logger LOGGER = Logger.getLogger(MissingInfoPageController.class.getName());

    @FXML public TextField __articleTitle, __firstName, __middleName, __lastName, __websiteTitle, __suffix,
            __pubSpon, __url, __day, __year, __time;
    @FXML private ChoiceBox<String> __contributorChoice, __monthChoice;
    @FXML private Button __continueButton;

    private HashMap<String, String> _foundInfo;
    private HashMap<String, String> _foundInfoText;
    private boolean _isInitialized = false;

    public boolean isCompleted = false;
    public MissingInfoPageController() {
        this._isInitialized = initialize();
    }

    public boolean initialize() {
        log("Initializing...");
        // Initialize elements
        if (!this._isInitialized) {
            this.__articleTitle = new TextField();
            this.__firstName = new TextField();
            this.__middleName = new TextField();
            this.__lastName = new TextField();
            this.__websiteTitle = new TextField();
            this.__suffix = new TextField();
            this.__pubSpon = new TextField();
            this.__url = new TextField();
            this.__day = new TextField();
            this.__year = new TextField();
            this.__time = new TextField();
            this.__contributorChoice = new ChoiceBox<String>();
            this.__monthChoice = new ChoiceBox<String>();

            log("Initialized!");
            return true;
        }
        log("Initialization failed...");
        return false;
    }

    public void continueButtonAction() {
        // Collect text information
        this._foundInfoText = createFoundInfoText();
        isCompleted = true;
        Stage stage = (Stage) __continueButton.getScene().getWindow();
        stage.close();
    }
    /** Logger Methods */
    private void log(String str) {
        LOGGER.info(str);
    }

    /** Helper methods */
    public void setFoundInfoText() {
        int size = _foundInfo.keySet().size();
        String[] keys = _foundInfo.keySet().toArray(new String[size]);

        System.out.println(Arrays.toString(keys));

        for (String key : keys) {
            if (key.equals(MissingInfoElement.ARTICLE_TITLE.getKey())) {
                __articleTitle.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.CONTRIBUTORS_SELECT.getKey())) {
                __contributorChoice.getSelectionModel().select(getTextValue(key));
            } else if (key.equals(MissingInfoElement.FIRST_NAME.getKey())) {
                __firstName.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.MIDDLE_NAME.getKey())) {
                __middleName.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.LAST_NAME.getKey())) {
                __lastName.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.SUFFIX.getKey())) {
                __suffix.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.WEBSITE_TITLE.getKey())) {
                __websiteTitle.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.PUBLISHER.getKey())) {
                __pubSpon.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.URL.getKey())) {
                __url.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.DAY.getKey())) {
                __day.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.MONTH.getKey())) {
                __monthChoice.getSelectionModel().select(getTextValue(key));
            } else if (key.equals(MissingInfoElement.YEAR.getKey())) {
                __year.setText(getTextValue(key));
            } else if (key.equals(MissingInfoElement.TIME.getKey())) {
                __time.setText(getTextValue(key));
            }
        }
    }

    public void setChoiceBoxes() {
        __contributorChoice.setItems(FXCollections.observableArrayList(ChoiceBoxEnum.CONTRIBUTOR_CHOICE.getValues()));
        __monthChoice.setItems(FXCollections.observableArrayList(ChoiceBoxEnum.MONTH_CHOICE.getValues()));
    }

    public HashMap<String, String> createFoundInfoText() {
        HashMap<String, String> foundInfo = new HashMap<String, String>();

        for (MissingInfoElement e: MissingInfoElement.values()) {
            switch (e) {
                case ARTICLE_TITLE:
                    foundInfo.put(e.getKey(), __articleTitle.getText());
                    break;
                case CONTRIBUTORS_SELECT:
                    foundInfo.put(e.getKey(), __contributorChoice.getValue());
                    break;
                case FIRST_NAME:
                    foundInfo.put(e.getKey(), __firstName.getText());
                    break;
                case MIDDLE_NAME:
                    foundInfo.put(e.getKey(), __middleName.getText());
                    break;
                case LAST_NAME:
                    foundInfo.put(e.getKey(), __lastName.getText());
                    break;
                case SUFFIX:
                    foundInfo.put(e.getKey(), __suffix.getText());
                    break;
                case WEBSITE_TITLE:
                    foundInfo.put(e.getKey(), __websiteTitle.getText());
                    break;
                case PUBLISHER:
                    foundInfo.put(e.getKey(), __pubSpon.getText());
                    break;
                case URL:
                    foundInfo.put(e.getKey(), __url.getText());
                    break;
                case DAY:
                    foundInfo.put(e.getKey(), __day.getText());
                    break;
                case MONTH:
                    foundInfo.put(e.getKey(), __monthChoice.getValue());
                    break;
                case YEAR:
                    foundInfo.put(e.getKey(), __year.getText());
                    break;
                case TIME:
                    foundInfo.put(e.getKey(), __time.getText());
                    break;
            }
        }
        isCompleted = true;
        return foundInfo;
    }

    private String getTextValue(String key) {
        return _foundInfo.get(key);
    }

    /** Getters and Setters */
    public void setFoundInfo(HashMap<String, String> foundInfo) {
        this._foundInfo = foundInfo;
    }

    public HashMap<String, String> getFoundInfoText() {
        return this._foundInfoText;
    }
}
