package com.athletico.eshop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class CartViewController {

    @FXML
    private Button BackButton;

    @FXML
    private TableColumn<?, ?> productColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TableColumn<?, ?> totalColumn;

    @FXML
    private TableColumn<?, ?> removeColumn;

    @FXML
    private Label totalpriceLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private void initialize() {
        BackButton.setOnAction(event -> SceneManager.switchTo("main_view.fxml"));

        checkoutButton.setOnAction(event -> handleCheckout());

        // TODO: bind the TableView to an ObservableList from a CartManager
        // singleton once that's built, and recalculate totalpriceLabel
        // whenever the cart changes.
    }

    private void handleCheckout() {
        // TODO: write order to MySQL via OrderDAO, clear cart, show confirmation.
        System.out.println("Checkout clicked");
    }
}