package com.courseselection.dto.request;

import com.courseselection.entity.CourseSchedule;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 创建/修改课程请求
 */
@Data
public class CourseRequest {

    @NotBlank(message = "课程代码不能为空")
    @Size(max = 20, message = "课程代码长度不能超过20")
    private String courseCode;

    @NotBlank(message = "课程名称不能为空")
    @Size(max = 100, message = "课程名称长度不能超过100")
    private String courseName;

    private String description;

    @NotNull(message = "学分不能为空")
    @Min(value = 1, message = "学分必须大于0")
    private Integer credits;

    @NotNull(message = "课程容量不能为空")
    @Min(value = 1, message = "课程容量必须大于0")
    private Integer capacity;

    @NotBlank(message = "授课教师不能为空")
    @Size(max = 50, message = "教师名称长度不能超过50")
    private String teacher;

    @NotBlank(message = "学期不能为空")
    @Size(max = 20, message = "学期长度不能超过20")
    private String semester;

    private List<ScheduleRequest> schedules;

    @Data
    public static class ScheduleRequest {
        @NotNull(message = "星期不能为空")
        private CourseSchedule.DayOfWeek dayOfWeek;

        @NotNull(message = "开始节次不能为空")
        @Min(value = 1, message = "开始节次必须在1-12之间")
        @Max(value = 12, message = "开始节次必须在1-12之间")
        private Integer startPeriod;

        @NotNull(message = "结束节次不能为空")
        @Min(value = 1, message = "结束节次必须在1-12之间")
        @Max(value = 12, message = "结束节次必须在1-12之间")
        private Integer endPeriod;

        @Size(max = 50, message = "地点长度不能超过50")
        private String location;
    }
}
