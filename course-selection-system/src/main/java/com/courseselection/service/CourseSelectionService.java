package com.courseselection.service;

import com.courseselection.entity.*;
import com.courseselection.exception.BusinessException;
import com.courseselection.exception.ResourceNotFoundException;
import com.courseselection.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 选课服务
 * 核心业务:选课、退课、冲突检测
 */
@Service
public class CourseSelectionService {

    private static final Logger logger = LoggerFactory.getLogger(CourseSelectionService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Autowired
    private CoursePrerequisiteRepository coursePrerequisiteRepository;

    @Autowired
    private CompletedCourseRepository completedCourseRepository;

    /**
     * 学生选课
     */
    @Transactional
    public CourseSelection selectCourse(Long studentId, Long courseId) {
        logger.info("学生 {} 尝试选课 {}", studentId, courseId);

        // 1. 验证课程存在
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));

        // 2. 检查课程状态
        if (course.getStatus() != Course.CourseStatus.OPEN) {
            throw new BusinessException("课程未开放选课");
        }

        // 3. 检查是否已选
        if (courseSelectionRepository.existsByStudentIdAndCourseIdAndStatusSelected(studentId, courseId)) {
            throw new BusinessException("您已选择该课程");
        }

        // 4. 检查课程容量
        if (course.getSelectedCount() >= course.getCapacity()) {
            throw new BusinessException("课程已满");
        }

        // 5. 检查前置课程
        checkPrerequisites(studentId, courseId);

        // 6. 检查时间冲突
        checkTimeConflict(studentId, courseId);

        // 7. 创建选课记录
        CourseSelection selection = new CourseSelection();
        selection.setStudentId(studentId);
        selection.setCourseId(courseId);
        selection.setStatus(CourseSelection.SelectionStatus.SELECTED);
        selection = courseSelectionRepository.save(selection);

        // 8. 增加课程选课人数(使用乐观锁)
        course.setSelectedCount(course.getSelectedCount() + 1);
        courseRepository.save(course);

        logger.info("学生 {} 选课成功: {}", studentId, courseId);
        return selection;
    }

    /**
     * 学生退课
     */
    @Transactional
    public void dropCourse(Long studentId, Long selectionId) {
        logger.info("学生 {} 尝试退课,选课记录ID: {}", studentId, selectionId);

        // 1. 查询选课记录
        CourseSelection selection = courseSelectionRepository.findById(selectionId)
                .orElseThrow(() -> new ResourceNotFoundException("选课记录不存在"));

        // 2. 验证是否是本人的选课记录
        if (!selection.getStudentId().equals(studentId)) {
            throw new BusinessException("无权操作此选课记录");
        }

        // 3. 检查是否已退课
        if (selection.getStatus() == CourseSelection.SelectionStatus.DROPPED) {
            throw new BusinessException("该课程已退选");
        }

        // 4. 更新选课状态
        selection.setStatus(CourseSelection.SelectionStatus.DROPPED);
        courseSelectionRepository.save(selection);

        // 5. 减少课程选课人数
        Course course = courseRepository.findById(selection.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));
        course.setSelectedCount(Math.max(0, course.getSelectedCount() - 1));
        courseRepository.save(course);

        logger.info("学生 {} 退课成功,课程ID: {}", studentId, selection.getCourseId());
    }

    /**
     * 查询学生已选课程
     */
    public List<CourseSelection> getMySelections(Long studentId) {
        return courseSelectionRepository.findByStudentIdAndStatus(
                studentId, CourseSelection.SelectionStatus.SELECTED);
    }

    /**
     * 检查前置课程
     */
    private void checkPrerequisites(Long studentId, Long courseId) {
        List<CoursePrerequisite> prerequisites = coursePrerequisiteRepository.findByCourseId(courseId);
        
        if (prerequisites.isEmpty()) {
            return;
        }

        // 获取学生已完成的课程
        List<Long> completedCourseIds = completedCourseRepository.findByStudentId(studentId)
                .stream()
                .map(CompletedCourse::getCourseId)
                .collect(Collectors.toList());

        // 检查是否满足所有前置课程
        for (CoursePrerequisite prerequisite : prerequisites) {
            if (!completedCourseIds.contains(prerequisite.getPrerequisiteCourseId())) {
                Course prerequisiteCourse = courseRepository.findById(prerequisite.getPrerequisiteCourseId())
                        .orElse(null);
                String courseName = prerequisiteCourse != null ? prerequisiteCourse.getCourseName() : "未知课程";
                throw new BusinessException("未满足前置课程要求: " + courseName);
            }
        }
    }

    /**
     * 检查时间冲突
     */
    private void checkTimeConflict(Long studentId, Long courseId) {
        // 获取要选课程的时间表
        List<CourseSchedule> newCourseSchedules = courseScheduleRepository.findByCourseId(courseId);
        
        if (newCourseSchedules.isEmpty()) {
            return;
        }

        // 获取学生已选课程的时间表
        List<CourseSchedule> existingSchedules = courseScheduleRepository.findByStudentSelectedCourses(studentId);

        // 检查时间冲突
        for (CourseSchedule newSchedule : newCourseSchedules) {
            for (CourseSchedule existingSchedule : existingSchedules) {
                if (isTimeConflict(newSchedule, existingSchedule)) {
                    Course conflictCourse = courseRepository.findById(existingSchedule.getCourse().getId())
                            .orElse(null);
                    String conflictCourseName = conflictCourse != null ? conflictCourse.getCourseName() : "已选课程";
                    throw new BusinessException(String.format(
                            "与已选课程时间冲突: %s (%s %d-%d节)",
                            conflictCourseName,
                            existingSchedule.getDayOfWeek(),
                            existingSchedule.getStartPeriod(),
                            existingSchedule.getEndPeriod()
                    ));
                }
            }
        }
    }

    /**
     * 判断两个时间段是否冲突
     */
    private boolean isTimeConflict(CourseSchedule schedule1, CourseSchedule schedule2) {
        // 不是同一天,不冲突
        if (schedule1.getDayOfWeek() != schedule2.getDayOfWeek()) {
            return false;
        }

        // 检查时间段是否重叠
        return !(schedule1.getEndPeriod() < schedule2.getStartPeriod() ||
                 schedule1.getStartPeriod() > schedule2.getEndPeriod());
    }
}
