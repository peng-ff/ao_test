package com.snake.game.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 游戏记录保存请求DTO
 */
@Data
public class GameRecordRequest {

    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数不能为负数")
    private Integer score;

    @NotNull(message = "最大长度不能为空")
    @Min(value = 3, message = "最大长度不能小于3")
    private Integer maxLength;

    @NotNull(message = "游戏时长不能为空")
    @Min(value = 0, message = "游戏时长不能为负数")
    private Integer gameDuration;

    @NotNull(message = "最高阶段不能为空")
    @Min(value = 1, message = "最高阶段不能小于1")
    private Integer maxStage;

    @NotNull(message = "道具收集数量不能为空")
    @Min(value = 0, message = "道具收集数量不能为负数")
    private Integer itemsCollected;
}
