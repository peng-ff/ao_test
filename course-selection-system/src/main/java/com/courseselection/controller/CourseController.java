package com.courseselection.controller;

import com.courseselection.dto.request.CourseRequest;
import com.courseselection.dto.response.ApiResponse;
import com.courseselection.dto.response.CourseResponse;
import com.courseselection.entity.Course;
import com.courseselection.entity.CourseSelection;
import com.courseselection.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 课程管理控制器
 */
@RestController
@RequestMapping("/api/courses")
@Tag(name = "课程管理接口", description = "课程的增删改查相关接口")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "创建课程", description = "管理员创建新课程")
    public ApiResponse<Course> createCourse(@Valid @RequestBody CourseRequest request) {
        Course course = courseService.createCourse(request);
        return ApiResponse.success("创建成功", course);
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改课程", description = "管理员修改课程信息")
    public ApiResponse<Course> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {
        Course course = courseService.updateCourse(id, request);
        return ApiResponse.success("修改成功", course);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程", description = "管理员删除课程")
    public ApiResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ApiResponse.success("删除成功", null);
    }

    @GetMapping
    @Operation(summary = "查询课程列表", description = "分页查询课程,支持多条件筛选")
    public ApiResponse<Page<Course>> getCourses(
            @Parameter(description = "课程名称(模糊搜索)")
            @RequestParam(required = false) String courseName,
            @Parameter(description = "教师名称")
            @RequestParam(required = false) String teacher,
            @Parameter(description = "学期")
            @RequestParam(required = false) String semester,
            @Parameter(description = "课程状态")
            @RequestParam(required = false) Course.CourseStatus status,
            @Parameter(description = "是否有余量")
            @RequestParam(required = false) Boolean hasCapacity,
            @Parameter(description = "页码(从0开始)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "排序方向(ASC/DESC)")
            @RequestParam(defaultValue = "ASC") String direction) {
        
        Sort.Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<Course> courses = courseService.getCourses(
                courseName, teacher, semester, status, hasCapacity, pageable);
        return ApiResponse.success(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询课程详情", description = "获取课程详细信息及时间表")
    public ApiResponse<CourseResponse> getCourseDetail(@PathVariable Long id) {
        CourseResponse course = courseService.getCourseDetail(id);
        return ApiResponse.success(course);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "设置课程状态", description = "管理员开放或关闭选课")
    public ApiResponse<Course> setCourseStatus(
            @PathVariable Long id,
            @Parameter(description = "课程状态(OPEN/CLOSED/ENDED)")
            @RequestParam Course.CourseStatus status) {
        Course course = courseService.setCourseStatus(id, status);
        return ApiResponse.success("状态修改成功", course);
    }

    @GetMapping("/{id}/selections")
    @Operation(summary = "查询课程选课情况", description = "查看某课程的选课学生列表")
    public ApiResponse<List<CourseSelection>> getCourseSelections(@PathVariable Long id) {
        List<CourseSelection> selections = courseService.getCourseSelections(id);
        return ApiResponse.success(selections);
    }
}
