package com.bus.ticketing.service;

import com.bus.ticketing.dto.response.TicketResponse;
import com.bus.ticketing.entity.Ticket;
import com.bus.ticketing.entity.TicketType;
import com.bus.ticketing.entity.enums.UsageStatus;
import com.bus.ticketing.entity.enums.ValidityType;
import com.bus.ticketing.exception.BusinessException;
import com.bus.ticketing.exception.ErrorCode;
import com.bus.ticketing.repository.TicketRepository;
import com.bus.ticketing.repository.TicketTypeRepository;
import com.bus.ticketing.util.QRCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 车票服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
    
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QRCodeUtil qrCodeUtil;
    
    /**
     * 生成车票
     */
    @Transactional
    public Ticket generateTicket(Long orderId, Long ticketTypeId, Long routeId, java.math.BigDecimal price) {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TICKET_TYPE_NOT_FOUND));
        
        Ticket ticket = new Ticket();
        ticket.setOrderId(orderId);
        ticket.setTicketTypeId(ticketTypeId);
        ticket.setRouteId(routeId);
        ticket.setTicketCode(generateTicketCode());
        ticket.setPurchasePrice(price);
        
        // 计算有效期
        LocalDateTime now = LocalDateTime.now();
        ticket.setValidFrom(now);
        ticket.setValidUntil(calculateValidUntil(now, ticketType));
        
        ticket.setUsageStatus(UsageStatus.UNUSED);
        
        // 生成二维码
        String qrCodeData = qrCodeUtil.generateQRCode(ticket.getTicketCode());
        ticket.setQrCodeData(qrCodeData);
        
        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("车票生成成功: ticketId={}, ticketCode={}", savedTicket.getId(), savedTicket.getTicketCode());
        
        return savedTicket;
    }
    
    /**
     * 计算有效期
     */
    private LocalDateTime calculateValidUntil(LocalDateTime from, TicketType ticketType) {
        if (ticketType.getValidityType() == ValidityType.SINGLE) {
            return from.plusDays(1); // 单次票当天有效
        } else if (ticketType.getValidityType() == ValidityType.DAILY) {
            return from.plusDays(ticketType.getValidityDuration());
        } else {
            return from.plusMonths(ticketType.getValidityDuration());
        }
    }
    
    /**
     * 生成票据编号
     */
    private String generateTicketCode() {
        return "TICKET" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
    /**
     * 获取车票详情
     */
    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TICKET_NOT_FOUND));
    }
    
    /**
     * 根据订单ID获取车票列表
     */
    public List<Ticket> getTicketsByOrderId(Long orderId) {
        return ticketRepository.findByOrderId(orderId);
    }
    
    /**
     * 验证车票
     */
    @Transactional
    public boolean validateTicket(String ticketCode, Long routeId) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.TICKET_NOT_FOUND));
        
        // 检查是否已使用
        if (ticket.getUsageStatus() == UsageStatus.USED) {
            throw new BusinessException(ErrorCode.TICKET_ALREADY_USED);
        }
        
        // 检查是否过期
        if (LocalDateTime.now().isAfter(ticket.getValidUntil())) {
            ticket.setUsageStatus(UsageStatus.EXPIRED);
            ticketRepository.save(ticket);
            throw new BusinessException(ErrorCode.TICKET_EXPIRED);
        }
        
        // 检查线路是否匹配
        if (ticket.getRouteId() != null && !ticket.getRouteId().equals(routeId)) {
            throw new BusinessException(ErrorCode.TICKET_ROUTE_MISMATCH);
        }
        
        // 标记为已使用
        ticket.setUsageStatus(UsageStatus.USED);
        ticket.setUsedTime(LocalDateTime.now());
        ticketRepository.save(ticket);
        
        log.info("车票验证成功: ticketCode={}, routeId={}", ticketCode, routeId);
        return true;
    }
    
    /**
     * 转换为响应DTO
     */
    public TicketResponse toResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTicketCode(ticket.getTicketCode());
        response.setPurchasePrice(ticket.getPurchasePrice());
        response.setValidFrom(ticket.getValidFrom());
        response.setValidUntil(ticket.getValidUntil());
        response.setUsageStatus(ticket.getUsageStatus().name());
        response.setQrCodeData(ticket.getQrCodeData());
        response.setCreatedAt(ticket.getCreatedAt());
        return response;
    }
}
