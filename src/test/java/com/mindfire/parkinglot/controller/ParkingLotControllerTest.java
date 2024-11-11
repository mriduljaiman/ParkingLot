package com.mindfire.parkinglot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.mindfire.parkinglot.model.ApiResponce;
import com.mindfire.parkinglot.model.ParkingSlot;
import com.mindfire.parkinglot.service.ParkingLotService;
import com.mindfire.parkinglot.util.JwtUtil;

public class ParkingLotControllerTest {
	
	@InjectMocks
    private ParkingLotController parkingLotController;

    @Mock
    private ParkingLotService parkingLotService;

    @Mock
    private JwtUtil jwtUtil;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for parking a car with a valid token.
     * Verifies that the car is successfully parked when a valid license plate is provided.
     */
    @Test
    void testParkCarWithValidToken() {
        String token = "validToken";
        String licensePlate = "XYZ123";
        ParkingSlot mockedSlot = mock(ParkingSlot.class);
        
        // Mock the behavior of JwtUtil and ParkingLotService
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(parkingLotService.parkCar(licensePlate)).thenReturn(Optional.of(mockedSlot));

        // Call the controller method
        ResponseEntity<ApiResponce<ParkingSlot>> responseEntity = parkingLotController.parkCar(licensePlate);

     // Validate the response
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        
     // Extract the body of the response
        ApiResponce<ParkingSlot> apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals("Car parked successfully", apiResponse.getMessage());
        assertEquals(mockedSlot, apiResponse.getData());

     // Verify that the service method was called with the correct license plate
        verify(parkingLotService).parkCar(licensePlate);
    }

    /**
     * Test case for retrieving a parking slot by ID.
     * Verifies that the correct parking slot is returned when a valid slot ID is provided.
     */
    @Test
    void testGetSlotById() {
        ParkingSlot mockedSlot = new ParkingSlot(1); // Create a mocked parking slot with ID 1
        
     // Mock the behavior of ParkingLotService to return the mocked parking slot
        when(parkingLotService.getSlotById(1)).thenReturn(mockedSlot);
        
        // Call the controller method to get the parking slot
        ResponseEntity<ApiResponce<ParkingSlot>> responseEntity = parkingLotController.getSlot(1);

     // Validate the response
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());  

     // Extract and validate the body of the response
        ApiResponce<ParkingSlot> apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals("Slot retrieved successfully", apiResponse.getMessage());
        assertEquals(mockedSlot, apiResponse.getData());
    }

    /**
     * Test case for un-parking a car with a valid token.
     * Verifies that the car is successfully un-parked when the correct license plate is provided.
     */
    @Test
    void testUnparkCarWithValidToken() {
    	 String token = "validToken";
        String licensePlate = "XYZ123";
        ParkingSlot mockedSlot = mock(ParkingSlot.class); // Mocked ParkingSlot object
        
     // Mock the behavior of JwtUtil and ParkingLotService
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(parkingLotService.unparkCar(licensePlate)).thenReturn(Optional.of(mockedSlot));

     // Call the controller method
        ResponseEntity<ApiResponce<ParkingSlot>> responseEntity = parkingLotController.unparkCar(licensePlate);

     // Validate the response
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

     // Extract the body of the response
        ApiResponce<ParkingSlot> apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals("Car unparked successfully", apiResponse.getMessage());
        assertEquals(mockedSlot, apiResponse.getData());

     // Verify that the service method was called with the correct license plate
        verify(parkingLotService).unparkCar(licensePlate);
    }

}
