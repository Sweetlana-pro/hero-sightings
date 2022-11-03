/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @Sweetlana Protsenko
 */

public class Superhero {
    private int id; 
    
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message="Name must be fewer than 50 characters")
    private String superheroName;  
    
    @NotBlank(message = "Description must not be blank")
    @Size(max = 100, message="Description must be less than 100 characters")
    private String superheroDescription;
    private Superpower superpower;
    
    private byte[] superheroImage;
    
    List<Organizations> allOrganizations;

    public List<Organizations> getAllOrganizations() {
        return allOrganizations;
    }

    public void setAllOrganizations(List<Organizations> allOrganizations) {
        this.allOrganizations = allOrganizations;
    }

    public byte[] getSuperheroImage() {
        return superheroImage;
    }

    public void setSuperheroImage(byte[] superheroImage) {
        this.superheroImage = superheroImage;
    }
      
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuperheroName() {
        return superheroName;
    }

    public void setSuperheroName(String superheroName) {
        this.superheroName = superheroName;
    }

    public String getSuperheroDescription() {
        return superheroDescription;
    }

    public void setSuperheroDescription(String superheroDescription) {
        this.superheroDescription = superheroDescription;
    }

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.superheroName);
        hash = 67 * hash + Objects.hashCode(this.superheroDescription);
        hash = 67 * hash + Objects.hashCode(this.superpower);
        hash = 67 * hash + Arrays.hashCode(this.superheroImage);
        hash = 67 * hash + Objects.hashCode(this.allOrganizations);
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
        final Superhero other = (Superhero) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.superheroName, other.superheroName)) {
            return false;
        }
        if (!Objects.equals(this.superheroDescription, other.superheroDescription)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        if (!Arrays.equals(this.superheroImage, other.superheroImage)) {
            return false;
        }
        if (!Objects.equals(this.allOrganizations, other.allOrganizations)) {
            return false;
        }
        return true;
    }


    
}
