package com.example.travelplanner.dto;

import com.example.travelplanner.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Map<String, String> preferences;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        
        this.preferences = new HashMap<>();
        this.preferences.put("travelStyle", user.getTravelStyle());
        this.preferences.put("budgetLevel", user.getBudgetLevel());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Map<String, String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, String> preferences) {
        this.preferences = preferences;
    }
} 