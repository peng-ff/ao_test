package com.courseselection.repository;

import com.courseselection.entity.CompletedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 已完成课程Repository
 */
@Repository
public interface CompletedCourseRepository extends JpaRepository<CompletedCourse, Long> {

    /**
     * 查询学生已完成的课程列表
     */
    List<CompletedCourse> findByStudentId(Long studentId);

    /**
     * 检查学生是否完成某课程
     */
    @Query("SELECT COUNT(cc) > 0 FROM CompletedCourse cc " +
           "WHERE cc.studentId = :studentId AND cc.courseId = :courseId")
    boolean existsByStudentIdAndCourseId(
        @Param("studentId") Long studentId,
        @Param("courseId") Long courseId
    );
}
