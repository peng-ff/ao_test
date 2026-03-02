package com.courseselection.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 选课请求
 */
@Data
public class SelectCourseRequest {

    @NotNull(message = "课程ID不能为空")
    private Long courseId;
}
