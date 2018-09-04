//package com.stackroute.cvrp.service;
//
//import java.util.ArrayList;
//
//import com.stackroute.cvrp.domain.Order;
//import com.stackroute.cvrp.domain.Vehicle;
//
//public class Solution {
//	int NoOfVehicles;
//	int NoOfCustomers;
//	Vehicle[] Vehicles;
//	double Cost;
//
//	// Tabu Variables
//	public Vehicle[] VehiclesForBestSolution;
//	double BestSolutionCost;
//
//	public ArrayList<Double> PastSolutions;
//
//	Solution(int CustNum, int VechNum, int VechCap) {
//		this.NoOfVehicles = VechNum;
//		this.NoOfCustomers = CustNum;
//		this.Cost = 0;
//		Vehicles = new Vehicle[NoOfVehicles];
//		VehiclesForBestSolution = new Vehicle[NoOfVehicles];
//		PastSolutions = new ArrayList<>();
//
//		for (int i = 0; i < NoOfVehicles; i++) {
//			Vehicles[i] = new Vehicle(i + 1, VechCap);
//			VehiclesForBestSolution[i] = new Vehicle(i + 1, VechCap);
//		}
//	}
//
//	public boolean UnassignedCustomerExists(Order[] Nodes) {
//		for (int i = 1; i < Nodes.length; i++) {
//			if (!Nodes[i].IsRouted)
//				return true;
//		}
//		return false;
//	}
//
//
//}
