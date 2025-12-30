package com.bus.ticketing.entity;

import com.bus.ticketing.entity.enums.UsageStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车票实体
 */
@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @Column(name = "ticket_type_id", nullable = false)
    private Long ticketTypeId;
    
    @Column(name = "route_id")
    private Long routeId;
    
    @Column(name = "ticket_code", nullable = false, unique = true, length = 100)
    private String ticketCode;
    
    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;
    
    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;
    
    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_status", nullable = false, length = 20)
    private UsageStatus usageStatus = UsageStatus.UNUSED;
    
    @Column(name = "used_time")
    private LocalDateTime usedTime;
    
    @Column(name = "validator_id")
    private Long validatorId;
    
    @Column(name = "qr_code_data", columnDefinition = "TEXT")
    private String qrCodeData;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
