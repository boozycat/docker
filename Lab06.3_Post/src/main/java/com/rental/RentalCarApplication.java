package com.rental;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rental.dao.RentalCar;

@SpringBootApplication
public class RentalCarApplication {

	public static void main(String[] args) {
		// SpringApplication.run(RentalCarApplication.class, args);
		SpringApplication app = new SpringApplication(RentalCarApplication.class);
		app.addInitializers(new ProfileInitializer());
		app.run(args);
        //add a comment here as it is important and some more comments
		String BASE_URL = "http://localhost:8081/";
		RentalCar car = new RentalCar("ORD", "SUV", 99.99, "Ford", "Explorer");

		ResponseEntity<String> response = new RestTemplate().postForEntity(BASE_URL + "rental/auto", car, String.class);
		System.out.println(response);
		List<String> locations = response.getHeaders().get("Location");
		String url = locations.get(0);
		System.out.println(url);
		String result = new RestTemplate().getForObject(BASE_URL + url, String.class);
		System.out.println(result);
	}

}
