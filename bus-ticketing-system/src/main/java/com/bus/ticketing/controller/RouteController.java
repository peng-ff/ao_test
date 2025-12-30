package com.bus.ticketing.controller;

import com.bus.ticketing.dto.response.ApiResponse;
import com.bus.ticketing.dto.response.RouteDetailResponse;
import com.bus.ticketing.entity.Route;
import com.bus.ticketing.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 线路控制器
 */
@Slf4j
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {
    
    private final RouteService routeService;
    
    /**
     * 获取所有运营中的线路
     */
    @GetMapping
    public ApiResponse<List<Route>> getRoutes() {
        List<Route> routes = routeService.getOperatingRoutes();
        return ApiResponse.success(routes);
    }
    
    /**
     * 获取线路详情
     */
    @GetMapping("/{routeId}")
    public ApiResponse<RouteDetailResponse> getRouteDetail(@PathVariable Long routeId) {
        RouteDetailResponse detail = routeService.getRouteDetail(routeId);
        return ApiResponse.success(detail);
    }
}
