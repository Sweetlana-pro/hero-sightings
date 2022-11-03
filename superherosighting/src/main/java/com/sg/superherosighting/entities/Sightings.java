/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @Sweetlana Protsenko
 */
public class Sightings {
    private int id;
    @NotNull(message = "Superhero was not selected")
    private Superhero superhero;
    @NotNull(message = "Location was not selected")
    private Location location;
    //@NotEmpty(message = "Date was not selected")
    @Past(message = "Date must be in the past")
    private LocalDate sightDate;
    @Size(max = 100, message="Description must be less than 100 characters")
    private String sightDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Superhero getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getSightDate() {
        return sightDate;
    }

    public void setSightDate(LocalDate sightDate) {
        this.sightDate = sightDate;
    }

    public String getSightDescription() {
        return sightDescription;
    }

    public void setSightDescription(String sightDescription) {
        this.sightDescription = sightDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.superhero);
        hash = 23 * hash + Objects.hashCode(this.location);
        hash = 23 * hash + Objects.hashCode(this.sightDate);
        hash = 23 * hash + Objects.hashCode(this.sightDescription);
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
        final Sightings other = (Sightings) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.sightDescription, other.sightDescription)) {
            return false;
        }
        if (!Objects.equals(this.superhero, other.superhero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.sightDate, other.sightDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sightings{" + "id=" + id + ", superhero=" + superhero + ", location=" + location + ", sightDate=" + sightDate + ", sightDescription=" + sightDescription + '}';
    }
       
}
