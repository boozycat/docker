package com.rental.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

public interface CarService {
	
	public Collection<RentalCar> getCarsPerLocationAndPrice(String location, double price) ; 
	public Collection<RentalCar> getAll();
	public RentalCar getByID(long id);
	@Transactional
	void add(RentalCar car);
 
}
