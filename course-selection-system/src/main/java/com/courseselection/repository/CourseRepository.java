package com.courseselection.repository;

import com.courseselection.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 课程Repository
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * 根据课程代码查询课程
     */
    Optional<Course> findByCourseCode(String courseCode);

    /**
     * 检查课程代码是否存在
     */
    boolean existsByCourseCode(String courseCode);

    /**
     * 分页查询课程,支持多条件筛选
     */
    @Query("SELECT c FROM Course c WHERE " +
           "(:courseName IS NULL OR c.courseName LIKE %:courseName%) AND " +
           "(:teacher IS NULL OR c.teacher LIKE %:teacher%) AND " +
           "(:semester IS NULL OR c.semester = :semester) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:hasCapacity IS NULL OR (:hasCapacity = true AND c.selectedCount < c.capacity) OR (:hasCapacity = false))")
    Page<Course> findWithFilters(
        @Param("courseName") String courseName,
        @Param("teacher") String teacher,
        @Param("semester") String semester,
        @Param("status") Course.CourseStatus status,
        @Param("hasCapacity") Boolean hasCapacity,
        Pageable pageable
    );
}
