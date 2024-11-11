package com.mindfire.parkinglot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfire.parkinglot.constant.Constants;
import com.mindfire.parkinglot.model.ApiResponce;
import com.mindfire.parkinglot.model.ParkingSlot;
import com.mindfire.parkinglot.service.ParkingLotService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

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
	 * @param token        (bearer token)
	 * @param licensePlate number car to park
	 * @return parking slot info
	 */
	@Operation(summary = "Park a car in a slot", description = "Parks a car in the first available slot.")
	@ApiResponses(value = {
			@ApiResponse(code = Constants.SUCCESS_CODE, message = Constants.PARKING_SUCCESS, response = ParkingSlot.class),
			@ApiResponse(code = Constants.BAD_REQUEST_CODE, message = Constants.PARKING_LOT_FULL) })
	@PostMapping("/park")
	public ResponseEntity<ApiResponce<ParkingSlot>> parkCar(
			@Parameter(description = "License plate number of the car to park", example = "RJ14JH0001") @RequestParam String licensePlate) {
		ParkingSlot slot = parkingLotService.parkCar(licensePlate)
				.orElseThrow(() -> new RuntimeException(Constants.PARKING_LOT_FULL));
		ApiResponce<ParkingSlot> response = new ApiResponce<>(true, Constants.PARKING_SUCCESS, slot);
		return ResponseEntity.ok(response);
	}

	/**
	 * Get the parking slot details by ID.
	 * 
	 * @param id
	 * @return details of the requested parking slot
	 */
	@Operation(summary = "Get parking slot by ID", description = "Fetches the details of a parking slot using the slot ID.")
	@ApiResponses(value = {
			@ApiResponse(code = Constants.SUCCESS_CODE, message = Constants.SLOT_RETRIEVED, response = ParkingSlot.class),
			@ApiResponse(code = Constants.NOT_FOUND_CODE, message = Constants.SLOT_NOT_FOUND) })
	@GetMapping("/slot/{id}")
	public ResponseEntity<ApiResponce<ParkingSlot>> getSlot(
			@Parameter(description = "ID of the parking slot to retrieve", example = "1") @PathVariable int id) {
		ParkingSlot slot = parkingLotService.getSlotById(id);
		ApiResponce<ParkingSlot> response = new ApiResponce<>(true, Constants.SLOT_RETRIEVED, slot);
		return ResponseEntity.ok(response);
	}

	/**
	 * Unpark a car.
	 * 
	 * @param token        (Bearer token)
	 * @param licensePlate number to unpark
	 * @return parking slot info
	 */
	@Operation(summary = "Unpark a car", description = "Unparks a car from a slot based on the license plate.")
	@ApiResponses(value = {
			@ApiResponse(code = Constants.SUCCESS_CODE, message = Constants.UNPARKING_SUCCESS, response = ParkingSlot.class),
			@ApiResponse(code = Constants.BAD_REQUEST_CODE, message = Constants.CAR_NOT_FOUND) })
	@PostMapping("/unpark")
	public ResponseEntity<ApiResponce<ParkingSlot>> unparkCar(
			@Parameter(description = "License plate number of the car to unpark", example = "RJ14AH0001") @RequestParam String licensePlate) {
		ParkingSlot slot = parkingLotService.unparkCar(licensePlate)
				.orElseThrow(() -> new RuntimeException(Constants.CAR_NOT_FOUND));
		ApiResponce<ParkingSlot> response = new ApiResponce<>(true, Constants.UNPARKING_SUCCESS, slot);
		return ResponseEntity.ok(response);
	}
}
