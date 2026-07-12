package com.athletico.eshop;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Athletico - Sporting Goods");
        SceneManager.switchTo("login_view.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}