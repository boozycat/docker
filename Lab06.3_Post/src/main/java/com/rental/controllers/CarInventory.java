package com.rental.controllers;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.rental.dao.RentalCar;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CarInventory {
    @XmlElementWrapper(name = "cars")
    @XmlElement(name="car")
    private Collection<RentalCar> inventory;

    public void setInventory(Collection<RentalCar> inventory) {
        this.inventory = inventory;
    }

    public Collection<RentalCar> getInventory() {
        return inventory;
    }
}
