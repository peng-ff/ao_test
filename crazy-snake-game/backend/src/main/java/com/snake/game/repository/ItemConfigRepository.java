package com.snake.game.repository;

import com.snake.game.entity.ItemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 道具配置仓库接口
 */
@Repository
public interface ItemConfigRepository extends JpaRepository<ItemConfig, Long> {

    /**
     * 查找所有启用的道具配置
     */
    List<ItemConfig> findByIsActiveTrue();

    /**
     * 根据道具类型查找
     */
    ItemConfig findByItemType(String itemType);
}
