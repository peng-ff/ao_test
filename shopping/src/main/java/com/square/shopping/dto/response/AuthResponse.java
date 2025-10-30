package com.square.shopping.dto.response;

/**
 * User login/registration response with token
 */
public class AuthResponse {
    private Long userId;
    private String username;
    private String email;
    private String token;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(Long userId, String username, String email, String token, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
