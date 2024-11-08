package com.mindfire.parkinglot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfire.parkinglot.model.ParkingSlot;
import com.mindfire.parkinglot.service.ParkingLotService;
import com.mindfire.parkinglot.util.JwtUtil;

@RestController
@RequestMapping("/api/v1")
public class ParkingLotController {

	private final ParkingLotService parkingLotService;

	public ParkingLotController(ParkingLotService parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

	@PostMapping("/park")
	public ParkingSlot parkCar(@RequestHeader("Authorization") String token, @RequestParam String licensePlate) {
		token = cleanToken(token);
		return parkingLotService.parkCar(licensePlate).orElseThrow(() -> new RuntimeException("Parking lot is full"));

	}

	@GetMapping("/slot/{id}")
	public ParkingSlot getSlot(@PathVariable int id) {
		return parkingLotService.getSlotById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
	}

	@PostMapping("/unpark")
	public ParkingSlot unparkCar(@RequestHeader("Authorization") String token, @RequestParam String licensePlate) {
		token = cleanToken(token);
		return parkingLotService.unparkCar(licensePlate).orElseThrow(() -> new RuntimeException("Car not found"));
	}
	
	private String cleanToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7).trim(); 
        }
        return token != null ? token.trim() : null;
    }


}
