package com.example.travelplanner.service;

import com.example.travelplanner.entity.User;
import com.example.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(String username, String email, String password, String travelStyle, String budgetLevel) {
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("邮箱已被使用");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("用户名已被使用");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setTravelStyle(travelStyle != null ? travelStyle : "休闲");
        user.setBudgetLevel(budgetLevel != null ? budgetLevel : "中等");

        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("邮箱或密码错误"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("邮箱或密码错误");
        }

        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }
} 