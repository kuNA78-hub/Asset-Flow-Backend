package com.example.demo.proj.dto;

import com.example.demo.proj.model.enums.BookingStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDto {
    private Long id;
    private Long assetId;
    private String assetName;
    private Long userId;
    private String userName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private BookingStatus status; // ADD THIS FIELD
}