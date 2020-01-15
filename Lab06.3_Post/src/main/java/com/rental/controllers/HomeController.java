package com.rental.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rental.RentalProperties;

@Controller
@RequestMapping("/")
public class HomeController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RentalProperties rentalProperties;
	@GetMapping
	public String goHome(Model model, @Value("${cars.title}") String pageTitle) {
		model.addAttribute("title", pageTitle);
		model.addAttribute("airports", rentalProperties.getAirports());
		LOGGER.debug("This is a debug message");
	    LOGGER.info("This is an info message");
	    LOGGER.warn("This is a warn message");
	    LOGGER.error("This is an error message");
		return "home";
	}
}
