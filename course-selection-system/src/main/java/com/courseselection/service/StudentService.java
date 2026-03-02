package com.courseselection.service;

import com.courseselection.dto.request.CreateStudentRequest;
import com.courseselection.entity.CompletedCourse;
import com.courseselection.entity.Student;
import com.courseselection.exception.BusinessException;
import com.courseselection.exception.ResourceNotFoundException;
import com.courseselection.repository.CompletedCourseRepository;
import com.courseselection.repository.CourseRepository;
import com.courseselection.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生管理服务
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompletedCourseRepository completedCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 创建学生
     */
    @Transactional
    public Student createStudent(CreateStudentRequest request) {
        // 检查学号是否已存在
        if (studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new BusinessException("学号已存在");
        }

        Student student = new Student();
        student.setStudentNumber(request.getStudentNumber());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setMajor(request.getMajor());
        student.setGrade(request.getGrade());

        return studentRepository.save(student);
    }

    /**
     * 修改学生信息
     */
    @Transactional
    public Student updateStudent(Long id, CreateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));

        // 检查学号是否与其他学生重复
        Student existingStudent = studentRepository.findByStudentNumber(request.getStudentNumber()).orElse(null);
        if (existingStudent != null && !existingStudent.getId().equals(id)) {
            throw new BusinessException("学号已被其他学生使用");
        }

        student.setStudentNumber(request.getStudentNumber());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setMajor(request.getMajor());
        student.setGrade(request.getGrade());

        // 只有提供了新密码才更新密码
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return studentRepository.save(student);
    }

    /**
     * 查询学生列表
     */
    public Page<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    /**
     * 查询学生详情
     */
    public Student getStudentDetail(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));
    }

    /**
     * 重置学生密码
     */
    @Transactional
    public void resetPassword(Long id, String newPassword) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));
        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);
    }

    /**
     * 添加已完成课程
     */
    @Transactional
    public CompletedCourse addCompletedCourse(Long studentId, Long courseId, String semester) {
        // 验证学生存在
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));

        // 验证课程存在
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));

        // 检查是否已记录
        if (completedCourseRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BusinessException("该课程已在学生的完成记录中");
        }

        CompletedCourse completedCourse = new CompletedCourse();
        completedCourse.setStudentId(studentId);
        completedCourse.setCourseId(courseId);
        completedCourse.setSemester(semester);

        return completedCourseRepository.save(completedCourse);
    }

    /**
     * 查询学生已完成课程
     */
    public List<CompletedCourse> getCompletedCourses(Long studentId) {
        // 验证学生存在
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));
        
        return completedCourseRepository.findByStudentId(studentId);
    }
}
