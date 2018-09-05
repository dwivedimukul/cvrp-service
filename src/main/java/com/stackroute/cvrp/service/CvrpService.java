package com.stackroute.cvrp.service;

import com.stackroute.cvrp.domain.Location;

import java.util.List;

import com.stackroute.cvrp.domain.DateLogistics;
import com.stackroute.cvrp.exceptions.IllegalLocationMatrixException;

public interface CvrpService {

	public DateLogistics getJson();
	public List<Location> getLocationBySlot(int slotId);
	public Double[][] getDistanceMatrix(Location[] location) throws IllegalLocationMatrixException;
	
	
}
