package com.assessment.student_service.exception;

import com.assessment.student_service.controller.StudentController;
import com.assessment.student_service.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void whenResourceNotFound_thenReturns404() throws Exception {
        // Arrange
        Mockito.when(studentService.getStudentById(999L))
                .thenThrow(new ResourceNotFoundException("Student not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/students/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student not found"));
    }

    @Test
    void whenRuntimeException_thenReturns500() throws Exception {
        // Arrange
        Mockito.when(studentService.getStudentById(1L))
                .thenThrow(new RuntimeException("Unexpected DB error"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong, please try again later"));
    }
}
