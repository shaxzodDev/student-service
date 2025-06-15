package com.assessment.student_service.conroller;

import com.assessment.student_service.dto.StudentCreateRequestDTO;
import com.assessment.student_service.dto.StudentCreateResponseDTO;
import com.assessment.student_service.dto.StudentUpdateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private static Long createdStudentId;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Order(1)
    void testCreateStudent() throws Exception {
        StudentCreateRequestDTO student = new StudentCreateRequestDTO();
        student.setName("Shakhzod Khasanov");
        student.setGrade("5");
        student.setMobileNumber("9729435455");
        student.setSchoolName("International Academy");

        MvcResult result = mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        // Store ID for later tests
        String response = result.getResponse().getContentAsString();
        StudentCreateResponseDTO created = objectMapper.readValue(response, StudentCreateResponseDTO.class);
        createdStudentId = created.getId();
    }

    @Test
    @Order(2)
    void testGetStudentList() throws Exception {
        mockMvc.perform(get("/api/v1/students?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", not(empty())));
    }

    @Test
    @Order(3)
    void testGetStudentDetail() throws Exception {
        mockMvc.perform(get("/api/v1/students/" + createdStudentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdStudentId));
    }

    @Test
    @Order(4)
    void testUpdateStudent() throws Exception {
        StudentUpdateRequestDTO updatedStudent = new StudentUpdateRequestDTO();
        updatedStudent.setName("Shakhzod Updated Name");
        updatedStudent.setGrade("6");
        updatedStudent.setMobileNumber("1915931135");
        updatedStudent.setSchoolName("International University");

        mockMvc.perform(put("/api/v1/students/" + createdStudentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shakhzod Updated Name"));
    }

    @Test
    @Order(5)
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/v1/students/" + createdStudentId))
                .andExpect(status().isNoContent());
    }
}
