package com.stackroute.cvrp.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	Double distance=0.0;

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

	// public List<Slot> AddOrderToSlot(Order orderObj) {
	// List<Slot> list = new ArrayList<>();
	// List<Order> orderList=new ArrayList<>();
	// Slot[] slots;
	// Vehicle[] vehicles;
	// orderObj=this.getNewOrder();
	// String newOrderCapacity;
	// newOrderCapacity=this.getNewOrderVolume();
	// slots=this.getSlots();
	// for(int i=0;i<slots.length;i++) {
	// vehicles=slots[i].getSlotVehicle();
	// for(int j=0;j<vehicles.length;j++) {
	// totalSlotCapacity += Float.parseFloat(vehicles[j].getVehicleCapacity());
	// filledSlotCapacity +=
	// Float.parseFloat(vehicles[j].getVehicleLoadedCapacity());
	// }
	// newFilledCapacity=filledSlotCapacity+Float.parseFloat(newOrderCapacity);
	// if(newFilledCapacity<=totalSlotCapacity) {
	//// list.add()
	// orderList.add(orderObj);
	//
	// }
	// }

	// for (int p = 0; p < dateLogistics.getSlots().length; p++) {
	//
	// slots = dateLogistics.getSlots();
	// vehicles = slots[p].getSlotVehicle();
	// for (int j = 0; j < vehicles.length; j++) {
	// totalSlotCapacity += Float.parseFloat(vehicles[j].getVehicleCapacity());
	// filledSlotCapacity +=
	// Float.parseFloat(vehicles[j].getVehicleLoadedCapacity());
	//
	// }
	// newCapacity = filledSlotCapacity +
	// Float.parseFloat(orderObj.getOrderVolume());
	// if (newCapacity <= totalSlotCapacity) {
	// list.add(slots[p]);
	// }
	// }
	// return list;

	// }
	//
	// public Slot[] getVehicleRoute(List<Slot> list,Order orderObj) {
	// Vehicle[] vehicle;
	// Order[] order;
	// Location location;
	// double[][] locationMatrix;
	// for(int i=0;i<list.size();i++) {
	// vehicle=list.get(i).slotVehicle;
	// for(int j=0;j<vehicle.length;j++) {
	// order=vehicle[i].getVehicleRoute();
	// for(int k=0;k<order.length;k++) {
	// location=order[k].getOrderLocation();
	//
	// }
	//
	// }
	// }
	//
	// }

	@Override
	public List<Location> getAllLocationsBySlot(String slotId) {
//		Route jsonRouteData = this.getJson();
//		DateLogistics jsonDateLogisticsData = jsonRouteData.getDataLogistics();
		Slot[] slots = this.getSlots();
		Vehicle[] vehicles;
		List<Order> orders;
		Location location;
		List<Location> locations = new ArrayList<>();
		Location newOrderLocation = this.getNewOrderLocation();

		for (int i = 0; i < slots.length; i++) {
			vehicles = slots[i].getSlotVehicle();
			for (int j = 0; j < vehicles.length; j++) {
				orders = Arrays.asList(vehicles[j].getVehicleRoute());
				for (int k = 0; k < orders.size(); k++) {
					location = orders.get(k).getOrderLocation();
					locations.add(location);
				}
			}

		}
		locations.add(newOrderLocation);
		return locations;
	}
	
	public List<Order> getAllOrders(String slotId){
//		Route jsonRouteData = this.getJson();
//		DateLogistics jsonDateLogisticsData = jsonRouteData.getDataLogistics();
		Slot[] slots = this.getSlots();
		Vehicle[] vehicles;
		List<Order> orders=new ArrayList<>();
		String id;
	
		for (int i = 0; i < slots.length; i++) {
			id=slots[i].getSlotId();
			if(slotId==id) {
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
					System.out.println("\n");

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
		Slot[] slots=this.getSlots();
		String numberOfVehicles;
		int numOfVehicles=0;
		for(int i=0;i<slots.length;i++) {
			if(slotId==slots[i].getSlotId()) {
				numberOfVehicles=slots[i].getSlotNoOfVehicle();
				numOfVehicles=Integer.parseInt(numberOfVehicles);
			}
		}
		return numOfVehicles;
	}

//	public List<Order> getVehicleRoute()
	public boolean UnassignedOrderExists(Order[] orders) {
		for (int i = 1; i < orders.length; i++) {
			if (!orders[i].isRouted())
				return true;
		}
		return false;
	}
//	public void AddOrder(Order order)// Add Customer to Vehicle Route
//	{
//		Slot[] slots=this.getSlots();
//		String numberOfVehicles;
//		int numOfVehicles=0;
//		for(int i=0;i<slots.length;i++) {
//				numberOfVehicles=slots[i].getSlotNoOfVehicle();
//				numOfVehicles=Integer.parseInt(numberOfVehicles);
//				for(int j=0;j<numOfVehicles;j++) {
//					vehicleRoute.add(order);
//					vehiclesArray[i].setVehicleRoute(vehicleRoute);	
//			}
//		}
////		Route.add(Customer);
////		this.load += Customer.demand;
////		this.CurLoc = Customer.NodeId;
//	}

	public Double greedySolution(Order[] orders, double[][] distanceMatrix) {

		double candCost, endCost;
		int vehicleIndex = 0;
		String slotId=null;

		while (UnassignedOrderExists(orders)) {

			int orderIndex = 0;
			Order orderObj = null;
			double minCost = (float) Double.MAX_VALUE;
			List<Order> ordersList=new ArrayList<>();
			for(int i=0;i<this.getSlots().length;i++) {
				slotId=this.getSlots()[i].getSlotId();
			}
			ordersList=this.getAllOrders(slotId);
			
			if((vehiclesArray[vehicleIndex].getVehicleRoute().length)==0) {
				vehiclesArray[vehicleIndex].addOrder(orders[0]);
			}
			for (int i = 1; i <= ordersList.size(); i++) {
				if (orders[i].isRouted() == false) {
					if (this.checkIfFits(orders[i].getOrderVolume())) {
						candCost = distanceMatrix[Integer.parseInt((vehiclesArray[vehicleIndex].getVehicleCurrentLocation()))][i];
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
					if (Integer.parseInt(vehiclesArray[vehicleIndex].getVehicleCurrentLocation()) != 0) {// End this route
						endCost = distanceMatrix[Integer.parseInt(vehiclesArray[vehicleIndex].getVehicleCurrentLocation())][0];
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

		return this.distance;
	}
	

}