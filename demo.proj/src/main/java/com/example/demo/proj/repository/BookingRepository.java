package com.example.demo.proj.repository;

import com.example.demo.proj.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query to find any booking for a specific asset that overlaps with the given time range.
    @Query("SELECT b FROM Booking b WHERE b.asset.id = :assetId AND b.startTime < :endTime AND b.endTime > :startTime")
    List<Booking> findOverlappingBookings(@Param("assetId") Long assetId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    // NEW METHOD: Finds all bookings associated with a specific user's ID.
    List<Booking> findByUserId(Long userId);

}