package com.courseselection.repository;

import com.courseselection.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程时间表Repository
 */
@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {

    /**
     * 查询某课程的所有时间表
     */
    List<CourseSchedule> findByCourseId(Long courseId);

    /**
     * 删除某课程的所有时间表
     */
    void deleteByCourseId(Long courseId);

    /**
     * 查询学生已选课程的时间表
     */
    @Query("SELECT cs FROM CourseSchedule cs " +
           "WHERE cs.course.id IN " +
           "(SELECT sel.courseId FROM CourseSelection sel " +
           "WHERE sel.studentId = :studentId AND sel.status = 'SELECTED')")
    List<CourseSchedule> findByStudentSelectedCourses(@Param("studentId") Long studentId);
}
