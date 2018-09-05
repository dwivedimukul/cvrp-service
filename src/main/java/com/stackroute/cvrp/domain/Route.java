package com.stackroute.cvrp.domain;

public class Route {
	private DateLogistics dataLogistics;
	private Order newOrder;
	
	public Route(DateLogistics dataLogistics, Order newOrder) {
		this.dataLogistics = dataLogistics;
		this.newOrder = newOrder;
	}
	public DateLogistics getDataLogistics() {
		return dataLogistics;
	}
	public void setDataLogistics(DateLogistics dataLogistics) {
		this.dataLogistics = dataLogistics;
	}
	public Order getNewOrder() {
		return newOrder;
	}
	public void setNewOrder(Order newOrder) {
		this.newOrder = newOrder;
	}

}
