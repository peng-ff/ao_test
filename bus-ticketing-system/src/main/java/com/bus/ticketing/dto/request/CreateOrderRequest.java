package com.bus.ticketing.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 创建订单请求DTO
 */
@Data
public class CreateOrderRequest {
    
    @NotNull(message = "线路ID不能为空")
    private Long routeId;
    
    @NotNull(message = "票务类型ID不能为空")
    private Long ticketTypeId;
    
    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量至少为1")
    private Integer quantity;
}
