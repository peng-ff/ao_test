package com.courseselection.service;

import com.courseselection.dto.request.AdminLoginRequest;
import com.courseselection.dto.request.StudentLoginRequest;
import com.courseselection.dto.response.LoginResponse;
import com.courseselection.entity.Admin;
import com.courseselection.entity.Student;
import com.courseselection.repository.AdminRepository;
import com.courseselection.repository.StudentRepository;
import com.courseselection.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务
 */
@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 学生登录
     */
    public LoginResponse studentLogin(StudentLoginRequest request) {
        Student student = studentRepository.findByStudentNumber(request.getStudentNumber())
                .orElseThrow(() -> new BadCredentialsException("学号或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new BadCredentialsException("学号或密码错误");
        }

        String token = jwtUtil.generateToken(student.getId(), "STUDENT");
        return new LoginResponse(token, "STUDENT", student.getId(), student.getName());
    }

    /**
     * 管理员登录
     */
    public LoginResponse adminLogin(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        String token = jwtUtil.generateToken(admin.getId(), "ADMIN");
        return new LoginResponse(token, "ADMIN", admin.getId(), admin.getName());
    }
}
