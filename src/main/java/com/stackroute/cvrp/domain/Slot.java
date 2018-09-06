package com.stackroute.cvrp.domain;

import java.util.Arrays;

public class Slot {
	public String slotId;
	public String slotDuration;
	public String slotStartTime;
	public String slotEndTIme;
	public String slotNoOfVehicle;
	public boolean slotAvailability;
	public Vehicle[] slotVehicle;
	public String getSlotId() {
		return slotId;
	}
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}
	public String getSlotDuration() {
		return slotDuration;
	}
	public void setSlotDuration(String slotDuration) {
		this.slotDuration = slotDuration;
	}
	public String getSlotStartTime() {
		return slotStartTime;
	}
	public void setSlotStartTime(String slotStartTime) {
		this.slotStartTime = slotStartTime;
	}
	public String getSlotEndTIme() {
		return slotEndTIme;
	}
	public void setSlotEndTIme(String slotEndTIme) {
		this.slotEndTIme = slotEndTIme;
	}
	public String getSlotNoOfVehicle() {
		return slotNoOfVehicle;
	}
	public void setSlotNoOfVehicle(String slotNoOfVehicle) {
		this.slotNoOfVehicle = slotNoOfVehicle;
	}
	public boolean isSlotAvailability() {
		return slotAvailability;
	}
	@Override
	public String toString() {
		return "Slot [slotId=" + slotId + ", slotDuration=" + slotDuration + ", slotStartTime=" + slotStartTime
				+ ", slotEndTIme=" + slotEndTIme + ", slotNoOfVehicle=" + slotNoOfVehicle + ", slotAvailability="
				+ slotAvailability + ", slotVehicle=" + Arrays.toString(slotVehicle) + "]";
	}
	public Slot() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Slot(String slotId, String slotDuration, String slotStartTime, String slotEndTIme, String slotNoOfVehicle,
			boolean slotAvailability, Vehicle[] slotVehicle) {
		super();
		this.slotId = slotId;
		this.slotDuration = slotDuration;
		this.slotStartTime = slotStartTime;
		this.slotEndTIme = slotEndTIme;
		this.slotNoOfVehicle = slotNoOfVehicle;
		this.slotAvailability = slotAvailability;
		this.slotVehicle = slotVehicle;
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
