package com.bus.ticketing.service;

import com.bus.ticketing.dto.response.RouteDetailResponse;
import com.bus.ticketing.entity.PriceRule;
import com.bus.ticketing.entity.Route;
import com.bus.ticketing.entity.RouteStation;
import com.bus.ticketing.entity.Station;
import com.bus.ticketing.entity.enums.RouteStatus;
import com.bus.ticketing.exception.BusinessException;
import com.bus.ticketing.exception.ErrorCode;
import com.bus.ticketing.repository.PriceRuleRepository;
import com.bus.ticketing.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 线路服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {
    
    private final RouteRepository routeRepository;
    private final PriceRuleRepository priceRuleRepository;
    
    /**
     * 获取所有运营中的线路
     */
    public List<Route> getOperatingRoutes() {
        return routeRepository.findByRouteStatus(RouteStatus.OPERATING);
    }
    
    /**
     * 获取所有线路
     */
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
    
    /**
     * 根据ID获取线路
     */
    public Route getRouteById(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));
    }
    
    /**
     * 获取线路详情
     */
    public RouteDetailResponse getRouteDetail(Long routeId) {
        Route route = getRouteById(routeId);
        
        RouteDetailResponse response = new RouteDetailResponse();
        response.setId(route.getId());
        response.setRouteName(route.getRouteName());
        response.setRouteNumber(route.getRouteNumber());
        response.setStartStation(route.getStartStation());
        response.setEndStation(route.getEndStation());
        response.setOperationStartTime(route.getOperationStartTime().toString());
        response.setOperationEndTime(route.getOperationEndTime().toString());
        response.setRouteStatus(route.getRouteStatus().name());
        
        // 获取票价信息
        List<PriceRule> priceRules = priceRuleRepository.findByRouteId(routeId);
        List<RouteDetailResponse.PriceInfo> priceInfos = priceRules.stream()
                .map(rule -> {
                    RouteDetailResponse.PriceInfo info = new RouteDetailResponse.PriceInfo();
                    info.setIdentityType(rule.getIdentityType().name());
                    info.setPrice(rule.getPrice());
                    return info;
                })
                .collect(Collectors.toList());
        response.setPrices(priceInfos);
        
        return response;
    }
    
    /**
     * 验证线路是否可用
     */
    public void validateRouteAvailable(Long routeId) {
        Route route = getRouteById(routeId);
        if (route.getRouteStatus() != RouteStatus.OPERATING) {
            throw new BusinessException(ErrorCode.ROUTE_NOT_OPERATING);
        }
    }
}
