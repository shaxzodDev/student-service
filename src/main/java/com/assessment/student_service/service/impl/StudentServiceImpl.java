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

/**
 * Service implementation for managing student-related operations.
 */
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentConverterService studentConverterService;

    /**
     * Creates a new student record.
     *
     * @param requestDTO the student creation request data
     * @return the response DTO with created student info
     */
    @Override
    public StudentCreateResponseDTO createStudent(StudentCreateRequestDTO requestDTO) {
        return studentConverterService.convertToCreateResponseDTO(
                studentRepository.save(studentConverterService.convertCreateRequestToEntity(requestDTO))
        );
    }

    /**
     * Retrieves a student by ID.
     *
     * @param id the ID of the student
     * @return detailed student information
     * @throws ResourceNotFoundException if the student is not found
     */
    @Override
    public StudentDetailDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentConverterService::convertToDetailDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    /**
     * Retrieves a paginated list of all students.
     *
     * @param pageNumber the page number to fetch
     * @param pageSize   the number of students per page
     * @return paginated list of student details
     */
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

    /**
     * Updates an existing student's details.
     *
     * @param id               the ID of the student to update
     * @param updateRequestDTO the new student data
     * @return the response DTO after the update
     * @throws ResourceNotFoundException if the student does not exist
     */
    @Override
    public StudentUpdateResponseDTO updateStudent(Long id, StudentUpdateRequestDTO updateRequestDTO) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        existing.setName(updateRequestDTO.getName());
        existing.setGrade(updateRequestDTO.getGrade());
        existing.setMobileNumber(updateRequestDTO.getMobileNumber());
        existing.setSchoolName(updateRequestDTO.getSchoolName());
        return studentConverterService.convertToUpdateResponseDTO(studentRepository.save(existing));
    }

    /**
     * Deletes a student by ID.
     *
     * @param id the ID of the student to delete
     */
    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
