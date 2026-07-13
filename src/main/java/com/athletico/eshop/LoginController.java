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

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        User user = userDAO.authenticate(username, password);

        if (user != null) {
            Session.getInstance().login(user.getId(), user.getUsername(), user.getRole());

            if (Session.getInstance().isAdmin()) {
                SceneManager.switchTo("admin_view.fxml");
            } else {
                SceneManager.switchTo("main_view.fxml");
            }
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