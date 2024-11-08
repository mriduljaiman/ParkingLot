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

    @Test
    void testParkCarWithValidToken() {
        String token = "validToken";
        String licensePlate = "XYZ123";
        ParkingSlot mockedSlot = mock(ParkingSlot.class);
        
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(parkingLotService.parkCar(licensePlate)).thenReturn(Optional.of(mockedSlot));

        ResponseEntity<ApiResponce<ParkingSlot>> responseEntity = parkingLotController.parkCar(licensePlate);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        
        ApiResponce<ParkingSlot> apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals("Car parked successfully", apiResponse.getMessage());
        assertEquals(mockedSlot, apiResponse.getData());

        verify(parkingLotService).parkCar(licensePlate);
    }

    @Test
    void testGetSlotById() {
        ParkingSlot mockedSlot = new ParkingSlot(1);
        
        when(parkingLotService.getSlotById(1)).thenReturn(mockedSlot);

        ResponseEntity<ApiResponce<ParkingSlot>> responseEntity = parkingLotController.getSlot(1);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());  
        ApiResponce<ParkingSlot> apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals("Slot retrieved successfully", apiResponse.getMessage());
        assertEquals(mockedSlot, apiResponse.getData());
    }

    @Test
    void testUnparkCarWithValidToken() {
    	 String token = "validToken";
        String licensePlate = "XYZ123";
        ParkingSlot mockedSlot = mock(ParkingSlot.class);
        
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(parkingLotService.unparkCar(licensePlate)).thenReturn(Optional.of(mockedSlot));

        ResponseEntity<ApiResponce<ParkingSlot>> responseEntity = parkingLotController.unparkCar(licensePlate);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        ApiResponce<ParkingSlot> apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals("Car unparked successfully", apiResponse.getMessage());
        assertEquals(mockedSlot, apiResponse.getData());

        verify(parkingLotService).unparkCar(licensePlate);
    }

}
