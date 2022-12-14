package dev.carter.financetracker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static DbConnection db;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(ActionEvent aE, String filename) throws IOException {
        Stage stage = (Stage) ((Node)aE.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(filename));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    public static void main(String[] args) {
        launch();
    }
}