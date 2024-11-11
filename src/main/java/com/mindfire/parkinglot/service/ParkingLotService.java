package com.mindfire.parkinglot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mindfire.parkinglot.constant.Constants;
import com.mindfire.parkinglot.exception.SlotNotFoundException;
import com.mindfire.parkinglot.model.ParkingSlot;

@Service
public class ParkingLotService {

	private final List<ParkingSlot> parkingSlots;
	private static final Logger logger = LoggerFactory.getLogger(ParkingLotService.class);

	public ParkingLotService(@Value("${parking.lot.size}") int lotSize) {
		this.parkingSlots = new ArrayList<>();

		// Populate the parkingSlots list with empty parking slots
		for (int i = 1; i <= lotSize; i++) {
			parkingSlots.add(new ParkingSlot(i));
		}
		logger.info("Parking lot initialized with {} slots.", lotSize);
	}

	public Optional<ParkingSlot> parkCar(String licensePlate) {
		logger.debug("Attempting to park car with license plate: {}", licensePlate);

		// Find the first available empty slot and park the car
		return parkingSlots.stream().filter(slot -> !slot.isOccupied()).findFirst().map(slot -> {
			slot.parkCar(licensePlate);
			logger.info("Car with license plate {} parked in slot {}", licensePlate, slot.getSlotId());
			return slot;
		});
	}

	public ParkingSlot getSlotById(int slotId) {
		logger.debug("Retrieving parking slot with ID: {}", slotId);

		return parkingSlots.stream().filter(slot -> slot.getSlotId() == slotId).findFirst().orElseThrow(() -> {
			logger.error(Constants.SLOT_NOT_FOUND + slotId);
			return new SlotNotFoundException(Constants.SLOT_NOT_FOUND + slotId);
		});
	}

	public Optional<ParkingSlot> unparkCar(String licensePlate) {
		logger.debug("Attempting to unpark car with license plate: {}", licensePlate);

		// Find the slot where the car with the given license plate is parked and unpark it
		return parkingSlots.stream().filter(slot -> slot.isOccupied() && slot.getLicensePlate().equals(licensePlate))
				.findFirst().map(slot -> {
					slot.unparkCar();
					logger.info("Car with license plate {} has been unparked from slot {}", licensePlate,
							slot.getSlotId());
					return slot;
				});
	}

}
