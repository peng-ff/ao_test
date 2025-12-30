package com.courseselection.service;

import com.courseselection.dto.request.CourseRequest;
import com.courseselection.dto.response.CourseResponse;
import com.courseselection.entity.Course;
import com.courseselection.entity.CourseSchedule;
import com.courseselection.entity.CourseSelection;
import com.courseselection.exception.BusinessException;
import com.courseselection.exception.ResourceNotFoundException;
import com.courseselection.repository.CourseRepository;
import com.courseselection.repository.CourseScheduleRepository;
import com.courseselection.repository.CourseSelectionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程管理服务
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    /**
     * 创建课程
     */
    @Transactional
    public Course createCourse(CourseRequest request) {
        // 检查课程代码是否重复
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new BusinessException("课程代码已存在");
        }

        // 创建课程
        Course course = new Course();
        BeanUtils.copyProperties(request, course);
        course.setSelectedCount(0);
        course.setStatus(Course.CourseStatus.OPEN);
        course = courseRepository.save(course);

        // 创建课程时间表
        if (request.getSchedules() != null && !request.getSchedules().isEmpty()) {
            List<CourseSchedule> schedules = new ArrayList<>();
            for (CourseRequest.ScheduleRequest scheduleReq : request.getSchedules()) {
                CourseSchedule schedule = new CourseSchedule();
                schedule.setCourse(course);
                schedule.setDayOfWeek(scheduleReq.getDayOfWeek());
                schedule.setStartPeriod(scheduleReq.getStartPeriod());
                schedule.setEndPeriod(scheduleReq.getEndPeriod());
                schedule.setLocation(scheduleReq.getLocation());
                schedules.add(schedule);
            }
            courseScheduleRepository.saveAll(schedules);
            course.setSchedules(schedules);
        }

        return course;
    }

    /**
     * 更新课程
     */
    @Transactional
    public Course updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));

        // 检查课程代码是否与其他课程重复
        Course existingCourse = courseRepository.findByCourseCode(request.getCourseCode()).orElse(null);
        if (existingCourse != null && !existingCourse.getId().equals(id)) {
            throw new BusinessException("课程代码已被其他课程使用");
        }

        // 更新课程基本信息
        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setCredits(request.getCredits());
        course.setCapacity(request.getCapacity());
        course.setTeacher(request.getTeacher());
        course.setSemester(request.getSemester());

        // 更新课程时间表
        if (request.getSchedules() != null) {
            // 删除旧的时间表
            courseScheduleRepository.deleteByCourseId(id);

            // 创建新的时间表
            List<CourseSchedule> schedules = new ArrayList<>();
            for (CourseRequest.ScheduleRequest scheduleReq : request.getSchedules()) {
                CourseSchedule schedule = new CourseSchedule();
                schedule.setCourse(course);
                schedule.setDayOfWeek(scheduleReq.getDayOfWeek());
                schedule.setStartPeriod(scheduleReq.getStartPeriod());
                schedule.setEndPeriod(scheduleReq.getEndPeriod());
                schedule.setLocation(scheduleReq.getLocation());
                schedules.add(schedule);
            }
            courseScheduleRepository.saveAll(schedules);
        }

        return courseRepository.save(course);
    }

    /**
     * 删除课程
     */
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));

        // 检查是否有学生已选该课程
        long selectionCount = courseSelectionRepository.countByCourseIdAndStatusSelected(id);
        if (selectionCount > 0) {
            throw new BusinessException("该课程已有学生选课,无法删除");
        }

        courseRepository.delete(course);
    }

    /**
     * 查询课程列表(支持筛选)
     */
    public Page<Course> getCourses(String courseName, String teacher, String semester,
                                    Course.CourseStatus status, Boolean hasCapacity, Pageable pageable) {
        return courseRepository.findWithFilters(courseName, teacher, semester, status, hasCapacity, pageable);
    }

    /**
     * 查询课程详情
     */
    public CourseResponse getCourseDetail(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));

        CourseResponse response = new CourseResponse();
        BeanUtils.copyProperties(course, response);

        // 获取课程时间表
        List<CourseSchedule> schedules = courseScheduleRepository.findByCourseId(id);
        List<CourseResponse.ScheduleResponse> scheduleResponses = schedules.stream()
                .map(schedule -> {
                    CourseResponse.ScheduleResponse scheduleRes = new CourseResponse.ScheduleResponse();
                    scheduleRes.setId(schedule.getId());
                    scheduleRes.setDayOfWeek(schedule.getDayOfWeek().name());
                    scheduleRes.setStartPeriod(schedule.getStartPeriod());
                    scheduleRes.setEndPeriod(schedule.getEndPeriod());
                    scheduleRes.setLocation(schedule.getLocation());
                    return scheduleRes;
                })
                .collect(Collectors.toList());
        response.setSchedules(scheduleResponses);

        return response;
    }

    /**
     * 设置课程状态
     */
    @Transactional
    public Course setCourseStatus(Long id, Course.CourseStatus status) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));
        course.setStatus(status);
        return courseRepository.save(course);
    }

    /**
     * 查询课程选课情况
     */
    public List<CourseSelection> getCourseSelections(Long courseId) {
        // 验证课程存在
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));
        
        return courseSelectionRepository.findByCourseIdAndStatus(
                courseId, CourseSelection.SelectionStatus.SELECTED);
    }
}
