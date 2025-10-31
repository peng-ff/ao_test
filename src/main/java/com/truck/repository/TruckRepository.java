package com.truck.repository;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;

import java.util.List;
import java.util.Optional;

/**
 * 卡车仓储接口
 * 定义卡车数据访问的标准操作
 */
public interface TruckRepository {
    
    /**
     * 保存卡车信息
     * 
     * @param truck 卡车对象
     * @return 保存后的卡车对象
     */
    Truck save(Truck truck);
    
    /**
     * 根据ID查找卡车
     * 
     * @param id 卡车ID
     * @return Optional包装的卡车对象
     */
    Optional<Truck> findById(String id);
    
    /**
     * 根据VIN码查找卡车
     * 
     * @param vin VIN码
     * @return Optional包装的卡车对象
     */
    Optional<Truck> findByVin(String vin);
    
    /**
     * 查找所有卡车
     * 
     * @return 卡车列表
     */
    List<Truck> findAll();
    
    /**
     * 根据品牌查找卡车
     * 
     * @param brand 卡车品牌
     * @return 卡车列表
     */
    List<Truck> findByBrand(TruckBrand brand);
    
    /**
     * 根据状态查找卡车
     * 
     * @param status 卡车状态
     * @return 卡车列表
     */
    List<Truck> findByStatus(TruckStatus status);
    
    /**
     * 更新卡车信息
     * 
     * @param truck 卡车对象
     * @return 更新后的卡车对象
     */
    Truck update(Truck truck);
    
    /**
     * 根据ID删除卡车
     * 
     * @param id 卡车ID
     * @return 是否删除成功
     */
    boolean deleteById(String id);
    
    /**
     * 统计所有卡车数量
     * 
     * @return 卡车总数
     */
    long count();
    
    /**
     * 根据品牌统计卡车数量
     * 
     * @param brand 卡车品牌
     * @return 该品牌的卡车数量
     */
    long countByBrand(TruckBrand brand);
    
    /**
     * 检查VIN码是否存在
     * 
     * @param vin VIN码
     * @return 是否存在
     */
    boolean existsByVin(String vin);
}
