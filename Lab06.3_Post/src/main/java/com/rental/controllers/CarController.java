package com.rental.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rental.RentalProperties;
import com.rental.RentalProperties.Airport;
import com.rental.dao.CarService;
import com.rental.dao.RentalCar;

@Controller
@RequestMapping("/car")
public class CarController{
	
	@Autowired private CarService dao;
 
	@GetMapping 
	public String getAll(Model model, @RequestParam("code") Optional<String> optional) {
		String code = optional.orElse("ORD");
	 	List<RentalCar> cars =  dao.getAll().stream().filter(p-> p.getLocation().equals(code)).collect(Collectors.toList());
	 	model.addAttribute("cars", cars);
	 	return "car";
	}
	@Autowired
	private RentalProperties rentalProperties;
	@GetMapping("/{id}")
	public @ResponseBody RentalCar getIndividualCar(@PathVariable("id") long id) {
	 	RentalCar car =  dao.getByID(id);
	 	Airport airport = rentalProperties.getAirports().stream().filter(p-> p.getIataCode().equals(car.getLocation())).findFirst().get();
	 	double tax = car.getDailyRate() * airport.getTax()/100;
	 	car.setDailyRate(car.getDailyRate() + tax);
	 	return car;
	}
	
	 
	

}
