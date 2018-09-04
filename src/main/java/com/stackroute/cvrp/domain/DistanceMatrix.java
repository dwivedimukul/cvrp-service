package com.stackroute.cvrp.domain;

public class DistanceMatrix {
	private int destinationIndex;
	private int originIndex;
	private double travelDistance;
	
	public DistanceMatrix(int destinationIndex, int originIndex, double travelDistance) {
		this.destinationIndex = destinationIndex;
		this.originIndex = originIndex;
		this.travelDistance = travelDistance;
	}

	public int getDestinationIndex() {
		return destinationIndex;
	}

	public void setDestinationIndex(int destinationIndex) {
		this.destinationIndex = destinationIndex;
	}

	public int getOriginIndex() {
		return originIndex;
	}

	public void setOriginIndex(int originIndex) {
		this.originIndex = originIndex;
	}

	public double getTravelDistance() {
		return travelDistance;
	}

	public void setTravelDistance(double travelDistance) {
		this.travelDistance = travelDistance;
	}
	
	

}
