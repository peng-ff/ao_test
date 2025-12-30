package com.courseselection.dto.response;

import com.courseselection.entity.Course;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程响应
 */
@Data
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String courseName;
    private String description;
    private Integer credits;
    private Integer capacity;
    private Integer selectedCount;
    private String teacher;
    private String semester;
    private Course.CourseStatus status;
    private List<ScheduleResponse> schedules;
    private LocalDateTime createdAt;

    @Data
    public static class ScheduleResponse {
        private Long id;
        private String dayOfWeek;
        private Integer startPeriod;
        private Integer endPeriod;
        private String location;
    }
}
