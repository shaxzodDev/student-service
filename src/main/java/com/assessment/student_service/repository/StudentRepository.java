package com.assessment.student_service.repository;

import com.assessment.student_service.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s")
    Page<Student> findAllStudents(Pageable pageable);
}