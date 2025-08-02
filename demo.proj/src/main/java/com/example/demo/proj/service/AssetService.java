package com.example.demo.proj.service;

import com.example.demo.proj.dto.AssetRequestDto;
import com.example.demo.proj.dto.AssetResponseDto;
import com.example.demo.proj.dto.BookingResponseDto;
import com.example.demo.proj.exception.ResourceNotFoundException;
import com.example.demo.proj.model.Asset;
import com.example.demo.proj.model.Booking;
import com.example.demo.proj.model.enums.AssetStatus;
import com.example.demo.proj.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    // GET All
    public List<AssetResponseDto> getAllAssets() {
        return assetRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // GET by ID
    public AssetResponseDto getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return convertToDto(asset);
    }

    // POST
    public AssetResponseDto createAsset(AssetRequestDto assetDto) {
        Asset newAsset = new Asset();
        newAsset.setName(assetDto.getName());
        newAsset.setDescription(assetDto.getDescription());
        newAsset.setType(assetDto.getType());
        newAsset.setLocation(assetDto.getLocation());
        newAsset.setImageUrl(assetDto.getImageUrl());
        newAsset.setStatus(AssetStatus.AVAILABLE);
        Asset savedAsset = assetRepository.save(newAsset);
        return convertToDto(savedAsset);
    }

    // PUT
    public AssetResponseDto updateAsset(Long id, AssetRequestDto assetDto) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        asset.setName(assetDto.getName());
        asset.setDescription(assetDto.getDescription());
        asset.setType(assetDto.getType());
        asset.setLocation(assetDto.getLocation());
        asset.setImageUrl(assetDto.getImageUrl());
        Asset updatedAsset = assetRepository.save(asset);
        return convertToDto(updatedAsset);
    }

    // DELETE
    public void deleteAsset(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asset not found with id: " + id);
        }
        assetRepository.deleteById(id);
    }

    // Conversion helpers...
    private AssetResponseDto convertToDto(Asset asset) {
        AssetResponseDto dto = new AssetResponseDto();
        dto.setId(asset.getId());
        dto.setName(asset.getName());
        dto.setDescription(asset.getDescription());
        dto.setType(asset.getType());
        dto.setLocation(asset.getLocation());
        dto.setStatus(asset.getStatus());
        dto.setImageUrl(asset.getImageUrl());
        if (asset.getBookings() != null) {
            dto.setBookings(
                    asset.getBookings().stream()
                            .map(this::convertBookingToDto)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setBookings(Collections.emptyList());
        }
        return dto;
    }

    private BookingResponseDto convertBookingToDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setPurpose(booking.getPurpose());
        dto.setUserId(booking.getUser().getId());
        dto.setUserName(booking.getUser().getName());
        dto.setAssetId(booking.getAsset().getId());
        dto.setAssetName(booking.getAsset().getName());
        dto.setStatus(booking.getStatus()); // MODIFIED: Added booking status
        return dto;
    }
}
