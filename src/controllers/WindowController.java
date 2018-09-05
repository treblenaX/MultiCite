package src.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.WebElement;
import src.helpers.Constants;
import src.pages.MissingInfoPage;

import java.io.IOException;
import java.util.HashMap;

public class WindowController {
    public static HashMap<String, String> addMissingInfoPage(Stage stage, String url, HashMap<String, String> foundInfo) throws IOException  {
        // Load fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MissingInfoPage.class.getClassLoader().getResource("src/resources/fxml/MissingInfoPage.fxml"));
        Parent root = loader.load();
        // Get controller and set elements in the appropriate state
        MissingInfoPageController controller = loader.getController();
        controller.setChoiceBoxes();
        controller.setFoundInfo(foundInfo);
        controller.setFoundInfoText();
        // Execute the new window
        Scene scene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.setTitle(Constants.APPLICATION_NAME);
        newWindow.setScene(scene);
        newWindow.initOwner(stage);
        newWindow.showAndWait();
        // Return controller with the information
        return controller.getFoundInfoText();
    }
}
