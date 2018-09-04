package com.stackroute.cvrp.domain;

public class Order {

	private String orderId;
	private Location orderLocation;
	private String orderVolume;
	private Slots availableSlots;
	private String selectedSlots;

	public Order(String orderId, Location orderLocation, String orderVolume, Slots availableSlots,
			String selectedSlots) {

		this.orderId = orderId;
		this.orderLocation = orderLocation;
		this.orderVolume = orderVolume;
		this.availableSlots = availableSlots;
		this.selectedSlots = selectedSlots;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Location getOrderLocation() {
		return orderLocation;
	}

	public void setOrderLocation(Location orderLocation) {
		this.orderLocation = orderLocation;
	}

	public String getOrderVolume() {
		return orderVolume;
	}

	public void setOrderVolume(String orderVolume) {
		this.orderVolume = orderVolume;
	}

	public Slots getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(Slots availableSlots) {
		this.availableSlots = availableSlots;
	}

	public String getSelectedSlots() {
		return selectedSlots;
	}

	public void setSelectedSlots(String selectedSlots) {
		this.selectedSlots = selectedSlots;
	}
	

}
