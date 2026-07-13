package com.athletico.eshop;

/**
 * Tracks who is currently logged in, so any controller can check
 * the current user's identity and role (e.g. to save orders under
 * the right user_id, or to decide whether to show the admin screen).
 * Simple singleton: one instance for the whole running application.
 */
public class Session {

    private static Session instance;

    private int userId;
    private String username;
    private String role; // "admin" or "customer"

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void login(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public void logout() {
        this.userId = 0;
        this.username = null;
        this.role = null;
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}