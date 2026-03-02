package com.courseselection.controller;

import com.courseselection.dto.response.ApiResponse;
import com.courseselection.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 统计报表控制器
 */
@RestController
@RequestMapping("/api/statistics")
@Tag(name = "统计报表接口", description = "选课数据统计相关接口")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/overview")
    @Operation(summary = "选课统计概览", description = "获取当前学期选课整体情况")
    public ApiResponse<Map<String, Object>> getOverview() {
        Map<String, Object> overview = statisticsService.getOverview();
        return ApiResponse.success(overview);
    }

    @GetMapping("/courses")
    @Operation(summary = "课程选课统计", description = "获取各课程选课人数统计")
    public ApiResponse<List<Map<String, Object>>> getCourseStatistics() {
        List<Map<String, Object>> statistics = statisticsService.getCourseStatistics();
        return ApiResponse.success(statistics);
    }

    @GetMapping("/semester")
    @Operation(summary = "学期选课统计", description = "按学期统计选课数据")
    public ApiResponse<Map<String, Object>> getStatisticsBySemester(
            @Parameter(description = "学期(如: 2024-2025-1)")
            @RequestParam String semester) {
        Map<String, Object> statistics = statisticsService.getStatisticsBySemester(semester);
        return ApiResponse.success(statistics);
    }
}
