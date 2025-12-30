package com.bus.ticketing.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 线路详情响应DTO
 */
@Data
public class RouteDetailResponse {
    
    private Long id;
    private String routeName;
    private String routeNumber;
    private String startStation;
    private String endStation;
    private String operationStartTime;
    private String operationEndTime;
    private String routeStatus;
    private List<StationInfo> stations;
    private List<PriceInfo> prices;
    
    @Data
    public static class StationInfo {
        private Long stationId;
        private String stationName;
        private Integer sequenceNumber;
        private Integer arrivalTimeOffset;
    }
    
    @Data
    public static class PriceInfo {
        private String ticketTypeName;
        private String identityType;
        private BigDecimal price;
    }
}
