package com.bus.ticketing.service;

import com.bus.ticketing.dto.request.LoginRequest;
import com.bus.ticketing.dto.response.LoginResponse;
import com.bus.ticketing.entity.User;
import com.bus.ticketing.entity.enums.AccountStatus;
import com.bus.ticketing.exception.BusinessException;
import com.bus.ticketing.exception.ErrorCode;
import com.bus.ticketing.repository.UserRepository;
import com.bus.ticketing.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    /**
     * 用户登录/注册
     * 简化版本:实际应该验证验证码
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.info("用户登录: phone={}", request.getPhone());
        
        // 查找或创建用户
        User user = userRepository.findByPhone(request.getPhone())
                .orElseGet(() -> createNewUser(request.getPhone()));
        
        // 检查账户状态
        if (user.getAccountStatus() == AccountStatus.FROZEN) {
            throw new BusinessException(ErrorCode.USER_FROZEN);
        }
        
        // 生成JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getPhone());
        
        log.info("用户登录成功: userId={}, phone={}", user.getId(), user.getPhone());
        
        return new LoginResponse(
                user.getId(),
                user.getPhone(),
                user.getName(),
                user.getIdentityType().name(),
                token
        );
    }
    
    /**
     * 创建新用户
     */
    private User createNewUser(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setAccountStatus(AccountStatus.ACTIVE);
        return userRepository.save(user);
    }
    
    /**
     * 获取用户信息
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
