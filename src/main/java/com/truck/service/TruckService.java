package com.truck.service;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 卡车服务接口
 * 定义卡车业务逻辑的标准操作
 */
public interface TruckService {
    
    /**
     * 创建新卡车
     * 
     * @param truck 卡车对象
     * @return 创建后的卡车对象
     * @throws IllegalArgumentException 如果卡车数据不合法
     */
    Truck createTruck(Truck truck);
    
    /**
     * 根据ID获取卡车信息
     * 
     * @param id 卡车ID
     * @return Optional包装的卡车对象
     */
    Optional<Truck> getTruckById(String id);
    
    /**
     * 根据VIN码获取卡车信息
     * 
     * @param vin VIN码
     * @return Optional包装的卡车对象
     */
    Optional<Truck> getTruckByVin(String vin);
    
    /**
     * 获取所有卡车
     * 
     * @return 卡车列表
     */
    List<Truck> getAllTrucks();
    
    /**
     * 根据品牌获取卡车列表
     * 
     * @param brand 卡车品牌
     * @return 卡车列表
     */
    List<Truck> getTrucksByBrand(TruckBrand brand);
    
    /**
     * 根据状态获取卡车列表
     * 
     * @param status 卡车状态
     * @return 卡车列表
     */
    List<Truck> getTrucksByStatus(TruckStatus status);
    
    /**
     * 更新卡车信息
     * 
     * @param truck 卡车对象
     * @return 更新后的卡车对象
     * @throws IllegalArgumentException 如果卡车不存在或数据不合法
     */
    Truck updateTruck(Truck truck);
    
    /**
     * 删除卡车
     * 
     * @param id 卡车ID
     * @return 是否删除成功
     */
    boolean deleteTruck(String id);
    
    /**
     * 获取所有品牌的卡车统计信息
     * 
     * @return 品牌到数量的映射
     */
    Map<TruckBrand, Long> getStatisticsByBrand();
    
    /**
     * 获取所有状态的卡车统计信息
     * 
     * @return 状态到数量的映射
     */
    Map<TruckStatus, Long> getStatisticsByStatus();
    
    /**
     * 获取卡车总数
     * 
     * @return 卡车总数
     */
    long getTotalCount();
    
    /**
     * 根据价格区间查询卡车
     * 
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 卡车列表
     */
    List<Truck> getTrucksByPriceRange(Double minPrice, Double maxPrice);
}
