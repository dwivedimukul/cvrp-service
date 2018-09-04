package com.stackroute.cvrp.domain;

public class Slots {

	private boolean[] slotAvailability;
	private String[] slotCost;

	public Slots(boolean[] slotAvailability, String[] slotCost) {

		this.slotAvailability = slotAvailability;
		this.slotCost = slotCost;
	}

	public boolean[] getSlotAvailability() {
		return slotAvailability;
	}

	public void setSlotAvailability(boolean[] slotAvailability) {
		this.slotAvailability = slotAvailability;
	}

	public String[] getSlotCost() {
		return slotCost;
	}

	public void setSlotCost(String[] slotCost) {
		this.slotCost = slotCost;
	}
	

}
