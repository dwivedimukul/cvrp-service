package com.stackroute.cvrp.domain;

public class Slot {
	public int slotId;
	public int slotDuration;
	public int slotStartTime;
	public int slotEndTIme;
	public int slotNoOfVehicle;
	public boolean slotAvailability;
	public Vehicle[] slotVehicle;

	public Slot(int slotId, int slotDuration, int slotStartTime, int slotEndTIme, int slotNoOfVehicle,
			boolean slotAvailability, Vehicle[] slotVehicle) {

		this.slotId = slotId;
		this.slotDuration = slotDuration;
		this.slotStartTime = slotStartTime;
		this.slotEndTIme = slotEndTIme;
		this.slotNoOfVehicle = slotNoOfVehicle;
		this.slotAvailability = slotAvailability;
		this.slotVehicle = slotVehicle;
	}

	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	public int getSlotDuration() {
		return slotDuration;
	}

	public void setSlotDuration(int slotDuration) {
		this.slotDuration = slotDuration;
	}

	public int getSlotStartTime() {
		return slotStartTime;
	}

	public void setSlotStartTime(int slotStartTime) {
		this.slotStartTime = slotStartTime;
	}

	public int getSlotEndTIme() {
		return slotEndTIme;
	}

	public void setSlotEndTIme(int slotEndTIme) {
		this.slotEndTIme = slotEndTIme;
	}

	public int getSlotNoOfVehicle() {
		return slotNoOfVehicle;
	}

	public void setSlotNoOfVehicle(int slotNoOfVehicle) {
		this.slotNoOfVehicle = slotNoOfVehicle;
	}

	public boolean isSlotAvailability() {
		return slotAvailability;
	}

	public void setSlotAvailability(boolean slotAvailability) {
		this.slotAvailability = slotAvailability;
	}

	public Vehicle[] getSlotVehicle() {
		return slotVehicle;
	}

	public void setSlotVehicle(Vehicle[] slotVehicle) {
		this.slotVehicle = slotVehicle;
	}

}
