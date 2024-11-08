package com.mindfire.parkinglot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindfire.parkinglot.model.ParkingSlot;

public class ParkingLotServiceTest {

	 @Test
	    void testParkCar() {
	        ParkingLotService parkingLotService = new ParkingLotService(10);
	        
	        ParkingSlot slot = parkingLotService.parkCar("ABC123").orElseThrow(() -> new RuntimeException("Parking lot is full"));
	        
	        assertNotNull(slot);
	        assertTrue(slot.isOccupied());
	        assertEquals("ABC123", slot.getLicensePlate());
	    }

	    @Test
	    void testUnparkCar() {
	        ParkingLotService parkingLotService = new ParkingLotService(10);
	        
	        ParkingSlot slot = parkingLotService.parkCar("ABC123").orElseThrow(() -> new RuntimeException("Parking lot is full"));
	        ParkingSlot unparkedSlot = parkingLotService.unparkCar("ABC123").orElseThrow(() -> new RuntimeException("Car not found"));
	        
	        assertNotNull(unparkedSlot);
	        assertFalse(unparkedSlot.isOccupied());
	    }
}
