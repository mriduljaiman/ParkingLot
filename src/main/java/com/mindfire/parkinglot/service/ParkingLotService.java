package com.mindfire.parkinglot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {
	
	@Value("${parking.lot.size}")
	private int lotSize;
	
	private final Map<Integer, String> slots = new HashMap<>();

	// park
	public String parkCar(String licensePlate) {
		for(int i=1; i <= lotSize; i++) {
			if(!slots.containsKey(i)) {
				slots.put(i, licensePlate);
				return "Car parket in slot" + i;
			}
		}
		throw new RuntimeException("Parking lot is full");
	}

}
