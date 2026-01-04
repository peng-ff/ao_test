package com.snake.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 游戏记录实体类
 */
@Entity
@Table(name = "game_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "max_length", nullable = false)
    private Integer maxLength;

    @Column(name = "game_duration", nullable = false)
    private Integer gameDuration;

    @Column(name = "max_stage", nullable = false)
    private Integer maxStage;

    @Column(name = "items_collected")
    private Integer itemsCollected = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
