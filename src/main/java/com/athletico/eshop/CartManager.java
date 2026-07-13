package com.athletico.eshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Holds the current shopping cart contents in memory for the duration
 * of the app's run. Singleton, same pattern as Session, so any
 * controller can access the same cart.
 */
public class CartManager {

    private static CartManager instance;

    private final ObservableList<CartItem> items = FXCollections.observableArrayList();

    private CartManager() {
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ObservableList<CartItem> getItems() {
        return items;
    }

    // Adds a product to the cart. If it's already in the cart, increases
    // the quantity instead of creating a duplicate row.
    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}