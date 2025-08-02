package com.example.demo.proj.model;

import com.example.demo.proj.model.enums.AssetStatus;
import com.example.demo.proj.model.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "assets")
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AssetType type;
    private String location;
    @Enumerated(EnumType.STRING)
    private AssetStatus status;
    private String imageUrl;
    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}