package com.snake.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 道具配置实体类
 */
@Entity
@Table(name = "item_configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_type", nullable = false, unique = true, length = 50)
    private String itemType;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Column(name = "effect_description", nullable = false, length = 255)
    private String effectDescription;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "rarity", nullable = false, length = 20)
    private String rarity;

    @Column(name = "spawn_weight", nullable = false)
    private Integer spawnWeight;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
