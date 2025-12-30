package com.bus.ticketing.entity;

import com.bus.ticketing.entity.enums.VehicleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 车辆实体
 */
@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    private String licensePlate;
    
    @Column(name = "vehicle_model", length = 50)
    private String vehicleModel;
    
    @Column
    private Integer capacity;
    
    @Column(name = "current_route_id")
    private Long currentRouteId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status", nullable = false, length = 20)
    private VehicleStatus vehicleStatus = VehicleStatus.OPERATING;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
