package com.square.shopping.repository;

import com.square.shopping.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * User repository for database operations
 */
@Mapper
public interface UserRepository {
    
    @Insert("INSERT INTO users (username, password, email, phone, first_name, last_name, role, enabled, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{firstName}, #{lastName}, #{role}, #{enabled}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Update("UPDATE users SET username = #{username}, email = #{email}, phone = #{phone}, " +
            "first_name = #{firstName}, last_name = #{lastName}, updated_at = NOW() WHERE id = #{id}")
    int update(User user);
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);
    
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
    
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);
    
    @Select("SELECT * FROM users WHERE enabled = true ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<User> findAll(@Param("limit") int limit, @Param("offset") int offset);
    
    @Select("SELECT COUNT(*) FROM users WHERE enabled = true")
    long count();
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);
}
