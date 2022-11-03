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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class SuperheroDaoDBTest {
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    @Autowired
    SuperheroDao superheroDao;
        
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationsDao organizationsDao;
    
    @Autowired
    SightingsDao sightingsDao;
    
    
    
    public SuperheroDaoDBTest() {
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
     * Test of getOrganizationById method, of class OrganizationsDaoDB.
     */
    @Test
    public void testAddAndGetSuperheroById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    /**
     * Test of getAllSuperheroes method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheroes() {
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
        superhero2.setSuperheroName("Test Name");
        superhero2.setSuperheroDescription("Test Description");
        superhero2.setSuperpower(superpower);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        
        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }

    /**
     * Test of updateSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testUpdateSuperhero() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        
        assertEquals(superhero, fromDao);
        superhero.setSuperheroName("New Test Name");
        superheroDao.updateSuperhero(superhero);
        
        assertNotEquals(superhero, fromDao);
        
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        
        assertEquals(superhero, fromDao);
    }

    /**
     * Test of deleteSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testDeleteSuperheroById() {
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
        location.setLocationDescription("test Location Description");
        location.setAddress("Test address");
        location.setCoordinates("Test coordinates");
        location = locationDao.addLocation(location);
        
        Sightings sightings = new Sightings();
        sightings.setSuperhero(superhero);
        sightings.setLocation(location);
        sightings.setSightDate(LocalDate.MIN);
        sightings.setSightDescription("Test sight description");
        
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        
        assertEquals(superhero, fromDao);
        
        superheroDao.deleteSuperheroById(superhero.getId());
        
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertNull(fromDao);
        
        
    }
    @Test
    public void getSuperheroesForOrganization(){
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
        superhero2.setSuperheroName("Test Name");
        superhero2.setSuperheroDescription("Test Description");
        superhero2.setSuperpower(superpower);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Superhero superhero3 = new Superhero();
        superhero3.setSuperheroName("Test Name");
        superhero3.setSuperheroDescription("Test Description");
        superhero3.setSuperpower(superpower);
        superhero3 = superheroDao.addSuperhero(superhero3);
        
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        members.add(superhero2);
        
        List<Superhero> members2 = new ArrayList<>();
        members2.add(superhero3);
        
        Organizations organizations = new Organizations();
        organizations.setOrganizationName("Test OrgName");
        organizations.setOrganizationDescription("Test OrgDescription");
        organizations.setAddress("Test org address");
        organizations.setContacts("Test contacts");
        organizations.setPhone("Test phone");
        organizations.setMembers(members);
        organizations = organizationsDao.addOrganization(organizations);        
             
        Organizations organizations2 = new Organizations();
        organizations2.setOrganizationName("Test OrgName");
        organizations2.setOrganizationDescription("Test OrgDescription");
        organizations2.setAddress("Test org address");
        organizations2.setContacts("Test contacts");
        organizations2.setPhone("Test phone");
        organizations2.setMembers(members2);
        organizations2 = organizationsDao.addOrganization(organizations2);
        
        List<Superhero> fromDao= superheroDao.getSuperheroesForOrganization(organizations);      
        assertEquals(2, members.size());
        assertTrue(members.contains(superhero));
        assertTrue(members.contains(superhero2));
        assertFalse(members.contains(superhero3));
        
    }
    
}
