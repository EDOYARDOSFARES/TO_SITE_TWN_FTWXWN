package com.athletico.eshop;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles all database access for the `users` table, including
 * password verification via bcrypt.
 */
public class UserDAO {

    /**
     * Checks the given username/password against the database.
     * Returns the matching User if credentials are correct, or null otherwise.
     */
    public User authenticate(String username, String plainPassword) {
        String sql = "SELECT id, username, password_hash, role FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");

                    if (BCrypt.checkpw(plainPassword, storedHash)) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                storedHash,
                                rs.getString("role")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // no match, or wrong password
    }

    /**
     * Creates a new user with a securely hashed password.
     * Not wired into the GUI yet, but ready for a future "sign up" screen.
     */
    public boolean createUser(String username, String plainPassword, String role) {
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashed);
            stmt.setString(3, role);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}