package com.bus.ticketing.controller;

import com.bus.ticketing.dto.response.ApiResponse;
import com.bus.ticketing.dto.response.TicketResponse;
import com.bus.ticketing.entity.Ticket;
import com.bus.ticketing.service.TicketService;
import com.bus.ticketing.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 车票控制器
 */
@Slf4j
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    
    private final TicketService ticketService;
    private final JwtUtil jwtUtil;
    
    /**
     * 获取车票详情
     */
    @GetMapping("/{ticketId}")
    public ApiResponse<TicketResponse> getTicketDetail(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        TicketResponse response = ticketService.toResponse(ticket);
        return ApiResponse.success(response);
    }
    
    /**
     * 从Token中获取用户ID
     */
    private Long getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtUtil.getUserIdFromToken(token);
    }
}
