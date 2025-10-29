package com.square.checkin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 签到请求数据传输对象 (DTO)
 * 
 * <p>用于封装用户签到请求的数据，包含签到操作所需的基本信息。
 * 该类使用 Lombok 注解简化代码，自动生成 getter、setter、构造器等方法。
 * 
 * <p>使用场景：
 * <ul>
 *   <li>用户执行每日签到操作</li>
 *   <li>前端向后端提交签到请求</li>
 *   <li>签到接口的入参封装</li>
 * </ul>
 * 
 * <p>验证规则：
 * <ul>
 *   <li>用户ID不能为空</li>
 *   <li>用户ID必须为正整数</li>
 * </ul>
 * 
 * @author Check-In System
 * @version 1.0
 * @since 1.0
 * @see javax.validation.constraints.NotNull
 * @see javax.validation.constraints.Positive
 */
@Data  // Lombok注解：自动生成getter、setter、toString、equals、hashCode方法
@Builder  // Lombok注解：提供建造者模式的构建方式，方便对象创建
@NoArgsConstructor  // Lombok注解：自动生成无参构造器
@AllArgsConstructor  // Lombok注解：自动生成全参构造器
public class CheckInRequest {
    
    /**
     * 用户唯一标识ID
     * 
     * <p>用于标识执行签到操作的用户身份。
     * 该字段必须满足以下条件：
     * <ul>
     *   <li>不能为 null（必填字段）</li>
     *   <li>必须为正整数（大于0的长整型数值）</li>
     * </ul>
     * 
     * <p>示例值：123456L
     * 
     * @see javax.validation.constraints.NotNull 非空校验
     * @see javax.validation.constraints.Positive 正数校验
     */
    @NotNull(message = "用户ID不能为空")  // JSR-303校验：确保字段值不为null
    @Positive(message = "用户ID必须为正整数")  // JSR-303校验：确保字段值为正数（>0）
    private Long userId;
}
