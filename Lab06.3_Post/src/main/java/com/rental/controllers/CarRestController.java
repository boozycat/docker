package com.rental.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.RentalProperties;
import com.rental.RentalProperties.Airport;
import com.rental.dao.CarService;
import com.rental.dao.RentalCar;

@RestController
@RequestMapping("/auto")
@CrossOrigin
public class CarRestController {
	@Autowired
	private CarService dao;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public Collection<RentalCar> getAll() throws JSONException {
		return dao.getAll();
	}

	@GetMapping(path = "/{pickUp}/{price}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Collection<RentalCar> getLocationPrice(@PathVariable("pickUp") String location,
			@PathVariable("price") double price) throws JSONException {
		return dao.getAll().stream().filter(p -> p.getDailyRate() < price && p.getLocation().equals(location))
				.collect(Collectors.toList());
	}

	@Autowired
	private RentalProperties rentalProperties;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public RentalCar getIndividualCar(@PathVariable("id") long id) {
		RentalCar car = dao.getByID(id);
		Airport airport = rentalProperties.getAirports().stream().filter(p -> p.getIataCode().equals(car.getLocation()))
				.findFirst().get();
		double tax = car.getDailyRate() * airport.getTax() / 100;
		car.setDailyRate(car.getDailyRate() + tax);
		return car;
	}

	// XML version
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE })
	public CarInventory getAllXml() throws JSONException {
		Collection<RentalCar> cars = dao.getAll();
		CarInventory inventory = new CarInventory();
		inventory.setInventory(cars);
		return inventory;
	}

	// new service
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<Collection<RentalCar>> add(HttpServletRequest request, @RequestBody RentalCar car)
			throws URISyntaxException {
        dao.add(car);
		RentalCar responseCar = dao.getCarsPerLocationAndPrice(car.getLocation(), car.getDailyRate() + 1).stream()
				.filter(p -> p.getMake().equals(car.getMake()) && p.getModel().equals(car.getModel())
						&& p.getDailyRate() == car.getDailyRate())
				.findFirst().get();
		URI uri = new URI(request.getRequestURI() + "/" + responseCar.getId());
		return ResponseEntity.created(uri).build();
	}

}
