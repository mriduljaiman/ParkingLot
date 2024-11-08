package com.mindfire.parkinglot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfire.parkinglot.model.ApiResponce;
import com.mindfire.parkinglot.model.ParkingSlot;
import com.mindfire.parkinglot.service.ParkingLotService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Controller responsible for in-memory Car parking related functions")
public class ParkingLotController {

	private final ParkingLotService parkingLotService;

	public ParkingLotController(ParkingLotService parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

	/**
	 * Park a car
	 * 
	 * @param token (bearer token)
	 * @param licensePlate number car to park
	 * @return parking slot info
	 */
	@ApiOperation(value = "Park a car in a slot", notes = "Parks a car in the first available slot.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully parked the car", 
                         response = ParkingSlot.class),
            @ApiResponse(code = 400, message = "Parking lot is full")
    })
	@PostMapping("/park")
	public ResponseEntity<ApiResponce<ParkingSlot>> parkCar(@RequestParam String licensePlate) {
        ParkingSlot slot = parkingLotService.parkCar(licensePlate)
                .orElseThrow(() -> new RuntimeException("Parking lot is full"));
        ApiResponce<ParkingSlot> response = new ApiResponce<>(true, "Car parked successfully", slot);
        return ResponseEntity.ok(response);
    }

	/**
     * Get the parking slot details by ID.
     * 
     * @param id 
     * @return details of the requested parking slot
     */
    @ApiOperation(value = "Get parking slot by ID", notes = "Fetches the details of a parking slot using the slot ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the slot details", 
                         response = ParkingSlot.class),
            @ApiResponse(code = 404, message = "Slot not found for the given ID")
    })
	  @GetMapping("/slot/{id}")
      public ResponseEntity<ApiResponce<ParkingSlot>> getSlot(@PathVariable int id) {
        ParkingSlot slot = parkingLotService.getSlotById(id);
        ApiResponce<ParkingSlot> response = new ApiResponce<>(true, "Slot retrieved successfully", slot);
        return ResponseEntity.ok(response);
    }

    /**
     * Unpark a car.
     * 
     * @param token (Bearer token)
     * @param licensePlate number to unpark
     * @return parking slot info
     */
    @ApiOperation(value = "Unpark a car", notes = "Unparks a car from a slot based on the license plate.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully unparked the car", 
                         response = ParkingSlot.class),
            @ApiResponse(code = 400, message = "Car not found")
    })
	@PostMapping("/unpark")
    public ResponseEntity<ApiResponce<ParkingSlot>> unparkCar(@RequestParam String licensePlate) {
        ParkingSlot slot = parkingLotService.unparkCar(licensePlate)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        ApiResponce<ParkingSlot> response = new ApiResponce<>(true, "Car unparked successfully", slot);
        return ResponseEntity.ok(response);
    }
}
