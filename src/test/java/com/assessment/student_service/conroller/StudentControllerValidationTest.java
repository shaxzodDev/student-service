package com.assessment.student_service.conroller;

import com.assessment.student_service.controller.StudentController;
import com.assessment.student_service.dto.StudentCreateRequestDTO;
import com.assessment.student_service.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 400 when creating student with invalid data")
    void shouldReturn400ForInvalidCreateRequest() throws Exception {
        StudentCreateRequestDTO request = new StudentCreateRequestDTO();
        request.setName(""); // invalid: blank
        request.setGrade("123"); // invalid: > 2 chars
        request.setMobileNumber("12345"); // invalid: not 10 digits
        request.setSchoolName(""); // invalid: blank

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(4)); // 4 validation errors
    }

    @Test
    @DisplayName("Should return 400 when updating student with invalid data")
    void shouldReturn400ForInvalidUpdateRequest() throws Exception {
        StudentCreateRequestDTO request = new StudentCreateRequestDTO();
        request.setName(null); // invalid
        request.setGrade("999"); // invalid
        request.setMobileNumber("abcdef"); // invalid
        request.setSchoolName(" "); // invalid

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(4));
    }
}
