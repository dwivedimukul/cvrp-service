package com.stackroute.cvrp.domain;

public class Slots {

	private String[] slotAvailability;
	private String[] slotCost;

	public Slots(String[] slotAvailability, String[] slotCost) {

		this.slotAvailability = slotAvailability;
		this.slotCost = slotCost;
	}

	public String[] getSlotAvailability() {
		return slotAvailability;
	}

	public void setSlotAvailability(String[] slotAvailability) {
		this.slotAvailability = slotAvailability;
	}

	public String[] getSlotCost() {
		return slotCost;
	}

	public void setSlotCost(String[] slotCost) {
		this.slotCost = slotCost;
	}
	

}
