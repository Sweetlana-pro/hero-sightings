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
import java.util.ArrayList;
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
public class SuperpowerDaoDBTest {
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
    
    public SuperpowerDaoDBTest() {
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
     * Test of getAndAddSuperpowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAndAddSuperpowerById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        
        assertEquals(superpower, fromDao);
    }

    /**
     * Test of getAllSuperpowers method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAllSuperpowers() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower superpower2 = new Superpower();
        superpower2.setPowerName("Test Name");
        superpower2.setPowerDescription("Test Description");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        
        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));
    }

    
    /**
     * Test of updateSuperpower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testUpdateSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        
        assertEquals(superpower, fromDao);
        superpower.setPowerName("New Test Name");
        superpowerDao.updateSuperpower(superpower);
        
        assertNotEquals(superpower, fromDao);
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
    }

    /**
     * Test of deleteSuperpowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testDeleteSuperpowerById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        //superhero.setAllOrganizations(allOrganizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        
        Organizations organizations = new Organizations();
        organizations.setOrganizationName("Test OrgName");
        organizations.setOrganizationDescription("Test OrgDescription");
        organizations.setAddress("Test org address");
        organizations.setPhone("Test phone");
        organizations.setMembers(members);
        organizations = organizationsDao.addOrganization(organizations);
        
//        List<Organizations> allOrganizations = new ArrayList<>();
//        allOrganizations.add(organizations);
        
        
        
        
        
    }
    
}
