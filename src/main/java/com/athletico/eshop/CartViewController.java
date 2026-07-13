package com.athletico.eshop;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CartViewController {

    @FXML
    private Button BackButton;

    @FXML
    private TableView<CartItem> cartTableView;

    @FXML
    private TableColumn<CartItem, String> productColumn;

    @FXML
    private TableColumn<CartItem, Double> priceColumn;

    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;

    @FXML
    private TableColumn<CartItem, Double> totalColumn;

    @FXML
    private TableColumn<CartItem, Void> removeColumn;

    @FXML
    private Label totalpriceLabel;

    @FXML
    private Button checkoutButton;

    private final OrderDAO orderDAO = new OrderDAO();

    @FXML
    private void initialize() {
        BackButton.setOnAction(event -> SceneManager.switchTo("main_view.fxml"));
        checkoutButton.setOnAction(event -> handleCheckout());

        cartTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setupColumns();
        refreshTable();
    }

    private void setupColumns() {
        productColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getProduct().getName()));

        priceColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getProduct().getPrice()).asObject());

        quantityColumn.setCellValueFactory(data ->
                data.getValue().quantityProperty().asObject());

        totalColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getTotal()).asObject());

        removeColumn.setCellFactory(col -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    CartManager.getInstance().removeItem(item);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeButton);
            }
        });
    }

    private void refreshTable() {
        cartTableView.setItems(CartManager.getInstance().getItems());
        totalpriceLabel.setText(String.format("$%.2f", CartManager.getInstance().getTotalPrice()));
    }

    private void handleCheckout() {
        if (CartManager.getInstance().getItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Your cart is empty.");
            return;
        }

        if (!Session.getInstance().isLoggedIn()) {
            showAlert(Alert.AlertType.ERROR, "You must be logged in to check out.");
            return;
        }

        int userId = Session.getInstance().getUserId();
        boolean success = orderDAO.createOrder(userId, CartManager.getInstance().getItems());

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Order placed successfully! Thank you for shopping with Athletico.");
            CartManager.getInstance().clear();
            SceneManager.switchTo("main_view.fxml");
        } else {
            showAlert(Alert.AlertType.ERROR, "Checkout failed - one or more items may be out of stock. Please review your cart.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Checkout");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}