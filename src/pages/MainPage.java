package src.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(getClass().getClassLoader().getResource("src/resources/fxml/MainPage.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/fxml/MainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Multi-Cite");
        stage.show();
    }
}
