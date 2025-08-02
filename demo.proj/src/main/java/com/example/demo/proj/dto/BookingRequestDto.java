package com.example.demo.proj.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDto {
    @NotNull(message = "assetId cannot be null")
    private Long assetId;
    @NotNull(message = "userId cannot be null")
    private Long userId;
    @NotNull(message = "startTime cannot be null")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;
    @NotNull(message = "endTime cannot be null")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;
    private String purpose;
}
