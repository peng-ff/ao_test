package com.bus.ticketing.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车票响应DTO
 */
@Data
public class TicketResponse {
    
    private Long id;
    private String ticketCode;
    private String ticketTypeName;
    private String routeName;
    private BigDecimal purchasePrice;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private String usageStatus;
    private String qrCodeData;
    private LocalDateTime createdAt;
}
