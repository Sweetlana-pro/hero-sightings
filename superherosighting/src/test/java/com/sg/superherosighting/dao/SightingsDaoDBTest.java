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
public class SightingsDaoDBTest {
    
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
    
    public SightingsDaoDBTest() {
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
        for (Organizations organizations : allOrganizations) {
            organizationsDao.deleteOrganizationById(organizations.getId());
        }
          
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
     * Test of getSightingById method, of class SightingsDaoDB.
     */
    @Test
    public void testAddAndGetSightingById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings fromDao = sightingsDao.getSightingById(sightings.getId());
        assertEquals(sightings, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingsDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings sightings2 = new Sightings();
        sightings2.setSuperhero(superhero);
        sightings2.setLocation(location);
        sightings2.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings2 = sightingsDao.addSighting(sightings2);
        
        List<Sightings> allSightings = sightingsDao.getAllSightings();
        assertEquals(2, allSightings.size());
        assertTrue(allSightings.contains(sightings));
        assertTrue(allSightings.contains(sightings2));
        
    }

    
    /**
     * Test of updateSighting method, of class SightingsDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings fromDao = sightingsDao.getSightingById(sightings.getId());
        assertEquals(sightings, fromDao);
        
        Location location2 = new Location();
        location2.setLocationName("New Test Location Name");
        location2.setLocationDescription("New Test Location Description");
        location2.setAddress("New Test address");
        location2.setCoordinates("New Test coordinates");
        location2 = locationDao.addLocation(location2);
        
        
        sightings.setLocation(location2);
        sightingsDao.updateSighting(sightings);
        assertNotEquals(sightings, fromDao);
        fromDao = sightingsDao.getSightingById(sightings.getId());
        assertEquals(sightings, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingsDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings fromDao = sightingsDao.getSightingById(sightings.getId());
        assertEquals(sightings, fromDao);
        
        sightingsDao.deleteSightingById(sightings.getId());
        
        fromDao = sightingsDao.getSightingById(sightings.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsForLocation method, of class SightingsDaoDB.
     */
    @Test
    public void testGetSightingsForLocation() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location Name");
        location2.setLocationDescription("Test Location Description");
        location2.setAddress("Test address");
        location2.setCoordinates("Test coordinates");
        location2 = locationDao.addLocation(location2);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings sightings2 = new Sightings();
        sightings2.setSuperhero(superhero);
        sightings2.setLocation(location2);
        sightings2.setSightDate(LocalDate.now());
        sightings2.setSightDescription(" Test sightDescription");
        sightings2 = sightingsDao.addSighting(sightings2);
        
        Sightings sightings3 = new Sightings();
        sightings3.setSuperhero(superhero);
        sightings3.setLocation(location);
        sightings3.setSightDate(LocalDate.now());
        sightings3.setSightDescription(" Test sightDescription");
        sightings3 = sightingsDao.addSighting(sightings3);
        
        List<Sightings> allSightings = sightingsDao.getSightingsForLocation(location);
        assertEquals(2, allSightings.size());
        assertTrue(allSightings.contains(sightings));
        assertFalse(allSightings.contains(sightings2));
        assertTrue(allSightings.contains(sightings3));
    }

    /**
     * Test of getSightingsByDate method, of class SightingsDaoDB.
     */
    @Test
    public void testGetSightingsByDate() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("Test Location Name");
        location2.setLocationDescription("Test Location Description");
        location2.setAddress("Test address");
        location2.setCoordinates("Test coordinates");
        location2 = locationDao.addLocation(location2);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.now());
        sightings.setSightDescription(" Test sightDescription");
        sightings = sightingsDao.addSighting(sightings);
        
        Sightings sightings2 = new Sightings();
        sightings2.setSuperhero(superhero);
        sightings2.setLocation(location2);
        sightings2.setSightDate(LocalDate.now());
        sightings2.setSightDescription(" Test sightDescription");
        sightings2 = sightingsDao.addSighting(sightings2);
        
        Sightings sightings3 = new Sightings();
        sightings3.setSuperhero(superhero);
        sightings3.setLocation(location);
        sightings3.setSightDate(LocalDate.now().minusDays(5));
        sightings3.setSightDescription(" Test sightDescription");
        sightings3 = sightingsDao.addSighting(sightings3);
        
        List<Sightings> allSightings = sightingsDao.getSightingsByDate(LocalDate.now());
        assertEquals(2, allSightings.size());
        assertTrue(allSightings.contains(sightings));
        assertTrue(allSightings.contains(sightings2));
        assertFalse(allSightings.contains(sightings3));
    }
    
}
