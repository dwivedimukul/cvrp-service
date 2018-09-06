package com.stackroute.cvrp.repository;

import org.springframework.data.repository.CrudRepository;

import com.stackroute.cvrp.domain.Order;
import com.stackroute.cvrp.domain.Vehicle;

public interface CvrpRepository extends CrudRepository<Vehicle,String>{

}
