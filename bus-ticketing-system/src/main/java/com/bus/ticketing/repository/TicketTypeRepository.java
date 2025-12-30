package com.bus.ticketing.repository;

import com.bus.ticketing.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 票务类型数据访问层
 */
@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
}
