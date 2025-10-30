package com.square.shopping.repository;

import com.square.shopping.entity.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Address repository for database operations
 */
@Mapper
public interface AddressRepository {
    
    @Insert("INSERT INTO addresses (user_id, recipient_name, phone, country, province, city, district, street, " +
            "postal_code, is_default, address_type, created_at, updated_at) " +
            "VALUES (#{userId}, #{recipientName}, #{phone}, #{country}, #{province}, #{city}, #{district}, #{street}, " +
            "#{postalCode}, #{isDefault}, #{addressType}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Address address);
    
    @Update("UPDATE addresses SET recipient_name = #{recipientName}, phone = #{phone}, country = #{country}, " +
            "province = #{province}, city = #{city}, district = #{district}, street = #{street}, " +
            "postal_code = #{postalCode}, is_default = #{isDefault}, address_type = #{addressType}, updated_at = NOW() WHERE id = #{id}")
    int update(Address address);
    
    @Select("SELECT * FROM addresses WHERE id = #{id}")
    Address findById(Long id);
    
    @Select("SELECT * FROM addresses WHERE user_id = #{userId} ORDER BY is_default DESC, created_at DESC")
    List<Address> findByUserId(Long userId);
    
    @Select("SELECT * FROM addresses WHERE user_id = #{userId} AND is_default = true LIMIT 1")
    Address findDefaultByUserId(Long userId);
    
    @Update("UPDATE addresses SET is_default = false WHERE user_id = #{userId} AND id != #{exceptId}")
    int clearDefaultExcept(@Param("userId") Long userId, @Param("exceptId") Long exceptId);
    
    @Delete("DELETE FROM addresses WHERE id = #{id}")
    int deleteById(Long id);
}
