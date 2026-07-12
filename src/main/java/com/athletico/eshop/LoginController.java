package com.athletico.eshop;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        // TEMPORARY hardcoded check - replace with UserDAO.authenticate()
        // once the MySQL layer is built.
        if ("admin".equals(username) && "admin123".equals(password)) {
            Session.getInstance().login(username, "admin");
            SceneManager.switchTo("admin_view.fxml");
        } else if ("customer".equals(username) && "customer123".equals(password)) {
            Session.getInstance().login(username, "customer");
            SceneManager.switchTo("main_view.fxml");
        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}