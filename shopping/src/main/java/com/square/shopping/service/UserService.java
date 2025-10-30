package com.square.shopping.service;

import com.square.shopping.dto.request.LoginRequest;
import com.square.shopping.dto.request.RegisterRequest;
import com.square.shopping.dto.response.AuthResponse;
import com.square.shopping.entity.User;
import com.square.shopping.exception.BusinessException;
import com.square.shopping.repository.UserRepository;
import com.square.shopping.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

/**
 * User service for user management and authentication
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    /**
     * Register a new user
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new BusinessException("Username already exists");
        }
        
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new BusinessException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole("CUSTOMER");
        user.setEnabled(true);
        
        userRepository.insert(user);
        
        // Generate token
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole());
        
        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token, user.getRole());
    }
    
    /**
     * Login user
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException("Invalid username or password");
        }
        
        if (!user.getEnabled()) {
            throw new BusinessException("Account is disabled");
        }
        
        String hashedPassword = hashPassword(request.getPassword());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new BusinessException("Invalid username or password");
        }
        
        // Generate token
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole());
        
        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token, user.getRole());
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        return user;
    }
    
    /**
     * Get all users with pagination
     */
    public List<User> getAllUsers(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return userRepository.findAll(pageSize, offset);
    }
    
    /**
     * Get total user count
     */
    public long getTotalUserCount() {
        return userRepository.count();
    }
    
    /**
     * Update user information
     */
    @Transactional
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId());
        if (existingUser == null) {
            throw new BusinessException("User not found");
        }
        
        userRepository.update(user);
        return userRepository.findById(user.getId());
    }
    
    /**
     * Hash password using SHA-256
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new BusinessException("Failed to hash password");
        }
    }
}
