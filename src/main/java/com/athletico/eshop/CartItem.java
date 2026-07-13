package com.athletico.eshop;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Represents one line in the shopping cart: a product and how many
 * of it the user wants. Uses JavaFX properties so the TableView in
 * cart_view.fxml can bind directly to these fields.
 */
public class CartItem {

    private final Product product;
    private final SimpleIntegerProperty quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    // Convenience properties for direct TableView column binding
    public SimpleStringProperty productNameProperty() {
        return new SimpleStringProperty(product.getName());
    }

    public SimpleDoubleProperty priceProperty() {
        return new SimpleDoubleProperty(product.getPrice());
    }

    public double getTotal() {
        return product.getPrice() * getQuantity();
    }
}