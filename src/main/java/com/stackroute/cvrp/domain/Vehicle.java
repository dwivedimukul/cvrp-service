package com.stackroute.cvrp.domain;

import java.util.ArrayList;

public class Vehicle {
	private String vehicleId;
	private String vehicleCapacity;
	private String vehicleLoadedCapacity;
	private String vehicleRouteDuration;
	private String vehicleRouteDurationLimit;
	private Order[] vehicleRoute;

	public Vehicle(String vehicleId, String vehicleCapacity, String vehicleLoadedCapacity, String vehicleRouteDuration,
			String vehicleRouteDurationLimit, Order[] vehicleRoute) {
		this.vehicleId = vehicleId;
		this.vehicleCapacity = vehicleCapacity;
		this.vehicleLoadedCapacity = vehicleLoadedCapacity;
		this.vehicleRouteDuration = vehicleRouteDuration;
		this.vehicleRouteDurationLimit = vehicleRouteDurationLimit;
		this.vehicleRoute = vehicleRoute;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleCapacity() {
		return vehicleCapacity;
	}

	public void setVehicleCapacity(String vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}

	public String getVehicleLoadedCapacity() {
		return vehicleLoadedCapacity;
	}

	public void setVehicleLoadedCapacity(String vehicleLoadedCapacity) {
		this.vehicleLoadedCapacity = vehicleLoadedCapacity;
	}

	public String getVehicleRouteDuration() {
		return vehicleRouteDuration;
	}

	public void setVehicleRouteDuration(String vehicleRouteDuration) {
		this.vehicleRouteDuration = vehicleRouteDuration;
	}

	public String getVehicleRouteDurationLimit() {
		return vehicleRouteDurationLimit;
	}

	public void setVehicleRouteDurationLimit(String vehicleRouteDurationLimit) {
		this.vehicleRouteDurationLimit = vehicleRouteDurationLimit;
	}

	public Order[] getVehicleRoute() {
		return vehicleRoute;
	}

	public void setVehicleRoute(Order[] vehicleRoute) {
		this.vehicleRoute = vehicleRoute;
	}

}
