package com.stackroute.cvrp.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
	private float filledSlotCapacity = 0;
	private float totalSlotCapacity = 0;
	private float newFilledCapacity;

	@Autowired
	public CvrpServiceImpl(CvrpRepository cvrpRepository, SlotRepository slotRepo, OrderRepository orderRepository,
			VehicleRepository vehicleRepository) {
		this.cvrpRepository = cvrpRepository;
		this.slotRepo = slotRepo;
		this.orderRepository=orderRepository;
		this.vehicleRepository=vehicleRepository;
	}

	@Override
	public Route getJson() {
		Route list;
		list = restTemplate.getForObject(url_route, Route.class);
		return list;
	}
	

	public Order getNewOrder() {
		Order newOrder;
		newOrder=this.getJson().getNewOrder();
		return newOrder;
	}


	public Location getNewOrderLocation() {
		Location location;
		location=this.getNewOrder().getOrderLocation();
		return location;
	}
	
	public String getNewOrderVolume() {
		String newOrderVolume;
		newOrderVolume=this.getNewOrder().getOrderVolume();
		return newOrderVolume;
	}
	
	public DateLogistics getDateLogistics() {
		DateLogistics dateLogistics;
		dateLogistics=this.getJson().getDataLogistics();
		return dateLogistics;
	}
	
	public Slot[] getSlots() {
		Slot[] slots;
		slots=this.getDateLogistics().getSlots();
		return slots;
	}

//	public List<Slot> AddOrderToSlot(Order orderObj) {
//		List<Slot> list = new ArrayList<>();
//		List<Order> orderList=new ArrayList<>();
//		Slot[] slots;
//		Vehicle[] vehicles;
//		orderObj=this.getNewOrder();
//		String newOrderCapacity;
//		newOrderCapacity=this.getNewOrderVolume();
//		slots=this.getSlots();
//		for(int i=0;i<slots.length;i++) {
//			vehicles=slots[i].getSlotVehicle();
//			for(int j=0;j<vehicles.length;j++) {
//				totalSlotCapacity += Float.parseFloat(vehicles[j].getVehicleCapacity());
//				filledSlotCapacity += Float.parseFloat(vehicles[j].getVehicleLoadedCapacity());
//			}
//			newFilledCapacity=filledSlotCapacity+Float.parseFloat(newOrderCapacity);
//			if(newFilledCapacity<=totalSlotCapacity) {
////				list.add()
//				orderList.add(orderObj);
//				
//			}
//		}
		
		
	
//		for (int p = 0; p < dateLogistics.getSlots().length; p++) {
//
//			slots = dateLogistics.getSlots();
//			vehicles = slots[p].getSlotVehicle();
//			for (int j = 0; j < vehicles.length; j++) {
//				totalSlotCapacity += Float.parseFloat(vehicles[j].getVehicleCapacity());
//				filledSlotCapacity += Float.parseFloat(vehicles[j].getVehicleLoadedCapacity());
//
//			}
//			newCapacity = filledSlotCapacity + Float.parseFloat(orderObj.getOrderVolume());
//			if (newCapacity <= totalSlotCapacity) {
//				list.add(slots[p]);
//			}
//		}
//		return list;

//	}
//	
//	public Slot[] getVehicleRoute(List<Slot> list,Order orderObj) {
//		Vehicle[] vehicle;
//		Order[] order;
//		Location location;
//		double[][] locationMatrix;
//		for(int i=0;i<list.size();i++) {
//			vehicle=list.get(i).slotVehicle;
//			for(int j=0;j<vehicle.length;j++) {
//				order=vehicle[i].getVehicleRoute();
//				for(int k=0;k<order.length;k++) {
//					location=order[k].getOrderLocation();
//					
//}
//				
//			}
//		}
//		
//	}


	@Override
	public List<Location> getAllLocationsBySlot(String slotId) {
		Route jsonRouteData=this.getJson();
		DateLogistics jsonDateLogisticsData=jsonRouteData.getDataLogistics();
		Slot[] slots=jsonDateLogisticsData.getSlots();
		Vehicle[] vehicles;
		Order[] orders;
		Location location;
		List<Location> locations=new ArrayList<>();
		Location newOrderLocation=this.getNewOrderLocation();
		
		for(int i=0;i<slots.length;i++) {
			vehicles=slots[i].getSlotVehicle();
			for(int j=0;j<vehicles.length;j++) {
				orders=vehicles[j].getVehicleRoute();
				for(int k=0;k<orders.length;k++) {
					location=orders[i].getOrderLocation();
					locations.add(location);
				}
			}
			
		}
		locations.add(newOrderLocation);
		

//		Optional<Slot> slotObj=slotRepo.findById(slotId);
//		Order[] orders = null;
//		Location location;
//		List<Location> locations=new ArrayList<>();
//		Slot slot=slotObj.get();
//		Vehicle[] vehicles=slot.getSlotVehicle();
//		for(int i=0;i<vehicles.length;i++) {
//			orders=vehicles[i].getVehicleRoute();
//		}
//		for(int j=0;j<orders.length;j++) {
//			location=orders[j].getOrderLocation();
//			locations.add(location);
//		}
		return locations;
	}

	@Override
	public Double[][] getDistanceMatrix(String slotId) throws IllegalLocationMatrixException {
		String url1 = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?";
		String origins = "origins=";
		String destinations = "destinations=";
		String url2 = "travelMode=driving&key=AhT3nVgSlv14w5u2GLYkCrCJm1VWDkBeEGHpG4JFNb13vgktN7OIJEr-5KZZrZah";
		String inline = "";
		List<Location> locations;
		locations=getAllLocationsBySlot(slotId);
		Double[][] distanceMatrix = new Double[locations.size()][locations.size()];
		while(!(locations.isEmpty())){
		for (int i = 0; i < locations.size(); i++) {
			for (int j = 0; j < 1; j++) {
				String str1=locations.get(i).getOrderLatitude();
				String str2=locations.get(i).getOrderLongitude();
//				String str1 = location[i].getOrderLatitude();
//				String str2 = location[i].getOrderLongitude();
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
	// public

	
//	@Override
//	public List<Location> getLocationBySlot(int slotId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}