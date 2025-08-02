package com.example.demo.proj.dto;

import com.example.demo.proj.model.enums.Role;
import lombok.Data;
import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private List<BookingResponseDto> bookings;
}
