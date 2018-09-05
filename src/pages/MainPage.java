package src.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.controllers.MainPageController;

import java.io.IOException;

public class MainPage extends Application {
    private Stage _stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/resources/fxml/MainPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainPageController controller = (MainPageController) loader.getController();
        controller.setStage(primaryStage);
        controller.preloadWindows();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Multi-Cite");
        primaryStage.show();
    }
}
