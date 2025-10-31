package com.square.checkin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.time.LocalDate;

/**
 * 查询请求 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {
    
    /**
     * 页码（从1开始）
     */
    @Min(value = 1, message = "页码必须大于等于1")
    private Integer page = 1;
    
    /**
     * 每页记录数（最大100）
     */
    @Min(value = 1, message = "每页记录数必须大于等于1")
    @Max(value = 100, message = "每页记录数不能超过100")
    private Integer pageSize = 20;
    
    /**
     * 开始日期
     */
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    private LocalDate endDate;
}
