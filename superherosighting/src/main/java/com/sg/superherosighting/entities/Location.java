/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @Sweetlana Protsenko
 */

/*id INT PRIMARY KEY AUTO_INCREMENT,
locationName VARCHAR(100) NOT NULL,
locationDescription VARCHAR(100),
address VARCHAR(100),
coordinates VARCHAR(50)*/
public class Location {
    private int id;
    
    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 100, message = "Location name must be less than 100 characters.")
    private String locationName;
    
    @Size(max = 100, message = "Description must be less than 100 characters.")
    private String locationDescription;
    
    @Size(max = 100, message = "Address must be less than 100 characters.")
    private String address;
    
    @Size(max = 50, message = "Coordinates must be less than 50 characters.")
    private String coordinates;
    
    public Location(){
    }
    
//    public Location(int id, String locationName, String locationDescription, 
//            String address, String coordinates) {
//        this.id = id;
//        this.locationName = locationName;
//        this.locationDescription = locationDescription;
//        this.address = address;
//        this.coordinates = coordinates;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.locationName);
        hash = 29 * hash + Objects.hashCode(this.locationDescription);
        hash = 29 * hash + Objects.hashCode(this.address);
        hash = 29 * hash + Objects.hashCode(this.coordinates);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescription, other.locationDescription)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.coordinates, other.coordinates)) {
            return false;
        }
        return true;
    }
    
    
}
