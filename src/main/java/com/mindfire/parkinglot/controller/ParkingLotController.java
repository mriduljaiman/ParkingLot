package com.mindfire.parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfire.parkinglot.service.ParkingLotService;

@RestController
@RequestMapping("/api")
public class ParkingLotController {
	
	@Autowired
	private ParkingLotService parkingLotService;
	
	public ResponseEntity<String> parkCar(@RequestParam String licensePlate){
		
		try {
			String result = parkingLotService.parkCar(licensePlate);
			return ResponseEntity.ok(result);
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
		
	}

}
