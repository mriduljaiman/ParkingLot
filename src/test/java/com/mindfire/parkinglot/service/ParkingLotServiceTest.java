package com.mindfire.parkinglot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mindfire.parkinglot.constant.Constants;
import com.mindfire.parkinglot.exception.SlotNotFoundException;
import com.mindfire.parkinglot.model.ParkingSlot;

public class ParkingLotServiceTest {
	
	 @Mock
	    private List<ParkingSlot> parkingSlots;  // Mock the parkingSlots list

	    private ParkingLotService parkingLotService;  // Create an instance manually

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);  // Initialize mocks
	        parkingLotService = new ParkingLotService(10);  // Manually instantiate the service
	    }
	 /**
     * Test parking a car in an empty slot.
     */
	 @Test
	    void testParkCar() {
	        ParkingLotService parkingLotService = new ParkingLotService(10); // Initialize service with 10 slots
	        
	        ParkingSlot slot = parkingLotService.parkCar("ABC123").orElseThrow(() -> new RuntimeException("Parking lot is full"));
	        
	        // Validate the response
	        assertNotNull(slot);
	        assertTrue(slot.isOccupied());
	        assertEquals("ABC123", slot.getLicensePlate());
	    }

	 /**
	     * Test unparking a car from slot.
	     */
	    @Test
	    void testUnparkCar() {
	        ParkingLotService parkingLotService = new ParkingLotService(10);
	        
	        ParkingSlot slot = parkingLotService.parkCar("ABC123").orElseThrow(() -> new RuntimeException("Parking lot is full"));
	        ParkingSlot unparkedSlot = parkingLotService.unparkCar("ABC123").orElseThrow(() -> new RuntimeException("Car not found"));
	        
	        // Validate the response
	        assertNotNull(unparkedSlot);
	        assertFalse(unparkedSlot.isOccupied());
	    }
	    
	    @Test
	    public void testGetSlotById_SlotFound() {
	        // Arrange: create a parking slot and mock the behavior of the parkingSlots list
	        int slotId = 1;
	        ParkingSlot slot = new ParkingSlot(slotId);
	        slot.setLicensePlate("ABC123");
	        
	        when(parkingSlots.stream()).thenReturn(List.of(slot).stream());  // Mock the parkingSlots list to contain the slot
	        
	        // Act: call the method to retrieve the slot by ID
	        ParkingSlot result = parkingLotService.getSlotById(slotId);

	        // Assert: verify that the correct slot is returned
	        assertNotNull(result);
	        assertEquals(slotId, result.getSlotId());
	    }	    
	    @Test
	    public void testGetSlotById_SlotNotFound() {
	        // Arrange: mock the behavior when the slot is not found
	        int slotId = 999;  // Non-existent slot ID
	        
//	        when(parkingSlots.stream()).thenReturn(List.of().stream());  // Mock an empty list (no slot found)

	        // Act & Assert: verify that SlotNotFoundException is thrown
	        SlotNotFoundException exception = assertThrows(SlotNotFoundException.class, () -> {
	            parkingLotService.getSlotById(slotId);  // Call the method with a non-existent ID
	        });

	        // Assert: verify the exception message
	        assertEquals(Constants.SLOT_NOT_FOUND + slotId, exception.getMessage());
	    }

}
