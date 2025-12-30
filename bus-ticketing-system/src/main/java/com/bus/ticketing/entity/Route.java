package com.bus.ticketing.entity;

import com.bus.ticketing.entity.enums.RouteStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 线路实体
 */
@Entity
@Table(name = "route")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Route {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "route_name", nullable = false, length = 50)
    private String routeName;
    
    @Column(name = "route_number", nullable = false, unique = true, length = 20)
    private String routeNumber;
    
    @Column(name = "start_station", nullable = false, length = 100)
    private String startStation;
    
    @Column(name = "end_station", nullable = false, length = 100)
    private String endStation;
    
    @Column(name = "operation_start_time", nullable = false)
    private LocalTime operationStartTime;
    
    @Column(name = "operation_end_time", nullable = false)
    private LocalTime operationEndTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "route_status", nullable = false, length = 20)
    private RouteStatus routeStatus = RouteStatus.OPERATING;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
