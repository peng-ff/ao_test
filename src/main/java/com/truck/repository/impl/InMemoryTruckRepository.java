package com.truck.repository.impl;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;
import com.truck.repository.TruckRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 卡车仓储内存实现
 * 使用ConcurrentHashMap保证线程安全
 */
public class InMemoryTruckRepository implements TruckRepository {
    
    // 使用ConcurrentHashMap保证线程安全
    private final Map<String, Truck> truckStore = new ConcurrentHashMap<>();
    
    // VIN码索引，用于快速查找
    private final Map<String, String> vinToIdIndex = new ConcurrentHashMap<>();

    @Override
    public Truck save(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("卡车对象不能为空");
        }
        if (truck.getId() == null || truck.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("卡车ID不能为空");
        }
        
        // 保存卡车信息
        truckStore.put(truck.getId(), truck);
        
        // 更新VIN码索引
        if (truck.getVin() != null && !truck.getVin().trim().isEmpty()) {
            vinToIdIndex.put(truck.getVin(), truck.getId());
        }
        
        return truck;
    }

    @Override
    public Optional<Truck> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(truckStore.get(id));
    }

    @Override
    public Optional<Truck> findByVin(String vin) {
        if (vin == null || vin.trim().isEmpty()) {
            return Optional.empty();
        }
        
        // 通过VIN码索引查找
        String id = vinToIdIndex.get(vin);
        return id != null ? findById(id) : Optional.empty();
    }

    @Override
    public List<Truck> findAll() {
        return new ArrayList<>(truckStore.values());
    }

    @Override
    public List<Truck> findByBrand(TruckBrand brand) {
        if (brand == null) {
            return Collections.emptyList();
        }
        
        return truckStore.values().stream()
                .filter(truck -> brand.equals(truck.getBrand()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Truck> findByStatus(TruckStatus status) {
        if (status == null) {
            return Collections.emptyList();
        }
        
        return truckStore.values().stream()
                .filter(truck -> status.equals(truck.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public Truck update(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("卡车对象不能为空");
        }
        if (truck.getId() == null || truck.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("卡车ID不能为空");
        }
        if (!truckStore.containsKey(truck.getId())) {
            throw new IllegalArgumentException("卡车不存在，ID: " + truck.getId());
        }
        
        // 从VIN索引中找到旧VIN：遍历索引找到指向当前ID的VIN
        String oldVin = null;
        for (Map.Entry<String, String> entry : vinToIdIndex.entrySet()) {
            if (entry.getValue().equals(truck.getId())) {
                oldVin = entry.getKey();
                break;
            }
        }
        
        // 更新卡车信息
        truckStore.put(truck.getId(), truck);
        
        // 更新VIN码索引
        if (oldVin != null && !oldVin.equals(truck.getVin())) {
            vinToIdIndex.remove(oldVin);
        }
        if (truck.getVin() != null && !truck.getVin().trim().isEmpty()) {
            vinToIdIndex.put(truck.getVin(), truck.getId());
        }
        
        return truck;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        Truck truck = truckStore.remove(id);
        if (truck != null && truck.getVin() != null) {
            // 同时删除VIN码索引
            vinToIdIndex.remove(truck.getVin());
            return true;
        }
        
        return false;
    }

    @Override
    public long count() {
        return truckStore.size();
    }

    @Override
    public long countByBrand(TruckBrand brand) {
        if (brand == null) {
            return 0;
        }
        
        return truckStore.values().stream()
                .filter(truck -> brand.equals(truck.getBrand()))
                .count();
    }

    @Override
    public boolean existsByVin(String vin) {
        if (vin == null || vin.trim().isEmpty()) {
            return false;
        }
        return vinToIdIndex.containsKey(vin);
    }
}
