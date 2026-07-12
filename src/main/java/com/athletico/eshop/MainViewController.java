package com.athletico.eshop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MainViewController {

    @FXML
    private TextField SearchBar;

    @FXML
    private Button SearchButton;

    @FXML
    private Button mainviewShowCartButton;

    @FXML
    private GridPane productGrid;

    @FXML
    private void initialize() {
        mainviewShowCartButton.setOnAction(event -> SceneManager.switchTo("cart_view.fxml"));

        SearchButton.setOnAction(event -> handleSearch());

        // TODO: load real products from ProductDAO once MySQL layer exists,
        // and populate productGrid with product cards dynamically.
    }

    private void handleSearch() {
        String query = SearchBar.getText();
        // TODO: filter productGrid based on query once products are loaded from DB.
        System.out.println("Search for: " + query);
    }
}