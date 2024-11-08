package com.mindfire.parkinglot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mindfire.parkinglot.exception.SlotNotFoundException;
import com.mindfire.parkinglot.model.ParkingSlot;

@Service
public class ParkingLotService {
	
	private final List<ParkingSlot> parkingSlots;

	public ParkingLotService(@Value("${parking.lot.size}") int lotSize) {
		this.parkingSlots = new ArrayList<>();
		
		for(int i =1; i<= lotSize; i++) {
			parkingSlots.add(new ParkingSlot(i));
		}
	}
	
	public Optional<ParkingSlot> parkCar(String licensePlate){
		return parkingSlots.stream().filter(slot -> !slot.isOccupied())
				.findFirst().map(slot ->{
					slot.parkCar(licensePlate);
					return slot;
				});
	}
	
	public ParkingSlot getSlotById(int slotId) {
        return parkingSlots.stream()
                .filter(slot -> slot.getSlotId() == slotId)
                .findFirst()
                .orElseThrow(() -> new SlotNotFoundException("No slot booked for the given ID: " + slotId));
    }

    public Optional<ParkingSlot> unparkCar(String licensePlate) {
        return parkingSlots.stream()
                .filter(slot -> slot.isOccupied() && slot.getLicensePlate().equals(licensePlate))
                .findFirst()
                .map(slot -> {
                    slot.unparkCar();
                    return slot;
                });
    }

    public List<ParkingSlot> getAllSlots() {
        return parkingSlots;
    }
	
	
}
