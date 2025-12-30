package com.bus.ticketing.controller;

import com.bus.ticketing.dto.response.ApiResponse;
import com.bus.ticketing.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 验票控制器
 */
@Slf4j
@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
public class ValidationController {
    
    private final TicketService ticketService;
    
    /**
     * 验证车票
     */
    @PostMapping("/verify")
    public ApiResponse<Boolean> verifyTicket(
            @RequestParam String ticketCode,
            @RequestParam Long routeId) {
        boolean result = ticketService.validateTicket(ticketCode, routeId);
        return ApiResponse.success(result);
    }
}
