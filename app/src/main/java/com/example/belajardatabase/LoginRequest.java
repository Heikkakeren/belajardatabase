package com.example.belajardatabase;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter & Setter (opsional)
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
