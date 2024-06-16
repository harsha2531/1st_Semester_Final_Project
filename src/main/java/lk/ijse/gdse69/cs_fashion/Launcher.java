package lk.ijse.gdse69.cs_fashion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/LogInPage.fxml"));

        Scene scene = new Scene(rootNode);

        stage.setScene(scene);
        stage.setTitle("login page");
        stage.show();
    }
}
