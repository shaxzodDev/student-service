package com.assessment.student_service.converter;

import com.assessment.student_service.dto.*;
import com.assessment.student_service.model.Student;

public interface StudentConverterService {
    Student convertCreateRequestToEntity(StudentCreateRequestDTO responseDTO);
    StudentCreateResponseDTO convertToCreateResponseDTO(Student student);
    StudentUpdateResponseDTO convertToUpdateResponseDTO(Student student);
    StudentDetailDTO convertToDetailDTO(Student student);
}
