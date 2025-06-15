package com.assessment.student_service.controller;

import com.assessment.student_service.dto.*;
import com.assessment.student_service.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Student entities.
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Creates a new student.
     */
    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<StudentCreateResponseDTO> create(@Valid @RequestBody StudentCreateRequestDTO requestDTO) {
        log.info("REST POST request: creating student {}", requestDTO);
        StudentCreateResponseDTO response = service.createStudent(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves a student by their ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<StudentDetailDTO> get(@PathVariable Long id) {
        log.info("REST GET request: get student by ID {}", id);
        return ResponseEntity.ok(service.getStudentById(id));
    }

    /**
     * Retrieves a paginated list of all students.
     */
    @GetMapping
    @Operation(summary = "List all students")
    public ResponseEntity<PaginationDTO<StudentDetailDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) int pageSize
    ) {
        log.info("REST GET request: list all students (page={}, size={})", pageNumber, pageSize);
        return ResponseEntity.ok(service.getAllStudents(pageNumber, pageSize));
    }

    /**
     * Updates a student's information by ID.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update student info")
    public ResponseEntity<StudentUpdateResponseDTO> update(
            @PathVariable Long id, @Valid @RequestBody StudentUpdateRequestDTO requestDTO
    ) {
        log.info("REST PUT request: update student ID {} with {}", id, requestDTO);
        return ResponseEntity.ok(service.updateStudent(id, requestDTO));
    }

    /**
     * Deletes a student by ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST DELETE request: delete student ID {}", id);
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
