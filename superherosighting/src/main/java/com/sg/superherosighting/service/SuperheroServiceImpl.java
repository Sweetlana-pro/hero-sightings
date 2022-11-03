/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationsDao;
import com.sg.superherosighting.dao.SightingsDao;
import com.sg.superherosighting.dao.SuperheroDao;
import com.sg.superherosighting.dao.SuperpowerDao;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organizations;
import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

///**
// *
// * @Sweetlana Protsenko
// */
@Component
public class SuperheroServiceImpl implements SuperheroService {
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationsDao organizationsDao;
    
    @Autowired
    SightingsDao sightingsDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    SuperpowerDao superpowerDao;

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);            
    }
    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocation(int id) {
        locationDao.deleteLocation(id);
    }
        

    @Override
    public List<Location> getLocationsForSuperhero(Superhero superhero) {
        return locationDao.getLocationsForSuperhero(superhero);
    }
    

    @Override
    public Superpower getSuperpowerById(int id) {
        return superpowerDao.getSuperpowerById(id);
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        return superpowerDao.getAllSuperpowers();
    }

    @Override
    public Superpower addSuperpower(Superpower superpower) throws DuplicateNameExistsExeption {
        return superpowerDao.addSuperpower(superpower);
    }

    @Override
    public void updateSuperpower(Superpower superpower) throws DuplicateNameExistsExeption {
        superpowerDao.updateSuperpower(superpower);
    }

    @Override
    public void deleteSuperpowerById(int id) {
        superpowerDao.deleteSuperpowerById(id);
    }

    @Override
    public Superhero getSuperheroById(int id) {
        return superheroDao.getSuperheroById(id);
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        return superheroDao.getAllSuperheroes();
    }

    @Override
    public Superhero addSuperhero(Superhero superhero) {
        return superheroDao.addSuperhero(superhero);
    }

    @Override
    public void updateSuperhero(Superhero superhero) {
        superheroDao.updateSuperhero(superhero);
    }

    @Override
    public void deleteSuperheroById(int id) {
        superheroDao.deleteSuperheroById(id);
    }

    @Override
    public List<Superhero> getSuperheroesForOrganization(Organizations organizations) {
        return superheroDao.getSuperheroesForOrganization(organizations);
    }

    @Override
    public Sightings getSightingById(int id) {
        return sightingsDao.getSightingById(id);
    }

    @Override
    public List<Sightings> getAllSightings() {
        return sightingsDao.getAllSightings();        
    }

    @Override
    public Sightings addSighting(Sightings sightings) {        
        return sightingsDao.addSighting(sightings);
    }

    @Override
    public void updateSighting(Sightings sightings) {
        sightingsDao.updateSighting(sightings);
    }

    @Override
    public void deleteSightingById(int id) {
        sightingsDao.deleteSightingById(id);
    }

    @Override
    public List<Sightings> getSightingsForLocation(Location location) {
        return sightingsDao.getSightingsForLocation(location);
    }

    @Override
    public List<Sightings> getSightingsByDate(LocalDate date) {
        return sightingsDao.getSightingsByDate(date);
    }

    @Override
    public Organizations getOrganizationById(int id) {
        return organizationsDao.getOrganizationById(id);
    }

    @Override
    public List<Organizations> getAllOrganizations() {
        return organizationsDao.getAllOrganizations();
    }

    @Override
    public Organizations addOrganization(Organizations organizations) {
        return organizationsDao.addOrganization(organizations);
    }

    @Override
    public void updateOrganization(Organizations organizations) {
        organizationsDao.updateOrganization(organizations);
    }

    @Override
    public void deleteOrganizationById(int id) {
        organizationsDao.deleteOrganizationById(id);
    }

    @Override
    public List<Organizations> getOrganizationsForSuperhero(Superhero superhero) {
        return organizationsDao.getOrganizationsForSuperhero(superhero);
    }

    @Override
    public void validateSuperpower(Superpower superpower) throws DuplicateNameExistsExeption {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        boolean isDuplicate = false;
        
        for(Superpower power: superpowers){
            if(power.getPowerName().toLowerCase().equals(superpower.getPowerName().toLowerCase())){
                isDuplicate = true;
            }
        }
        if(isDuplicate){
            throw new DuplicateNameExistsExeption("This Superpower Name Already Exists");
        }
    }

    @Override
    public void validateSuperhero(Superhero superhero) throws DuplicateNameExistsExeption {
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        boolean isDuplicate = false;
        
        for(Superhero hero : superheroes){
            if(hero.getSuperheroName().toLowerCase().equals(superhero.getSuperheroName().toLowerCase())){
                isDuplicate = true;
            }
        }
        if(isDuplicate){
            throw new DuplicateNameExistsExeption("This Name Already Exists");
        }
    }
    @Override
    public void validateLocation(Location location) throws DuplicateNameExistsExeption{
        List<Location> locations = locationDao.getAllLocations();
        boolean isDuplicate = false;
        
        for(Location loc : locations){
            if(loc.getLocationName().toLowerCase().equals(location.getLocationName().toLowerCase())){
                isDuplicate = true;
            }
        }
        if(isDuplicate){
            throw new DuplicateNameExistsExeption("This Name Already Exists");
        }
    }
    

    @Override
    public void validateOrganizations(Organizations organizations) throws DuplicateNameExistsExeption {
        List<Organizations> allOrganizations = organizationsDao.getAllOrganizations();
        boolean isDuplicate = false;
        
        for(Organizations org : allOrganizations){
            if(org.getOrganizationName().toLowerCase().equals(organizations.getOrganizationName().toLowerCase())){
                isDuplicate = true;
            }
        }
        if(isDuplicate){
            throw new DuplicateNameExistsExeption("This Name Already Exists");
        }
    }    
}
