package com.assessment.student_service.controller;

import com.assessment.student_service.dto.*;
import com.assessment.student_service.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<StudentCreateResponseDTO> create(@RequestBody StudentCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(service.createStudent(requestDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<StudentDetailDTO> get(@PathVariable Long id) {
        log.info("REST GET Request get student by ID: {}", id);
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @GetMapping
    @Operation(summary = "List all students")
    public ResponseEntity<PaginationDTO<StudentDetailDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) int pageSize
    ) {
        log.info("REST GET Request to get all students");
        return ResponseEntity.ok(service.getAllStudents(pageNumber, pageSize));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student info")
    public ResponseEntity<StudentUpdateResponseDTO> update(
            @PathVariable Long id, @RequestBody StudentUpdateRequestDTO requestDTO
    ) {
        log.info("REST PUT Request to update student with id: {}", id);
        return ResponseEntity.ok(service.updateStudent(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST DELETE Request to delete student with id: {}", id);
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
