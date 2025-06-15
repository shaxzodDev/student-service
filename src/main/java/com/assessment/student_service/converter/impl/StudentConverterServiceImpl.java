package com.assessment.student_service.converter.impl;

import com.assessment.student_service.converter.StudentConverterService;
import com.assessment.student_service.dto.StudentCreateRequestDTO;
import com.assessment.student_service.dto.StudentCreateResponseDTO;
import com.assessment.student_service.dto.StudentDetailDTO;
import com.assessment.student_service.dto.StudentUpdateResponseDTO;
import com.assessment.student_service.model.Student;
import org.springframework.stereotype.Service;

/**
 * Implementation of the StudentConverterService interface.
 * Handles conversion between Student entity and its DTO representations.
 */
@Service
public class StudentConverterServiceImpl implements StudentConverterService {

    /**
     * Converts a StudentCreateRequestDTO to a Student entity.
     *
     * @param requestDTO DTO containing data to create a student
     * @return a new Student entity populated with data from the DTO
     */
    @Override
    public Student convertCreateRequestToEntity(StudentCreateRequestDTO requestDTO) {
        Student student = new Student();
        student.setName(requestDTO.getName());
        student.setGrade(requestDTO.getGrade());
        student.setMobileNumber(requestDTO.getMobileNumber());
        student.setSchoolName(requestDTO.getSchoolName());
        return student;
    }

    /**
     * Converts a Student entity to a StudentCreateResponseDTO.
     *
     * @param student the saved student entity
     * @return DTO containing created student details
     */
    @Override
    public StudentCreateResponseDTO convertToCreateResponseDTO(Student student) {
        StudentCreateResponseDTO studentCreateResponseDTO = new StudentCreateResponseDTO();
        studentCreateResponseDTO.setId(student.getId());
        studentCreateResponseDTO.setName(student.getName());
        studentCreateResponseDTO.setGrade(student.getGrade());
        studentCreateResponseDTO.setMobileNumber(student.getMobileNumber());
        studentCreateResponseDTO.setSchoolName(student.getSchoolName());
        return studentCreateResponseDTO;
    }

    /**
     * Converts a Student entity to a StudentUpdateResponseDTO.
     *
     * @param student the updated student entity
     * @return DTO containing updated student details
     */
    @Override
    public StudentUpdateResponseDTO convertToUpdateResponseDTO(Student student) {
        StudentUpdateResponseDTO studentUpdateResponseDTO = new StudentUpdateResponseDTO();
        studentUpdateResponseDTO.setId(student.getId());
        studentUpdateResponseDTO.setName(student.getName());
        studentUpdateResponseDTO.setGrade(student.getGrade());
        studentUpdateResponseDTO.setMobileNumber(student.getMobileNumber());
        studentUpdateResponseDTO.setSchoolName(student.getSchoolName());
        return studentUpdateResponseDTO;
    }

    /**
     * Converts a Student entity to a StudentDetailDTO.
     *
     * @param student the student entity
     * @return DTO with detailed student information
     */
    @Override
    public StudentDetailDTO convertToDetailDTO(Student student) {
        StudentDetailDTO studentDetailDTO = new StudentDetailDTO();
        studentDetailDTO.setId(student.getId());
        studentDetailDTO.setName(student.getName());
        studentDetailDTO.setGrade(student.getGrade());
        studentDetailDTO.setMobileNumber(student.getMobileNumber());
        studentDetailDTO.setSchoolName(student.getSchoolName());
        return studentDetailDTO;
    }
}
