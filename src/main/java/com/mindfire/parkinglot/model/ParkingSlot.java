package com.mindfire.parkinglot.model;

public class ParkingSlot {
	
	private int slotId;
	private String licensePlate;
	private boolean isOccupied;
	
	public ParkingSlot(int slotId) {
		this.slotId = slotId;
		this.licensePlate = null;
		this.isOccupied = false;
	}
	
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public void parkCar(String licensePlate) {
		this.licensePlate = licensePlate;
		this.isOccupied = true;
	}
	
	public void unparkCar() {
        this.licensePlate = null;
        this.isOccupied = false;
    }
	

}
