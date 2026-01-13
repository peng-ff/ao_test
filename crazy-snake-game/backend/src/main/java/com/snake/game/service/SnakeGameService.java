package com.snake.game.service;

import com.snake.game.dto.request.GameRecordRequest;
import com.snake.game.dto.request.LoginRequest;
import com.snake.game.dto.request.RegisterRequest;
import com.snake.game.entity.GameRecord;
import com.snake.game.entity.ItemConfig;
import com.snake.game.entity.User;
import com.snake.game.repository.GameRecordRepository;
import com.snake.game.repository.ItemConfigRepository;
import com.snake.game.repository.UserRepository;
import com.snake.game.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnakeGameService {

    private final UserRepository userRepository;
    private final GameRecordRepository gameRecordRepository;
    private final ItemConfigRepository itemConfigRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @Transactional
    public Map<String, Object> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setHighestScore(0);
        user.setTotalGames(0);

        user = userRepository.save(user);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getUserId());
        result.put("username", user.getUsername());
        return result;
    }

    /**
     * 用户登录
     */
    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getUserId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getUserId());
        result.put("username", user.getUsername());
        return result;
    }

    /**
     * 保存游戏记录
     */
    @Transactional
    public Map<String, Object> saveGameRecord(Long userId, GameRecordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        GameRecord record = new GameRecord();
        record.setUserId(userId);
        record.setScore(request.getScore());
        record.setMaxLength(request.getMaxLength());
        record.setGameDuration(request.getGameDuration());
        record.setMaxStage(request.getMaxStage());
        record.setItemsCollected(request.getItemsCollected());

        record = gameRecordRepository.save(record);

        // 更新用户统计
        user.setTotalGames(user.getTotalGames() + 1);
        boolean isNewHighScore = false;
        if (request.getScore() > user.getHighestScore()) {
            user.setHighestScore(request.getScore());
            isNewHighScore = true;
        }
        userRepository.save(user);

        // 计算排名
        List<User> topUsers = userRepository.findAll().stream()
                .sorted((u1, u2) -> u2.getHighestScore().compareTo(u1.getHighestScore()))
                .collect(Collectors.toList());
        int ranking = topUsers.indexOf(user) + 1;

        Map<String, Object> result = new HashMap<>();
        result.put("recordId", record.getRecordId());
        result.put("isNewHighScore", isNewHighScore);
        result.put("ranking", ranking);
        return result;
    }

    /**
     * 获取用户游戏记录
     */
    public List<GameRecord> getUserRecords(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameRecordRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable).getContent();
    }

    /**
     * 获取全局排行榜
     */
    public List<Map<String, Object>> getGlobalRankings(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<User> topUsers = userRepository.findAll(pageable).getContent().stream()
                .sorted((u1, u2) -> u2.getHighestScore().compareTo(u1.getHighestScore()))
                .collect(Collectors.toList());

        return topUsers.stream().map((user) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("rank", topUsers.indexOf(user) + 1);
            map.put("username", user.getUsername());
            map.put("highestScore", user.getHighestScore());
            map.put("totalGames", user.getTotalGames());
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 获取每日排行榜
     */
    public List<Map<String, Object>> getDailyRankings(int limit) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Pageable pageable = PageRequest.of(0, limit);
        List<GameRecord> todayRecords = gameRecordRepository.findTodayTopScores(startOfDay, pageable);

        return todayRecords.stream().map(record -> {
            User user = userRepository.findById(record.getUserId()).orElse(null);
            Map<String, Object> map = new HashMap<>();
            map.put("rank", todayRecords.indexOf(record) + 1);
            map.put("username", user != null ? user.getUsername() : "Unknown");
            map.put("highestScore", record.getScore());
            map.put("totalGames", 1);
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 获取所有活跃道具配置
     */
    public List<ItemConfig> getActiveItems() {
        return itemConfigRepository.findByIsActiveTrue();
    }

    /**
     * 获取用户信息
     */
    public Map<String, Object> getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", user.getUserId());
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("highestScore", user.getHighestScore());
        profile.put("totalGames", user.getTotalGames());
        return profile;
    }
}
