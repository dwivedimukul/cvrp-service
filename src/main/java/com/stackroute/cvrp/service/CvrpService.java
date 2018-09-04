package com.stackroute.cvrp.service;

import com.stackroute.cvrp.domain.Location;
import com.stackroute.cvrp.domain.Logistics;
import com.stackroute.cvrp.exceptions.IllegalLocationMatrixException;

public interface CvrpService {

	public Logistics getJson();
	public Double[][] getDistanceMatrix(Location[] order) throws IllegalLocationMatrixException;
	
	
}
