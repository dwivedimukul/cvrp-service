package com.stackroute.cvrp.service;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stackroute.cvrp.domain.DateLogistics;
import com.stackroute.cvrp.domain.Location;
import com.stackroute.cvrp.domain.Order;
import com.stackroute.cvrp.domain.Route;
import com.stackroute.cvrp.domain.Slot;
import com.stackroute.cvrp.domain.Vehicle;
import com.stackroute.cvrp.exceptions.IllegalLocationMatrixException;
import com.stackroute.cvrp.repository.CvrpRepository;
import com.stackroute.cvrp.repository.OrderRepository;
import com.stackroute.cvrp.repository.SlotRepository;
import com.stackroute.cvrp.repository.VehicleRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Service
@Qualifier("CvrpServiceImpl")
public class CvrpServiceImpl implements CvrpService {
	RestTemplate restTemplate = new RestTemplate();
	private String url_route = "http://localhost:8090/api/v1/route";
	private CvrpRepository cvrpRepository;
	private SlotRepository slotRepo;
	private OrderRepository orderRepository;
	private VehicleRepository vehicleRepository;
	private DateLogistics dateLogistics;
	private Slot[] slots;
	private Vehicle[] vehicles;
	private Vehicle[] vehiclesArray;
	private float filledSlotCapacity = 0;
	private float totalSlotCapacity = 0;
	private float newFilledCapacity;
	private float vehicleFilledCapacity;
	private float vehicleTotalCapacity;
	private List<Order> vehicleRoute;
<<<<<<< HEAD
	Double distance = 0.0;
	private double BestSolutionCost;
	private ArrayList<Double> PastSolutions;
=======
	Double distance=0.0;
>>>>>>> 3bcf04055590f1cc731ae25f7431eab13f53a383

	@Autowired
	public CvrpServiceImpl(CvrpRepository cvrpRepository, SlotRepository slotRepo, OrderRepository orderRepository,
			VehicleRepository vehicleRepository) {
		this.cvrpRepository = cvrpRepository;
		this.slotRepo = slotRepo;
		this.orderRepository = orderRepository;
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public Route getJson() {
		Route list;
		list = restTemplate.getForObject(url_route, Route.class);
		return list;
	}

	public Order getNewOrder() {
		Order newOrder;
		newOrder = this.getJson().getNewOrder();
		return newOrder;
	}

	public Location getNewOrderLocation() {
		Location location;
		location = this.getNewOrder().getOrderLocation();
		return location;
	}

	public String getNewOrderVolume() {
		String newOrderVolume;
		newOrderVolume = this.getNewOrder().getOrderVolume();
		return newOrderVolume;
	}

	public DateLogistics getDateLogistics() {
		DateLogistics dateLogistics;
		dateLogistics = this.getJson().getDataLogistics();
		return dateLogistics;
	}

	public Slot[] getSlots() {
		Slot[] slots;
		slots = this.getDateLogistics().getSlots();
		return slots;
	}

	@Override
	public List<Location> getAllLocationsBySlot(String slotId) {
		Slot[] slots = this.getSlots();
		Vehicle[] vehicles;
		String id;
		List<Order> orders;
		Location location;
		List<Location> locations = new ArrayList<>();
		Location newOrderLocation = this.getNewOrderLocation();

		for (int i = 0; i < slots.length; i++) {
			id = slots[i].getSlotId();
			if (slotId == id) {
			vehicles = slots[i].getSlotVehicle();
			for (int j = 0; j < vehicles.length; j++) {
				orders = Arrays.asList(vehicles[j].getVehicleRoute());
				for (int k = 0; k < orders.size(); k++) {
					location = orders.get(k).getOrderLocation();
					locations.add(location);
				}
			}

		}
		}
		locations.add(newOrderLocation);
		return locations;
	}

	public List<Order> getAllOrders(String slotId) {
		Slot[] slots = this.getSlots();
		Vehicle[] vehicles;
		List<Order> orders = new ArrayList<>();
		String id;

		for (int i = 0; i < slots.length; i++) {
			id = slots[i].getSlotId();
			if (slotId == id) {
				vehicles = slots[i].getSlotVehicle();
				for (int j = 0; j < vehicles.length; j++) {
					orders = Arrays.asList(vehicles[j].getVehicleRoute());
				}

			}
			orders.add(this.getNewOrder());
		}
		return orders;
	}

	@Override
	public Double[][] getDistanceMatrix(String slotId) throws IllegalLocationMatrixException {
		String url1 = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?";
		String origins = "origins=";
		String destinations = "destinations=";
		String url2 = "travelMode=driving&key=AhT3nVgSlv14w5u2GLYkCrCJm1VWDkBeEGHpG4JFNb13vgktN7OIJEr-5KZZrZah";
		String inline = "";
		List<Location> locations;
		locations = getAllLocationsBySlot(slotId);
		Double[][] distanceMatrix = new Double[locations.size()][locations.size()];
		while (!(locations.isEmpty())) {
			for (int i = 0; i < locations.size(); i++) {
				for (int j = 0; j < 1; j++) {
					String str1 = locations.get(i).getOrderLatitude();
					String str2 = locations.get(i).getOrderLongitude();
					origins = origins + str1 + "," + str2 + ";";
					destinations = destinations + str1 + "," + str2 + ";";
				}

			}
			String url = url1 + origins + "&" + destinations + "&" + url2;

			try {
				URL url3 = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) url3.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				int responsecode = conn.getResponseCode();
				if (responsecode != 200)
					throw new IllegalLocationMatrixException("HttpResponseCode: " + responsecode);
				else {
					Scanner sc = new Scanner(url3.openStream());
					while (sc.hasNext()) {
						inline += sc.nextLine();
					}
					sc.close();
				}

				JSONParser parse = new JSONParser();
				JSONObject jobj = (JSONObject) parse.parse(inline);
				JSONArray jsonarr_1 = (JSONArray) jobj.get("resourceSets");
				JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(0);
				JSONArray jsonarr_2 = (JSONArray) jsonobj_1.get("resources");
				JSONObject jsonobj_3 = (JSONObject) jsonarr_2.get(0);
				JSONArray jsonarr_3 = (JSONArray) jsonobj_3.get("results");
				for (int j = 1; j < jsonarr_3.size(); j++) {
					JSONObject jsonobj_2 = (JSONObject) jsonarr_3.get(j);
					int str_data1 = ((Long) jsonobj_2.get("destinationIndex")).intValue();
					// System.out.println(str_data1);
					int str_data2 = ((Long) jsonobj_2.get("originIndex")).intValue();
					// System.out.println(str_data2);
					Long str_data3 = (Long) jsonobj_2.get("totalWalkDuration");
					// System.out.println(str_data3);
					try {
						Double str_data4 = (Double) jsonobj_2.get("travelDistance");
						// System.out.println(str_data4);
						Double str_data5 = (Double) jsonobj_2.get("travelDuration");
						// System.out.println(str_data5);
						if (str_data1 != str_data2) {
							distanceMatrix[str_data1][str_data2] = str_data4;
							distanceMatrix[str_data2][str_data1] = str_data4;
						} else
							distanceMatrix[str_data1][str_data1] = null;
					} catch (Exception e) {
						Long str_data4 = (Long) jsonobj_2.get("travelDistance");
						// System.out.println(str_data4);
						Long str_data5 = (Long) jsonobj_2.get("travelDuration");
						// System.out.println(str_data5);

					}
					// System.out.println("\n");

				}
				conn.disconnect();
			} catch (

			Exception e) {
				e.printStackTrace();
			}
		}
		return distanceMatrix;
	}

	public boolean checkIfFits(String demand) {
		boolean result = false;
		demand = this.getNewOrderVolume();
		slots = this.getSlots();
		for (int i = 0; i < slots.length; i++) {
			vehicles = slots[i].getSlotVehicle();
			for (int j = 0; j < vehicles.length; j++) {
				totalSlotCapacity += Float.parseFloat(vehicles[j].getVehicleCapacity());
				filledSlotCapacity += Float.parseFloat(vehicles[j].getVehicleLoadedCapacity());
				vehicleFilledCapacity = Float.parseFloat(vehicles[j].getVehicleLoadedCapacity());
				vehicleTotalCapacity = Float.parseFloat(vehicles[i].getVehicleCapacity());
			}
			newFilledCapacity = filledSlotCapacity + Float.parseFloat(demand);
			if (newFilledCapacity <= totalSlotCapacity) {
				if (vehicleFilledCapacity + Float.parseFloat(demand) <= vehicleTotalCapacity) {
					result = true;
				}
			}
		}
		return result;
	}

	public int getNoOfVehicles(String slotId) {
		Slot[] slots = this.getSlots();
		String numberOfVehicles;
		int numOfVehicles = 0;
		for (int i = 0; i < slots.length; i++) {
			if (slotId == slots[i].getSlotId()) {
				numberOfVehicles = slots[i].getSlotNoOfVehicle();
				numOfVehicles = Integer.parseInt(numberOfVehicles);
			}
		}
		return numOfVehicles;
	}

	// public List<Order> getVehicleRoute()
	public boolean UnassignedOrderExists(Order[] orders) {
		for (int i = 1; i < orders.length; i++) {
			if (!orders[i].isRouted())
				return true;
		}
		return false;
	}

	public Double greedySolution(Order[] orders, double[][] distanceMatrix) {

		double candCost, endCost;
		int vehicleIndex = 0;
		String slotId = null;

		while (UnassignedOrderExists(orders)) {

			int orderIndex = 0;
			Order orderObj = null;
			double minCost = (float) Double.MAX_VALUE;
			List<Order> ordersList = new ArrayList<>();
			for (int i = 0; i < this.getSlots().length; i++) {
				slotId = this.getSlots()[i].getSlotId();
			}
			ordersList = this.getAllOrders(slotId);

			if ((vehiclesArray[vehicleIndex].getVehicleRoute().length) == 0) {
				vehiclesArray[vehicleIndex].addOrder(orders[0]);
			}
			for (int i = 1; i <= ordersList.size(); i++) {
				if (orders[i].isRouted() == false) {
					if (this.checkIfFits(orders[i].getOrderVolume())) {
						candCost = distanceMatrix[Integer
								.parseInt((vehiclesArray[vehicleIndex].getVehicleCurrentLocation()))][i];
						if (minCost > candCost) {
							minCost = candCost;
							orderIndex = i;
							orderObj = orders[i];
						}
					}
				}
			}

			if (orderObj == null) {
				// Not a single Customer Fits
				if (vehicleIndex + 1 < vehiclesArray.length) // We have more vehicles to assign
				{
					if (Integer.parseInt(vehiclesArray[vehicleIndex].getVehicleCurrentLocation()) != 0) {// End this
																											// route
						endCost = distanceMatrix[Integer
								.parseInt(vehiclesArray[vehicleIndex].getVehicleCurrentLocation())][0];
						vehiclesArray[vehicleIndex].addOrder(orders[0]);
						this.distance += endCost;
					}
					vehicleIndex = vehicleIndex + 1; // Go to next Vehicle
				} else // We DO NOT have any more vehicle to assign. The problem is unsolved under
						// these parameters
				{
					System.out.println("\nThe rest customers do not fit in any Vehicle\n"
							+ "The problem cannot be resolved under these constrains");
					System.exit(0);
				}
			} else {
				vehiclesArray[vehicleIndex].addOrder(orderObj);// If a fitting Customer is Found
				orders[orderIndex].setRouted(true);
				this.distance += minCost;
			}
		}

		endCost = distanceMatrix[Integer.parseInt(vehiclesArray[vehicleIndex].getVehicleCurrentLocation())][0];
		vehiclesArray[vehicleIndex].addOrder(orders[0]);
		this.distance += endCost;
<<<<<<< HEAD

	}

	public void TabuSearch(int TABU_Horizon, double[][] distanceMatrix) {

		// We use 1-0 exchange move
		List<Order> RouteFrom = new ArrayList<>();
		List<Order> RouteTo = new ArrayList<>();

		String MovingNodeDemand = null;

		int VehIndexFrom, VehIndexTo;
		double BestNCost, NeigthboorCost;

		int SwapIndexA = -1, SwapIndexB = -1, SwapRouteFrom = -1, SwapRouteTo = -1;

		int MAX_ITERATIONS = 200;
		int iteration_number = 0;

		int DimensionCustomer = distanceMatrix[1].length;
		int TABU_Matrix[][] = new int[DimensionCustomer + 1][DimensionCustomer + 1];

		String slotId = null;
		BestSolutionCost = this.distance; // Initial Solution Cost
		// System.out.println("Best SOlution cost:"+BestSolutionCost);

		boolean Termination = false;

		while (!Termination) {
			iteration_number++;
			BestNCost = Double.MAX_VALUE;
			// System.out.println("Best cost"+BestNCost);
			for (int i = 0; i < this.getSlots().length; i++) {
				slotId = this.getSlots()[i].getSlotId();

				for (VehIndexFrom = 0; VehIndexFrom < this.getNoOfVehicles(slotId); VehIndexFrom++) {
					RouteFrom = Arrays.asList(this.vehicles[VehIndexFrom].getVehicleRoute());

					int RoutFromLength = RouteFrom.size();
					// System.out.println(RoutFromLength);
					for (int j = 1; j < RoutFromLength - 1; j++) { // Not possible to move depot!

						for (VehIndexTo = 0; VehIndexTo < this.vehicles.length; VehIndexTo++) {
							RouteTo = Arrays.asList(this.vehicles[VehIndexTo].getVehicleRoute());
							int RouteTolength = RouteTo.size();
							// System.out.println("hey"+RouteTolength);
							for (int k = 0; (k < RouteTolength - 1); k++) {// Not possible to move after last Depot!

								MovingNodeDemand = RouteFrom.get(i).getOrderVolume();

								if ((VehIndexFrom == VehIndexTo) || this.checkIfFits(MovingNodeDemand)) {
									// If we assign to a different route check capacity constrains
									// if in the new route is the same no need to check for capacity

									if (((VehIndexFrom == VehIndexTo) && ((j == i) || (j == i - 1))) == false) // Not a
																												// move
																												// that
																												// Changes
																												// solution
																												// cost
									{ // System.out.println("i "+i);
										// System.out.println("j "+j);
										double MinusCost1 = distanceMatrix[Integer
												.parseInt(RouteFrom.get(i - 1).getOrderId())][Integer
														.parseInt(RouteFrom.get(i).getOrderId())];
										// System.out.println("Minus Cost 1 " + i + " " + MinusCost1);
										double MinusCost2 = distanceMatrix[Integer
												.parseInt(RouteFrom.get(i).getOrderId())][Integer
														.parseInt(RouteFrom.get(i + 1).getOrderId())];
										// System.out.println("Minus Cost 2 " + i + " " + MinusCost2);
										double MinusCost3 = distanceMatrix[Integer
												.parseInt(RouteTo.get(j).getOrderId())][Integer
														.parseInt(RouteTo.get(j + 1).getOrderId())];
										// System.out.println("Minus Cost 3 " + i + " " + MinusCost3);

										double AddedCost1 = distanceMatrix[Integer
												.parseInt(RouteFrom.get(i - 1).getOrderId())][Integer
														.parseInt(RouteFrom.get(i + 1).getOrderId())];
										// System.out.println("Added Cost 1 " + i + " " + " " + j + AddedCost1);
										double AddedCost2 = distanceMatrix[Integer
												.parseInt(RouteTo.get(j).getOrderId())][Integer
														.parseInt(RouteFrom.get(i).getOrderId())];
										// System.out.println("Added Cost 2 " + i + " " + " " + j + AddedCost2);
										double AddedCost3 = distanceMatrix[Integer
												.parseInt(RouteFrom.get(i).getOrderId())][Integer
														.parseInt(RouteTo.get(j + 1).getOrderId())];
										System.out.println("Added Cost 3 " + i + " " + " " + j + AddedCost3);

										// Check if the move is a Tabu! - If it is Tabu break
										if ((TABU_Matrix[Integer.parseInt(RouteFrom.get(i - 1).getOrderId())][Integer
												.parseInt(RouteFrom.get(i + 1).getOrderId())] != 0)
												|| (TABU_Matrix[Integer.parseInt(RouteTo.get(j).getOrderId())][Integer
														.parseInt(RouteFrom.get(i).getOrderId())] != 0)
												|| (TABU_Matrix[Integer.parseInt(RouteFrom.get(i).getOrderId())][Integer
														.parseInt(RouteTo.get(j + 1).getOrderId())] != 0)) {
											break;
										}

										NeigthboorCost = AddedCost1 + AddedCost2 + AddedCost3 - MinusCost1 - MinusCost2
												- MinusCost3;
										// System.out.println(NeigthboorCost);

										if (NeigthboorCost < BestNCost) {
											BestNCost = NeigthboorCost;
											SwapIndexA = i;
											SwapIndexB = j;
											SwapRouteFrom = VehIndexFrom;
											SwapRouteTo = VehIndexTo;
										}
									}
								}
							}
						}
					}
				}
			}

			for (int o = 0; o < TABU_Matrix[0].length; o++) {
				for (int p = 0; p < TABU_Matrix[0].length; p++) {
					if (TABU_Matrix[o][p] > 0) {
						TABU_Matrix[o][p]--;
					}
				}
			}

			RouteFrom = Arrays.asList(this.vehicles[SwapRouteFrom].getVehicleRoute());
			RouteTo = Arrays.asList(this.vehicles[SwapRouteTo].getVehicleRoute());
			this.vehicles[SwapRouteFrom].setVehicleRoute(null);
			this.vehicles[SwapRouteTo].setVehicleRoute(null);

			Order SwapNode = RouteFrom.get(SwapIndexA);

			int NodeIDBefore = Integer.parseInt(RouteFrom.get(SwapIndexA - 1).getOrderId());
			int NodeIDAfter = Integer.parseInt(RouteFrom.get(SwapIndexA + 1).getOrderId());
			int NodeID_F = Integer.parseInt(RouteTo.get(SwapIndexB).getOrderId());
			int NodeID_G = Integer.parseInt(RouteTo.get(SwapIndexB + 1).getOrderId());

			Random TabuRan = new Random();
			int RendomDelay1 = TabuRan.nextInt(5);
			int RendomDelay2 = TabuRan.nextInt(5);
			int RendomDelay3 = TabuRan.nextInt(5);

			TABU_Matrix[NodeIDBefore][Integer.parseInt(SwapNode.getOrderId())] = TABU_Horizon + RendomDelay1;
			TABU_Matrix[Integer.parseInt(SwapNode.getOrderId())][NodeIDAfter] = TABU_Horizon + RendomDelay2;
			TABU_Matrix[NodeID_F][NodeID_G] = TABU_Horizon + RendomDelay3;

			RouteFrom.remove(SwapIndexA);

			if (SwapRouteFrom == SwapRouteTo) {
				if (SwapIndexA < SwapIndexB) {
					RouteTo.add(SwapIndexB, SwapNode);
				} else {
					RouteTo.add(SwapIndexB + 1, SwapNode);
				}
			} else {
				RouteTo.add(SwapIndexB + 1, SwapNode);
			}

			this.vehicles[SwapRouteFrom].setVehicleRoute(RouteFrom.toArray(new Order[RouteFrom.size()]));

			this.vehicles[SwapRouteFrom].setVehicleLoadedCapacity(
					Integer.toString(Integer.parseInt(this.vehicles[SwapRouteFrom].getVehicleLoadedCapacity())
							- Integer.parseInt(MovingNodeDemand)));
			this.vehicles[SwapRouteTo].setVehicleRoute(RouteTo.toArray(new Order[RouteTo.size()]));

			this.vehicles[SwapRouteTo].setVehicleLoadedCapacity(
					Integer.toString(Integer.parseInt(this.vehicles[SwapRouteTo].getVehicleLoadedCapacity())
							- Integer.parseInt(MovingNodeDemand)));

			PastSolutions.add(this.distance);

			this.distance += BestNCost;

			if (this.distance < BestSolutionCost) {
				saveBestSolution();
			}

			if (iteration_number == MAX_ITERATIONS) {
				Termination = true;
			}
		}

		this.vehicles = vehiclesArray;
		this.distance = BestSolutionCost;

		try {
			PrintWriter writer = new PrintWriter("PastSolutionsTabu.txt", "UTF-8");
			writer.println("Solutions" + "\t");
			for (int i = 0; i < PastSolutions.size(); i++) {
				writer.println(PastSolutions.get(i) + "\t");
			}
			writer.close();
		} catch (Exception e) {
		}
	}

	public void saveBestSolution() {
		BestSolutionCost = distance;
		String slotId;
		for (int i = 0; i < this.getSlots().length; i++) {
			slotId = this.getSlots()[i].getSlotId();
			for (int j = 0; j < this.getNoOfVehicles(slotId); j++) {
				Arrays.asList(vehicles[j].getVehicleRoute()).clear();
				if (!Arrays.asList(vehicles[j].getVehicleRoute()).isEmpty()) {
					int RoutSize = Arrays.asList(vehicles[j].getVehicleRoute()).size();
					for (int k = 0; k < RoutSize; k++) {
						Order orderObj = Arrays.asList(vehicles[j].getVehicleRoute()).get(k);
						Arrays.asList(vehiclesArray[j].getVehicleRoute()).add(orderObj);
					}
				}
			}
		}
=======

		return this.distance;
>>>>>>> 3bcf04055590f1cc731ae25f7431eab13f53a383
	}
	

}