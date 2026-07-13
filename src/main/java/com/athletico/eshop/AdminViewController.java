package com.athletico.eshop;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminViewController {

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField stockInput;

    @FXML
    private Button addproductButton;

    @FXML
    private Button editproductButton;

    @FXML
    private Button deleteproductButton;

    @FXML
    private Button logoutButton;

    private final ProductDAO productDAO = new ProductDAO();

    @FXML
    private void initialize() {
        setupColumns();
        loadProducts();

        // When a row is selected, fill the form fields with its data,
        // so "Edit" knows what to update.
        productTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nameInput.setText(newVal.getName());
                priceInput.setText(String.valueOf(newVal.getPrice()));
                stockInput.setText(String.valueOf(newVal.getStock()));
            }
        });

        addproductButton.setOnAction(event -> handleAddProduct());
        editproductButton.setOnAction(event -> handleEditProduct());
        deleteproductButton.setOnAction(event -> handleDeleteProduct());
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void handleLogout() {
        Session.getInstance().logout();
        SceneManager.switchTo("login_view.fxml");
    }

    private void setupColumns() {
        idColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));
        priceColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        stockColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getStock()).asObject());
    }

    private void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(productDAO.getAllProducts());
        productTableView.setItems(products);
    }

    private void handleAddProduct() {
        String name = nameInput.getText();
        Double price = parseDouble(priceInput.getText());
        Integer stock = parseInt(stockInput.getText());

        if (name == null || name.isBlank() || price == null || stock == null) {
            showError("Please fill in a valid name, price, and stock quantity.");
            return;
        }

        boolean success = productDAO.addProduct(name, price, stock);
        if (success) {
            loadProducts();
            clearForm();
        } else {
            showError("Failed to add product - check the console for details.");
        }
    }

    private void handleEditProduct() {
        Product selected = productTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a product from the table first.");
            return;
        }

        String name = nameInput.getText();
        Double price = parseDouble(priceInput.getText());
        Integer stock = parseInt(stockInput.getText());

        if (name == null || name.isBlank() || price == null || stock == null) {
            showError("Please fill in a valid name, price, and stock quantity.");
            return;
        }

        boolean success = productDAO.updateProduct(selected.getId(), name, price, stock);
        if (success) {
            loadProducts();
            clearForm();
        } else {
            showError("Failed to update product - check the console for details.");
        }
    }

    private void handleDeleteProduct() {
        Product selected = productTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a product from the table first.");
            return;
        }

        boolean success = productDAO.deleteProduct(selected.getId());
        if (success) {
            loadProducts();
            clearForm();
        } else {
            showError("Failed to delete product - it may be referenced by an existing order.");
        }
    }

    private void clearForm() {
        nameInput.clear();
        priceInput.clear();
        stockInput.clear();
        productTableView.getSelectionModel().clearSelection();
    }

    private Double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}