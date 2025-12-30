package com.bus.ticketing.repository;

import com.bus.ticketing.entity.Ticket;
import com.bus.ticketing.entity.enums.UsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 车票数据访问层
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    /**
     * 根据票据编号查找
     */
    Optional<Ticket> findByTicketCode(String ticketCode);
    
    /**
     * 根据订单ID查找车票
     */
    List<Ticket> findByOrderId(Long orderId);
    
    /**
     * 根据订单ID和使用状态查找车票
     */
    List<Ticket> findByOrderIdAndUsageStatus(Long orderId, UsageStatus usageStatus);
}
