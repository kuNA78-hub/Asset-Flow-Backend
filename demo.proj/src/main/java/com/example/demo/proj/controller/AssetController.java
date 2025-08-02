package com.example.demo.proj.controller;

import com.example.demo.proj.dto.AssetRequestDto;
import com.example.demo.proj.dto.AssetResponseDto;
import com.example.demo.proj.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Make sure this is imported
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    public List<AssetResponseDto> getAllAssets() {
        return assetService.getAllAssets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDto> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AssetResponseDto createAsset(@Valid @RequestBody AssetRequestDto assetDto) {
        return assetService.createAsset(assetDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssetResponseDto> updateAsset(@PathVariable Long id, @Valid @RequestBody AssetRequestDto assetDto) {
        return ResponseEntity.ok(assetService.updateAsset(id, assetDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
    }
}