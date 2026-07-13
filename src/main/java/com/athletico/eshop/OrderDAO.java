package com.athletico.eshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Handles writing a completed checkout to the database:
 * one row in `orders`, one row per item in `order_items`,
 * and reduces stock in `products` accordingly.
 */
public class OrderDAO {

    /**
     * Creates an order from the given cart items for the given user.
     * Returns true if the whole order was saved successfully.
     * All steps run in a single transaction - if anything fails,
     * nothing is saved (no partial orders, no stock changes).
     */
    public boolean createOrder(int userId, List<CartItem> cartItems) {
        String insertOrderSql = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?)";
        String insertItemSql = "INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotal();
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // start transaction

            int orderId;
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, userId);
                orderStmt.setDouble(2, total);
                orderStmt.executeUpdate();

                try (ResultSet keys = orderStmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        orderId = keys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated order ID.");
                    }
                }
            }

            try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSql);
                 PreparedStatement stockStmt = conn.prepareStatement(updateStockSql)) {

                for (CartItem item : cartItems) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, item.getProduct().getId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getProduct().getPrice());
                    itemStmt.executeUpdate();

                    stockStmt.setInt(1, item.getQuantity());
                    stockStmt.setInt(2, item.getProduct().getId());
                    stockStmt.setInt(3, item.getQuantity());
                    int updated = stockStmt.executeUpdate();

                    if (updated == 0) {
                        // Not enough stock for this item - abort the whole order.
                        throw new SQLException("Not enough stock for: " + item.getProduct().getName());
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}