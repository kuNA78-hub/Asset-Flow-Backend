package com.example.demo.proj.dto;

import com.example.demo.proj.model.enums.AssetType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetRequestDto {
    @NotEmpty(message = "Asset name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "Asset type cannot be null")
    private AssetType type;
    @NotEmpty(message = "Asset location cannot be empty")
    private String location;
    private String imageUrl;
}