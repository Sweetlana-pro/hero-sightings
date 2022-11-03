/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organizations;
import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @Sweetlana Protsenko
 */
@SpringBootTest
public class LocatioDaoDBTest {
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationsDao organizationsDao;
    
    @Autowired
    SightingsDao sightingsDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    public LocatioDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }
        
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for (Superhero superhero : superheroes){
            superheroDao.deleteSuperheroById(superhero.getId());
        }
        
        List<Organizations> allOrganizations = organizationsDao.getAllOrganizations();
        allOrganizations.forEach(organizations -> {organizationsDao.deleteOrganizationById(organizations.getId());
        });
          
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations){
            locationDao.deleteLocation(location.getId());
        }
        
        List<Sightings> allSightings = sightingsDao.getAllSightings();
        for(Sightings sightings : allSightings){
            sightingsDao.deleteSightingById(sightings.getId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLocationById method, of class LocatioDaoDB.
     */
    @Test
    public void testAddAndGetLocationById() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocatioDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location Name");
        location2.setLocationDescription("test Location Description");
        location2.setAddress("Test address");
        location2.setCoordinates("Test coordinates");
        location2 = locationDao.addLocation(location);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }
    
    /**
     * Test of updateLocation method, of class LocatioDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        location.setLocationName("New Test name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocation method, of class LocatioDaoDB.
     */
    @Test
    public void testDeleteLocation() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.MIN);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocation(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
        
    }

    /**
     * Test of getLocationsForSuperhero method, of class LocatioDaoDB.
     */
    @Test
    public void testGetLocationsForSuperhero() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location2 Name");
        location2.setLocationDescription("test Location2 Description");
        location2.setAddress("Test address2");
        location2.setCoordinates("Test coordinates2");
        location2 = locationDao.addLocation(location2);
        
        Location location3 = new Location();
        location3.setLocationName("Test Location3 Name");
        location3.setLocationDescription("test Location3 Description");
        location3.setAddress("Test address3");
        location3.setCoordinates("Test coordinates3");
        location3 = locationDao.addLocation(location3);
        
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Superhero superhero2 = new Superhero();
        superhero2.setSuperheroName("Test Name2");
        superhero2.setSuperheroDescription("Test Description2");
        superhero2.setSuperpower(superpower);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings sightings2 = new Sightings();
        sightings2.setSuperhero(superhero);
        sightings2.setLocation(location2);
        sightings2.setSightDate(LocalDate.now());
        sightings2 = sightingsDao.addSighting(sightings2);
        
        Sightings sightings3 = new Sightings();
        sightings3.setSuperhero(superhero2);
        sightings3.setLocation(location3);
        sightings3.setSightDate(LocalDate.now());
        sightings3 = sightingsDao.addSighting(sightings3);
        
        List<Location> locations = locationDao.getLocationsForSuperhero(superhero);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertFalse(locations.contains(location3));
        assertTrue(locations.contains(location2));
    }    
}
