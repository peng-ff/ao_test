package com.courseselection.controller;

import com.courseselection.dto.request.SelectCourseRequest;
import com.courseselection.dto.response.ApiResponse;
import com.courseselection.entity.CourseSelection;
import com.courseselection.service.CourseSelectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 选课控制器
 */
@RestController
@RequestMapping("/api/selections")
@Tag(name = "选课接口", description = "学生选课、退课相关接口")
public class CourseSelectionController {

    @Autowired
    private CourseSelectionService courseSelectionService;

    @PostMapping
    @Operation(summary = "选课")
    public ApiResponse<CourseSelection> selectCourse(
            @Valid @RequestBody SelectCourseRequest request,
            Authentication authentication) {
        Long studentId = (Long) authentication.getPrincipal();
        CourseSelection selection = courseSelectionService.selectCourse(studentId, request.getCourseId());
        return ApiResponse.success("选课成功", selection);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "退课")
    public ApiResponse<Void> dropCourse(
            @PathVariable Long id,
            Authentication authentication) {
        Long studentId = (Long) authentication.getPrincipal();
        courseSelectionService.dropCourse(studentId, id);
        return ApiResponse.success("退课成功", null);
    }

    @GetMapping("/my")
    @Operation(summary = "查询我的选课")
    public ApiResponse<List<CourseSelection>> getMySelections(Authentication authentication) {
        Long studentId = (Long) authentication.getPrincipal();
        List<CourseSelection> selections = courseSelectionService.getMySelections(studentId);
        return ApiResponse.success(selections);
    }
}
