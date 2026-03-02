package com.courseselection.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程实体类
 */
@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", unique = true, nullable = false, length = 20)
    private String courseCode;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "selected_count", nullable = false)
    private Integer selectedCount = 0;

    @Column(name = "teacher", nullable = false, length = 50)
    private String teacher;

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CourseStatus status = CourseStatus.OPEN;

    @Version
    @Column(name = "version")
    private Integer version = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CourseSchedule> schedules = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * 课程状态枚举
     */
    public enum CourseStatus {
        OPEN,    // 开放选课
        CLOSED,  // 关闭选课
        ENDED    // 已结束
    }
}
