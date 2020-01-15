package com.rental;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
@Component
@ConfigurationProperties("rent")
@PropertySource("classpath:config/airport.properties")
@Validated
public class RentalProperties {
 
	private List<Airport> airports = new ArrayList<>();
	@Max(value=5, message="Inventory too large")
	@Min(value=2)
	private int maxInventory;
 
	public static class Airport {
		private String iataCode;
		private String desc;
		private double tax;
		public String getIataCode() {
			return iataCode;
		}
		public void setIataCode(String iataCode) {
			this.iataCode = iataCode;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public double getTax() {
			return tax;
		}
		public void setTax(double tax) {
			this.tax = tax;
		}
	}

	public List<Airport> getAirports() {
		return airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}

	public int getMaxInventory() {
		return maxInventory;
	}

	public void setMaxInventory(int maxInventory) {
		this.maxInventory = maxInventory;
	}
	
	

	 
}
