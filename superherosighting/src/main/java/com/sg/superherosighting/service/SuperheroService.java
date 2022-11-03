/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organizations;
import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @Sweetlana Protsenko
 */
public interface SuperheroService {
    public Location getLocationById(int id);
    public List<Location> getAllLocations();
    public Location addLocation(Location location);
    public void updateLocation(Location location);
    public void deleteLocation(int id);    
    public List<Location> getLocationsForSuperhero(Superhero superhero);
    
    public Superpower getSuperpowerById(int id);
    public List<Superpower> getAllSuperpowers();
    public Superpower addSuperpower(Superpower superpower) throws DuplicateNameExistsExeption;
    public void updateSuperpower(Superpower superpower) throws DuplicateNameExistsExeption;
    public void deleteSuperpowerById(int id);
    
    public Superhero getSuperheroById (int id);
    public List<Superhero> getAllSuperheroes();
    public Superhero addSuperhero(Superhero superhero);
    public void updateSuperhero(Superhero superhero);
    public void deleteSuperheroById(int id); 
    public List<Superhero> getSuperheroesForOrganization(Organizations organizations);
    
    public Sightings getSightingById(int id);
    public List<Sightings> getAllSightings();
    public Sightings addSighting(Sightings sightings);
    public void updateSighting(Sightings sightings);
    public void deleteSightingById(int id);
    
    public List<Sightings> getSightingsForLocation(Location location);
    public List<Sightings> getSightingsByDate(LocalDate date);
    
    public Organizations getOrganizationById(int id);
    public List<Organizations> getAllOrganizations();
    public Organizations addOrganization(Organizations organizations);
    public void updateOrganization(Organizations organizations);
    public void deleteOrganizationById(int id);
        
    public List<Organizations> getOrganizationsForSuperhero(Superhero superhero);
    
    public void validateSuperpower(Superpower superpower) throws DuplicateNameExistsExeption; 
    public void validateSuperhero(Superhero superhero) throws DuplicateNameExistsExeption;
    public void validateLocation(Location location) throws DuplicateNameExistsExeption;
    public void validateOrganizations(Organizations organizations) throws DuplicateNameExistsExeption;
    
}
