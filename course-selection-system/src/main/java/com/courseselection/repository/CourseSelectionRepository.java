package com.courseselection.repository;

import com.courseselection.entity.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 选课记录Repository
 */
@Repository
public interface CourseSelectionRepository extends JpaRepository<CourseSelection, Long> {

    /**
     * 查询学生的已选课程(状态为SELECTED)
     */
    List<CourseSelection> findByStudentIdAndStatus(Long studentId, CourseSelection.SelectionStatus status);

    /**
     * 查询某课程的选课记录
     */
    List<CourseSelection> findByCourseIdAndStatus(Long courseId, CourseSelection.SelectionStatus status);

    /**
     * 检查学生是否已选某课程
     */
    @Query("SELECT COUNT(cs) > 0 FROM CourseSelection cs " +
           "WHERE cs.studentId = :studentId AND cs.courseId = :courseId AND cs.status = 'SELECTED'")
    boolean existsByStudentIdAndCourseIdAndStatusSelected(
        @Param("studentId") Long studentId,
        @Param("courseId") Long courseId
    );

    /**
     * 查询学生某课程的选课记录
     */
    Optional<CourseSelection> findByStudentIdAndCourseIdAndStatus(
        Long studentId,
        Long courseId,
        CourseSelection.SelectionStatus status
    );

    /**
     * 统计课程选课人数
     */
    @Query("SELECT COUNT(cs) FROM CourseSelection cs " +
           "WHERE cs.courseId = :courseId AND cs.status = 'SELECTED'")
    long countByCourseIdAndStatusSelected(@Param("courseId") Long courseId);
}
