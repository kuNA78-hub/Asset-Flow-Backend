package com.example.demo.proj.service;

import com.example.demo.proj.dto.BookingRequestDto;
import com.example.demo.proj.dto.BookingResponseDto;
import com.example.demo.proj.exception.BookingConflictException;
import com.example.demo.proj.exception.ResourceNotFoundException;
import com.example.demo.proj.model.Asset;
import com.example.demo.proj.model.Booking;
import com.example.demo.proj.model.User;
import com.example.demo.proj.model.enums.BookingStatus; // Import BookingStatus
import com.example.demo.proj.repository.AssetRepository;
import com.example.demo.proj.repository.BookingRepository;
import com.example.demo.proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private UserRepository userRepository;

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return convertToDto(booking);
    }

    public List<BookingResponseDto> getBookingsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingResponseDto createBooking(BookingRequestDto bookingDto) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                bookingDto.getAssetId(), bookingDto.getStartTime(), bookingDto.getEndTime());

        if (!overlappingBookings.isEmpty()) {
            throw new BookingConflictException("Asset is already booked for the requested time period.");
        }

        Asset asset = assetRepository.findById(bookingDto.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + bookingDto.getAssetId()));
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + bookingDto.getUserId()));

        Booking newBooking = new Booking();
        newBooking.setAsset(asset);
        newBooking.setUser(user);
        newBooking.setStartTime(bookingDto.getStartTime());
        newBooking.setEndTime(bookingDto.getEndTime());
        newBooking.setPurpose(bookingDto.getPurpose());
        newBooking.setStatus(BookingStatus.PENDING); // Set default status to PENDING

        Booking savedBooking = bookingRepository.save(newBooking);
        return convertToDto(savedBooking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    private BookingResponseDto convertToDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setPurpose(booking.getPurpose());
        dto.setAssetId(booking.getAsset().getId());
        dto.setAssetName(booking.getAsset().getName());
        dto.setUserId(booking.getUser().getId());
        dto.setUserName(booking.getUser().getName());
        dto.setStatus(booking.getStatus()); // Add status to the DTO conversion
        return dto;
    }

    // This method is now complete
    public BookingResponseDto updateBookingStatus(Long id, BookingStatus status) {
        // Find the booking or throw an error
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        // Set the new status
        booking.setStatus(status);

        // Save the updated booking and return its DTO
        Booking updatedBooking = bookingRepository.save(booking);
        return convertToDto(updatedBooking);
    }
}