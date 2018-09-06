package com.stackroute.cvrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.cvrp.domain.Location;
import com.stackroute.cvrp.domain.Route;
import com.stackroute.cvrp.domain.DateLogistics;
import com.stackroute.cvrp.exceptions.IllegalLocationMatrixException;
import com.stackroute.cvrp.service.CvrpService;

@RestController
@RequestMapping("api/v1/cvrp/")
public class CvrpController {

	private CvrpService cvrpService;
	@Autowired
	public CvrpController(CvrpService cvrpService) {
		this.cvrpService=cvrpService;
	}
	@GetMapping("/slot")
	public ResponseEntity<Route> getJson(){
//		Console.log(this.cvrpService.getJson());
	System.out.println(this.cvrpService.getJson());
		return new ResponseEntity<Route>(this.cvrpService.getJson(),HttpStatus.OK);
	}
	@GetMapping(value="/distancematrix/{slotId}",produces= {"application/json"})
	public ResponseEntity<?> getDistanceMatrix(@PathVariable String slotId){
		try {
			Double[][] matrix=cvrpService.getDistanceMatrix(slotId);
			System.out.println("hello");
			return new ResponseEntity<Double[][]>(matrix, HttpStatus.CREATED);
		}
		catch(IllegalLocationMatrixException e) {
			return new ResponseEntity<String>("{ \"message\": \"" + "no distance matrix" + "\"}", HttpStatus.BAD_REQUEST);
		}
	}
}
