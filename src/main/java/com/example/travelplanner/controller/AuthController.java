package com.example.travelplanner.controller;

import com.example.travelplanner.config.JwtUtils;
import com.example.travelplanner.dto.LoginRequest;
import com.example.travelplanner.dto.RegisterRequest;
import com.example.travelplanner.dto.UserResponse;
import com.example.travelplanner.entity.User;
import com.example.travelplanner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            
            String token = jwtUtils.generateJwtToken(user.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", new UserResponse(user));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.register(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getTravelStyle(),
                registerRequest.getBudgetLevel()
            );
            
            String token = jwtUtils.generateJwtToken(user.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", new UserResponse(user));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            // 移除 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String email = jwtUtils.getEmailFromJwtToken(token);
            User user = userService.findByEmail(email);
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", new UserResponse(user));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "无效的令牌");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/preferences")
    public ResponseEntity<?> updatePreferences(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> request) {
        try {
            // 移除 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String email = jwtUtils.getEmailFromJwtToken(token);
            User user = userService.findByEmail(email);
            
            @SuppressWarnings("unchecked")
            Map<String, String> preferences = (Map<String, String>) request.get("preferences");
            
            if (preferences.containsKey("travelStyle")) {
                user.setTravelStyle(preferences.get("travelStyle"));
            }
            if (preferences.containsKey("budgetLevel")) {
                user.setBudgetLevel(preferences.get("budgetLevel"));
            }
            
            User updatedUser = userService.save(user);
            
            Map<String, Object> response = new HashMap<>();
            Map<String, String> userPreferences = new HashMap<>();
            userPreferences.put("travelStyle", updatedUser.getTravelStyle());
            userPreferences.put("budgetLevel", updatedUser.getBudgetLevel());
            response.put("preferences", userPreferences);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
} 