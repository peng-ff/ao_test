package com.bus.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 线路站点关联实体
 */
@Entity
@Table(name = "route_station")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RouteStation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "route_id", nullable = false)
    private Long routeId;
    
    @Column(name = "station_id", nullable = false)
    private Long stationId;
    
    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;
    
    @Column(name = "arrival_time_offset")
    private Integer arrivalTimeOffset;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
