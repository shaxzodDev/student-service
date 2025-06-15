package com.assessment.student_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentBaseDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @Size(max = 2, message = "Grade should be max 2 characters")
    private String grade;
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @NotBlank(message = "School name is required")
    private String schoolName;
}
