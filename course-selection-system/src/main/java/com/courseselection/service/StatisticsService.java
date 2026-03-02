package com.courseselection.service;

import com.courseselection.entity.Course;
import com.courseselection.entity.CourseSelection;
import com.courseselection.repository.CourseRepository;
import com.courseselection.repository.CourseSelectionRepository;
import com.courseselection.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计报表服务
 */
@Service
public class StatisticsService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * 选课统计概览
     */
    public Map<String, Object> getOverview() {
        Map<String, Object> overview = new HashMap<>();

        // 总课程数
        long totalCourses = courseRepository.count();
        overview.put("totalCourses", totalCourses);

        // 总学生数
        long totalStudents = studentRepository.count();
        overview.put("totalStudents", totalStudents);

        // 总选课记录数(已选中状态)
        List<CourseSelection> selections = courseSelectionRepository.findAll();
        long activeSelections = selections.stream()
                .filter(s -> s.getStatus() == CourseSelection.SelectionStatus.SELECTED)
                .count();
        overview.put("totalSelections", activeSelections);

        // 开放选课的课程数
        List<Course> courses = courseRepository.findAll();
        long openCourses = courses.stream()
                .filter(c -> c.getStatus() == Course.CourseStatus.OPEN)
                .count();
        overview.put("openCourses", openCourses);

        // 已满课程数
        long fullCourses = courses.stream()
                .filter(c -> c.getSelectedCount() >= c.getCapacity())
                .count();
        overview.put("fullCourses", fullCourses);

        return overview;
    }

    /**
     * 课程选课统计
     */
    public List<Map<String, Object>> getCourseStatistics() {
        List<Course> courses = courseRepository.findAll();
        
        return courses.stream()
                .map(course -> {
                    Map<String, Object> stat = new HashMap<>();
                    stat.put("courseId", course.getId());
                    stat.put("courseCode", course.getCourseCode());
                    stat.put("courseName", course.getCourseName());
                    stat.put("teacher", course.getTeacher());
                    stat.put("capacity", course.getCapacity());
                    stat.put("selectedCount", course.getSelectedCount());
                    stat.put("availableCount", course.getCapacity() - course.getSelectedCount());
                    stat.put("selectionRate", 
                            course.getCapacity() > 0 ? 
                            String.format("%.2f%%", (course.getSelectedCount() * 100.0 / course.getCapacity())) : 
                            "0.00%");
                    stat.put("status", course.getStatus().name());
                    return stat;
                })
                .toList();
    }

    /**
     * 按学期统计选课数据
     */
    public Map<String, Object> getStatisticsBySemester(String semester) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 该学期的课程
        List<Course> courses = courseRepository.findAll().stream()
                .filter(c -> c.getSemester().equals(semester))
                .toList();
        
        statistics.put("semester", semester);
        statistics.put("totalCourses", courses.size());
        
        // 计算该学期总选课人数
        int totalSelections = courses.stream()
                .mapToInt(Course::getSelectedCount)
                .sum();
        statistics.put("totalSelections", totalSelections);
        
        // 计算该学期总容量
        int totalCapacity = courses.stream()
                .mapToInt(Course::getCapacity)
                .sum();
        statistics.put("totalCapacity", totalCapacity);
        
        // 计算选课率
        if (totalCapacity > 0) {
            double rate = (totalSelections * 100.0) / totalCapacity;
            statistics.put("selectionRate", String.format("%.2f%%", rate));
        } else {
            statistics.put("selectionRate", "0.00%");
        }
        
        return statistics;
    }
}
