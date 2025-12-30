package com.bus.ticketing.entity;

import com.bus.ticketing.entity.enums.ApplicableScope;
import com.bus.ticketing.entity.enums.ValidityType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 票务类型实体
 */
@Entity
@Table(name = "ticket_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TicketType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "validity_type", nullable = false, length = 20)
    private ValidityType validityType;
    
    @Column(name = "validity_duration", nullable = false)
    private Integer validityDuration;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_scope", nullable = false, length = 20)
    private ApplicableScope applicableScope;
    
    @Column(nullable = false)
    private Boolean refundable = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
