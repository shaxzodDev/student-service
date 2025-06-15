package com.assessment.student_service.service;

import com.assessment.student_service.dto.*;

public interface StudentService {
    StudentCreateResponseDTO createStudent(StudentCreateRequestDTO requestDTO);
    StudentDetailDTO getStudentById(Long id);
    PaginationDTO<StudentDetailDTO> getAllStudents(int pageNumber, int pageSize);
    StudentUpdateResponseDTO updateStudent(Long id, StudentUpdateRequestDTO requestDTO);
    void deleteStudent(Long id);
}
