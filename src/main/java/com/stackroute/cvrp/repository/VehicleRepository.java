package com.stackroute.cvrp.repository;

import org.springframework.data.repository.CrudRepository;

import com.stackroute.cvrp.domain.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

}
