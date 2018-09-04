package com.stackroute.cvrp.service;

import com.stackroute.cvrp.domain.Location;
import com.stackroute.cvrp.domain.Logistics;

public interface CvrpService {

	public Logistics getJson();
	public double[][] getDistanceMatrix(Location[] order);
	
}
