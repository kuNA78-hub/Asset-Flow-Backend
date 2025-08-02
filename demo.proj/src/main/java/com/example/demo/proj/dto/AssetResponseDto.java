package com.example.demo.proj.dto;

import com.example.demo.proj.model.enums.AssetStatus;
import com.example.demo.proj.model.enums.AssetType;
import lombok.Data;
import java.util.List;

@Data
public class AssetResponseDto {
    private Long id;
    private String name;
    private String description;
    private AssetType type;
    private String location;
    private AssetStatus status;
    private String imageUrl;
    // The list will contain DTOs, not the full Booking entities
    private List<BookingResponseDto> bookings;
}