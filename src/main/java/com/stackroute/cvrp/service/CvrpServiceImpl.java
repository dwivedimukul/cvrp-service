package com.stackroute.cvrp.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stackroute.cvrp.domain.Location;
import com.stackroute.cvrp.domain.Logistics;
import com.stackroute.cvrp.repository.CvrpRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Service
@Qualifier("CvrpServiceImpl")
public class CvrpServiceImpl implements CvrpService {
	RestTemplate restTemplate = new RestTemplate();
	String url = "http://localhost:8090/api/v1/logistics";
	// MappingJacksonHttpMessageConverter converter = new
	// MappingJacksonHttpMessageConverter();
	private CvrpRepository cvrpRepository;
	@Autowired
	public CvrpServiceImpl(CvrpRepository cvrpRepository) {
		this.cvrpRepository=cvrpRepository;
	}

	public Logistics getJson() {
		Logistics list;
		list = restTemplate.getForObject(url, Logistics.class);
		return list;
	}

	public double[][] getDistanceMatrix(Location[] location) {
		String url1 = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?";
		String origins = "origins=";
		String destinations = "destinations=";
		String url2 = "travelMode=driving&key=AhT3nVgSlv14w5u2GLYkCrCJm1VWDkBeEGHpG4JFNb13vgktN7OIJEr-5KZZrZah";
		String inline = "";
		double[][] distanceMatrix = new double[location.length][location.length];
		for (int i = 0; i < location.length; i++) {
			for (int j = 0; j < 1; j++) {
				String str1 = location[i].getOrderLatitude();
				String str2 = location[i].getOrderLongitude();
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
			// System.out.println("Response code is: " +responsecode);
			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner sc = new Scanner(url3.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				System.out.println("\nJSON Response in String format");
				System.out.println(inline);
				sc.close();
			}
			@SuppressWarnings("deprecation")
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
				System.out.println(str_data1);
				int str_data2 = ((Long) jsonobj_2.get("originIndex")).intValue();
				System.out.println(str_data2);
				Long str_data3 = (Long) jsonobj_2.get("totalWalkDuration");
				System.out.println(str_data3);
				try {
					Double str_data4 = (Double) jsonobj_2.get("travelDistance");
					System.out.println(str_data4);
					Double str_data5 = (Double) jsonobj_2.get("travelDuration");
					System.out.println(str_data5);
					if (str_data1 != str_data2) {
						distanceMatrix[str_data1][str_data2] = str_data4;
						distanceMatrix[str_data2][str_data1]=str_data4;}
					else
						distanceMatrix[str_data1][str_data1] = 0;
				} catch (Exception e) {
					Long str_data4 = (Long) jsonobj_2.get("travelDistance");
					System.out.println(str_data4);
					Long str_data5 = (Long) jsonobj_2.get("travelDuration");
					System.out.println(str_data5);

				}
				System.out.println("\n");

			}
			conn.disconnect();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return distanceMatrix;
	}
}
