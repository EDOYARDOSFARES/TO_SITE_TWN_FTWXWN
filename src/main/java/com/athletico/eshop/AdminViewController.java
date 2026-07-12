package com.athletico.eshop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminViewController {

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
    private void initialize() {
        addproductButton.setOnAction(event -> handleAddProduct());
        editproductButton.setOnAction(event -> handleEditProduct());
        deleteproductButton.setOnAction(event -> handleDeleteProduct());

        // TODO: guard this whole screen - if Session.getInstance().isAdmin()
        // is false, redirect back to login. Also load the TableView
        // from ProductDAO.getAllProducts() once MySQL layer exists.
    }

    private void handleAddProduct() {
        // TODO: ProductDAO.addProduct(nameInput.getText(), priceInput.getText(), stockInput.getText());
        System.out.println("Add product clicked: " + nameInput.getText());
    }

    private void handleEditProduct() {
        // TODO: ProductDAO.updateProduct(...) based on selected TableView row.
        System.out.println("Edit product clicked");
    }

    private void handleDeleteProduct() {
        // TODO: ProductDAO.deleteProduct(...) based on selected TableView row.
        System.out.println("Delete product clicked");
    }
}