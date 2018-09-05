package com.stackroute.cvrp.repository;

import org.springframework.data.repository.CrudRepository;

import com.stackroute.cvrp.domain.Order;

public interface OrderRepository extends CrudRepository<Order, String> {

}
