package com.athletico.eshop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles switching the content of the single primary Stage between
 * different FXML views (login, shop, cart, admin).
 */
public class SceneManager {

    private static Stage primaryStage;

    // Called once from Main.java when the app starts
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    // Switches the window to show the given FXML file (must be in the
    // same resources/com/athletico/eshop folder), keeping window size flexible.
    public static void switchTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource(fxmlFile)
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load FXML: " + fxmlFile, e);
        }
    }
}