package com.snake.game.controller;

import com.snake.game.dto.request.GameRecordRequest;
import com.snake.game.dto.request.LoginRequest;
import com.snake.game.dto.request.RegisterRequest;
import com.snake.game.dto.response.Result;
import com.snake.game.entity.GameRecord;
import com.snake.game.entity.ItemConfig;
import com.snake.game.service.SnakeGameService;
import com.snake.game.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SnakeGameController {

    private final SnakeGameService snakeGameService;
    private final JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @PostMapping("/auth/register")
    public Result<Map<String, Object>> register(@Validated @RequestBody RegisterRequest request) {
        try {
            Map<String, Object> result = snakeGameService.register(request);
            return Result.success("注册成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/auth/login")
    public Result<Map<String, Object>> login(@Validated @RequestBody LoginRequest request) {
        try {
            Map<String, Object> result = snakeGameService.login(request);
            return Result.success("登录成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 保存游戏记录
     */
    @PostMapping("/game/record")
    public Result<Map<String, Object>> saveRecord(
            @RequestHeader("Authorization") String token,
            @Validated @RequestBody GameRecordRequest request) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(jwt);
            Map<String, Object> result = snakeGameService.saveGameRecord(userId, request);
            return Result.success("保存成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户游戏记录
     */
    @GetMapping("/game/records")
    public Result<List<GameRecord>> getRecords(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(jwt);
            List<GameRecord> records = snakeGameService.getUserRecords(userId, page, size);
            return Result.success(records);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取全局排行榜
     */
    @GetMapping("/ranking/global")
    public Result<List<Map<String, Object>>> getGlobalRankings(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Map<String, Object>> rankings = snakeGameService.getGlobalRankings(limit);
            return Result.success(rankings);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取每日排行榜
     */
    @GetMapping("/ranking/daily")
    public Result<List<Map<String, Object>>> getDailyRankings(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Map<String, Object>> rankings = snakeGameService.getDailyRankings(limit);
            return Result.success(rankings);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取道具配置列表
     */
    @GetMapping("/game/items")
    public Result<List<ItemConfig>> getItems() {
        try {
            List<ItemConfig> items = snakeGameService.getActiveItems();
            return Result.success(items);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/user/profile")
    public Result<Map<String, Object>> getUserProfile(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(jwt);
            Map<String, Object> profile = snakeGameService.getUserProfile(userId);
            return Result.success(profile);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户统计数据
     */
    @GetMapping("/user/stats")
    public Result<Map<String, Object>> getUserStats(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(jwt);
            Map<String, Object> stats = snakeGameService.getUserProfile(userId);
            return Result.success(stats);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
