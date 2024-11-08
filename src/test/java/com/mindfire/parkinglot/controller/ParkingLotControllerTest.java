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

        ParkingSlot response = parkingLotController.parkCar("Bearer " + token, licensePlate);

        assertNotNull(response);
        verify(parkingLotService).parkCar(licensePlate);
    }

    @Test
    void testGetSlotById() {
        ParkingSlot mockedSlot = new ParkingSlot(1);
        
        when(parkingLotService.getSlotById(1)).thenReturn(mockedSlot);

        ParkingSlot response = parkingLotController.getSlot(1);

        assertNotNull(response);
        assertEquals(1, response.getSlotId());
    }

    @Test
    void testUnparkCarWithValidToken() {
        String token = "validToken";
        String licensePlate = "XYZ123";
        ParkingSlot mockedSlot = mock(ParkingSlot.class);
        
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(parkingLotService.unparkCar(licensePlate)).thenReturn(Optional.of(mockedSlot));

        ParkingSlot response = parkingLotController.unparkCar("Bearer " + token, licensePlate);

        assertNotNull(response);
        verify(parkingLotService).unparkCar(licensePlate);
    }

}
