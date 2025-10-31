package com.truck.service.impl;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;
import com.truck.repository.TruckRepository;
import com.truck.service.TruckService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 卡车服务实现类
 * 实现卡车相关的业务逻辑
 */
public class TruckServiceImpl implements TruckService {
    
    private final TruckRepository truckRepository;

    public TruckServiceImpl(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    public Truck createTruck(Truck truck) {
        // 业务验证：检查必填字段
        validateTruck(truck);
        
        // 业务验证：检查VIN码是否已存在
        if (truck.getVin() != null && truckRepository.existsByVin(truck.getVin())) {
            throw new IllegalArgumentException("VIN码已存在: " + truck.getVin());
        }
        
        // 如果没有设置ID，自动生成UUID
        if (truck.getId() == null || truck.getId().trim().isEmpty()) {
            truck.setId(UUID.randomUUID().toString());
        }
        
        // 如果没有设置状态，默认为在售
        if (truck.getStatus() == null) {
            truck.setStatus(TruckStatus.AVAILABLE);
        }
        
        return truckRepository.save(truck);
    }

    @Override
    public Optional<Truck> getTruckById(String id) {
        return truckRepository.findById(id);
    }

    @Override
    public Optional<Truck> getTruckByVin(String vin) {
        return truckRepository.findByVin(vin);
    }

    @Override
    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    @Override
    public List<Truck> getTrucksByBrand(TruckBrand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("品牌不能为空");
        }
        return truckRepository.findByBrand(brand);
    }

    @Override
    public List<Truck> getTrucksByStatus(TruckStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("状态不能为空");
        }
        return truckRepository.findByStatus(status);
    }

    @Override
    public Truck updateTruck(Truck truck) {
        // 业务验证：检查必填字段
        validateTruck(truck);
        
        // 业务验证：检查卡车是否存在
        Optional<Truck> existingTruck = truckRepository.findById(truck.getId());
        if (existingTruck.isEmpty()) {
            throw new IllegalArgumentException("卡车不存在，ID: " + truck.getId());
        }
        
        // 业务验证：如果修改了VIN码，检查新VIN码是否已被其他卡车使用
        String oldVin = existingTruck.get().getVin();
        String newVin = truck.getVin();
        if (newVin != null && !newVin.equals(oldVin)) {
            Optional<Truck> truckWithSameVin = truckRepository.findByVin(newVin);
            if (truckWithSameVin.isPresent() && !truckWithSameVin.get().getId().equals(truck.getId())) {
                throw new IllegalArgumentException("VIN码已被其他卡车使用: " + newVin);
            }
        }
        
        return truckRepository.update(truck);
    }

    @Override
    public boolean deleteTruck(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID不能为空");
        }
        
        // 业务验证：检查卡车是否存在
        Optional<Truck> truck = truckRepository.findById(id);
        if (truck.isEmpty()) {
            return false;
        }
        
        return truckRepository.deleteById(id);
    }

    @Override
    public Map<TruckBrand, Long> getStatisticsByBrand() {
        Map<TruckBrand, Long> statistics = new EnumMap<>(TruckBrand.class);
        
        // 初始化所有品牌的计数为0
        for (TruckBrand brand : TruckBrand.values()) {
            statistics.put(brand, 0L);
        }
        
        // 统计每个品牌的卡车数量
        for (TruckBrand brand : TruckBrand.values()) {
            long count = truckRepository.countByBrand(brand);
            statistics.put(brand, count);
        }
        
        return statistics;
    }

    @Override
    public Map<TruckStatus, Long> getStatisticsByStatus() {
        Map<TruckStatus, Long> statistics = new EnumMap<>(TruckStatus.class);
        
        // 初始化所有状态的计数为0
        for (TruckStatus status : TruckStatus.values()) {
            statistics.put(status, 0L);
        }
        
        // 统计每个状态的卡车数量
        List<Truck> allTrucks = truckRepository.findAll();
        for (Truck truck : allTrucks) {
            TruckStatus status = truck.getStatus();
            if (status != null) {
                statistics.put(status, statistics.get(status) + 1);
            }
        }
        
        return statistics;
    }

    @Override
    public long getTotalCount() {
        return truckRepository.count();
    }

    @Override
    public List<Truck> getTrucksByPriceRange(Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("最低价格不能大于最高价格");
        }
        
        return truckRepository.findAll().stream()
                .filter(truck -> {
                    Double price = truck.getPrice();
                    if (price == null) {
                        return false;
                    }
                    // 价格区间过滤
                    boolean minCheck = minPrice == null || price >= minPrice;
                    boolean maxCheck = maxPrice == null || price <= maxPrice;
                    return minCheck && maxCheck;
                })
                .collect(Collectors.toList());
    }

    /**
     * 验证卡车对象的必填字段
     * 
     * @param truck 卡车对象
     * @throws IllegalArgumentException 如果验证失败
     */
    private void validateTruck(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("卡车对象不能为空");
        }
        if (truck.getBrand() == null) {
            throw new IllegalArgumentException("卡车品牌不能为空");
        }
        if (truck.getModel() == null || truck.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("卡车型号不能为空");
        }
        if (truck.getVin() == null || truck.getVin().trim().isEmpty()) {
            throw new IllegalArgumentException("VIN码不能为空");
        }
    }
}
