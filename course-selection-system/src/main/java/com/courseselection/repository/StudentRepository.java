package com.courseselection.repository;

import com.courseselection.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 学生Repository
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * 根据学号查询学生
     */
    Optional<Student> findByStudentNumber(String studentNumber);

    /**
     * 检查学号是否存在
     */
    boolean existsByStudentNumber(String studentNumber);
}
