package com.assessment.student_service.service.impl;

import com.assessment.student_service.converter.StudentConverterService;
import com.assessment.student_service.dto.*;
import com.assessment.student_service.exception.ResourceNotFoundException;
import com.assessment.student_service.model.Student;
import com.assessment.student_service.repository.StudentRepository;
import com.assessment.student_service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentConverterService studentConverterService;

    @Override
    public StudentCreateResponseDTO createStudent(StudentCreateRequestDTO requestDTO) {
        return studentConverterService.convertToCreateResponseDTO(
                studentRepository.save(studentConverterService.convertCreateRequestToEntity(requestDTO))
        );
    }

    @Override
    public StudentDetailDTO getStudentById(Long id) {
        return studentRepository.findById(id).map(studentConverterService::convertToDetailDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public PaginationDTO<StudentDetailDTO> getAllStudents(int pageNumber, int pageSize) {
        Page<Student> students = studentRepository.findAllStudents(PageRequest.of(pageNumber, pageSize));
        return PaginationDTO.<StudentDetailDTO>builder()
                .content(students.getContent().stream().map(studentConverterService::convertToDetailDTO).toList())
                .pageNumber(students.getNumber())
                .pageSize(students.getSize())
                .totalElements(students.getTotalElements())
                .totalPages(students.getTotalPages())
                .build();
    }

    @Override
    public StudentUpdateResponseDTO updateStudent(Long id, StudentUpdateRequestDTO updateRequestDTO) {
        Student existing = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        existing.setName(updateRequestDTO.getName());
        existing.setGrade(updateRequestDTO.getGrade());
        existing.setMobileNumber(updateRequestDTO.getMobileNumber());
        existing.setSchoolName(updateRequestDTO.getSchoolName());
        return studentConverterService.convertToUpdateResponseDTO(studentRepository.save(existing));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
