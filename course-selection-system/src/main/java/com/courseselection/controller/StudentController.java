package com.courseselection.controller;

import com.courseselection.dto.request.CreateStudentRequest;
import com.courseselection.dto.response.ApiResponse;
import com.courseselection.entity.CompletedCourse;
import com.courseselection.entity.Student;
import com.courseselection.service.StudentService;
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
 * 学生管理控制器
 */
@RestController
@RequestMapping("/api/students")
@Tag(name = "学生管理接口", description = "管理员管理学生相关接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    @Operation(summary = "创建学生", description = "管理员创建学生账户")
    public ApiResponse<Student> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        Student student = studentService.createStudent(request);
        return ApiResponse.success("创建成功", student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改学生信息", description = "管理员修改学生信息")
    public ApiResponse<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody CreateStudentRequest request) {
        Student student = studentService.updateStudent(id, request);
        return ApiResponse.success("修改成功", student);
    }

    @GetMapping
    @Operation(summary = "查询学生列表", description = "管理员分页查询学生")
    public ApiResponse<Page<Student>> getStudents(
            @Parameter(description = "页码(从0开始)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段")
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Student> students = studentService.getStudents(pageable);
        return ApiResponse.success(students);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询学生详情", description = "查看学生详细信息")
    public ApiResponse<Student> getStudentDetail(@PathVariable Long id) {
        Student student = studentService.getStudentDetail(id);
        return ApiResponse.success(student);
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "重置学生密码", description = "管理员重置学生密码")
    public ApiResponse<Void> resetPassword(
            @PathVariable Long id,
            @Parameter(description = "新密码")
            @RequestParam String newPassword) {
        studentService.resetPassword(id, newPassword);
        return ApiResponse.success("密码重置成功", null);
    }

    @PostMapping("/{id}/completed-courses")
    @Operation(summary = "添加已完成课程", description = "为学生添加已完成课程记录")
    public ApiResponse<CompletedCourse> addCompletedCourse(
            @PathVariable Long id,
            @Parameter(description = "课程ID")
            @RequestParam Long courseId,
            @Parameter(description = "完成学期")
            @RequestParam String semester) {
        CompletedCourse completedCourse = studentService.addCompletedCourse(id, courseId, semester);
        return ApiResponse.success("添加成功", completedCourse);
    }

    @GetMapping("/{id}/completed-courses")
    @Operation(summary = "查询已完成课程", description = "查询学生已完成的课程列表")
    public ApiResponse<List<CompletedCourse>> getCompletedCourses(@PathVariable Long id) {
        List<CompletedCourse> completedCourses = studentService.getCompletedCourses(id);
        return ApiResponse.success(completedCourses);
    }
}
