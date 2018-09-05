package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import src.helpers.Constants;
import src.helpers.mLogger;
import src.pages.MainPage;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            mLogger.setup();
            MainPage.launch(MainPage.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There were problems creating the log files.");
        }
    }
}