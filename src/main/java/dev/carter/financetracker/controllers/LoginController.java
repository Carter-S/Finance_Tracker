package dev.carter.financetracker.controllers;

import dev.carter.financetracker.DbConnection;
import dev.carter.financetracker.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainApplication.db = new DbConnection();
    }

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label errorText;

    @FXML
    private void loginClick(ActionEvent actionEvent) throws IOException {
        boolean successful = MainApplication.db.attemptConnection(passwordInput.getText());
        if(successful){
            MainApplication.changeScene(actionEvent, "home.fxml");
        }else{
            errorText.setText("Invalid password, try again.");
        }
    }
}
