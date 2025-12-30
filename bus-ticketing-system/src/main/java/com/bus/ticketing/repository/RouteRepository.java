package com.bus.ticketing.repository;

import com.bus.ticketing.entity.Route;
import com.bus.ticketing.entity.enums.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 线路数据访问层
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    
    /**
     * 根据线路编号查找
     */
    Optional<Route> findByRouteNumber(String routeNumber);
    
    /**
     * 根据状态查找线路
     */
    List<Route> findByRouteStatus(RouteStatus routeStatus);
    
    /**
     * 检查线路编号是否存在
     */
    boolean existsByRouteNumber(String routeNumber);
}
