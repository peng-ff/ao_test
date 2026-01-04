package com.snake.game.repository;

import com.snake.game.entity.GameRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 游戏记录仓库接口
 */
@Repository
public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

    /**
     * 根据用户ID查找游戏记录
     */
    Page<GameRecord> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 查找用户的最高分记录
     */
    GameRecord findFirstByUserIdOrderByScoreDesc(Long userId);

    /**
     * 查询今日排行榜
     */
    @Query("SELECT gr FROM GameRecord gr WHERE gr.createdAt >= :startOfDay ORDER BY gr.score DESC")
    List<GameRecord> findTodayTopScores(LocalDateTime startOfDay, Pageable pageable);
}
