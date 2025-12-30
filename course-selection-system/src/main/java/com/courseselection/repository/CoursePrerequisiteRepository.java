package com.courseselection.repository;

import com.courseselection.entity.CoursePrerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程前置关系Repository
 */
@Repository
public interface CoursePrerequisiteRepository extends JpaRepository<CoursePrerequisite, Long> {

    /**
     * 查询某课程的所有前置课程
     */
    List<CoursePrerequisite> findByCourseId(Long courseId);

    /**
     * 删除某课程的所有前置关系
     */
    void deleteByCourseId(Long courseId);
}
