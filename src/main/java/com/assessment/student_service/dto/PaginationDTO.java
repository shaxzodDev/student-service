package com.assessment.student_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO<T> {
    private List<T> content;       // List of records (paged data)
    private int pageNumber;        // Current page number (0-based or 1-based depending on usage)
    private int pageSize;          // Number of records per page
    private long totalElements;    // Total number of records available
    private int totalPages;        // Total pages available
}