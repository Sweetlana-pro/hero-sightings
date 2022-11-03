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
 * 
 */
@SpringBootTest
public class OrganizationsDaoDBTest {
    
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
    
    public OrganizationsDaoDBTest() {
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
    public void testAddAndGetOrganizationById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        
        Organizations organizations = new Organizations();
        organizations.setOrganizationName("Test OrgName");
        organizations.setOrganizationDescription("Test OrgDescription");
        organizations.setAddress("Test org address");
        organizations.setContacts("Test contacts");
        organizations.setPhone("Test phone");
        organizations.setMembers(members);
        organizations = organizationsDao.addOrganization(organizations);
        
        Organizations fromDao = organizationsDao.getOrganizationById(organizations.getId());
        assertEquals(organizations, fromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationsDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        
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
        organizations2.setMembers(members);
        organizations2 = organizationsDao.addOrganization(organizations2);
        
        List<Organizations> allOrganizations = organizationsDao.getAllOrganizations();
        assertEquals(2, allOrganizations.size());
        assertTrue(allOrganizations.contains(organizations));
        assertTrue(allOrganizations.contains(organizations2));
        
    }
    
    /**
     * Test of updateOrganization method, of class OrganizationsDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        
        Organizations organizations = new Organizations();
        organizations.setOrganizationName("Test OrgName");
        organizations.setOrganizationDescription("Test OrgDescription");
        organizations.setAddress("Test org address");
        organizations.setContacts("Test contacts");
        organizations.setPhone("Test phone");
        organizations.setMembers(members);
        organizations = organizationsDao.addOrganization(organizations);
        
        Organizations fromDao = organizationsDao.getOrganizationById(organizations.getId());
        assertEquals(organizations, fromDao);
        
        organizations.setOrganizationName("New test org name");
        Superhero superhero2 = new Superhero();
        superhero2.setSuperheroName("Test Name");
        superhero2.setSuperheroDescription("Test Description");
        superhero2.setSuperpower(superpower);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        members.add(superhero2);
        organizations.setMembers(members);
        organizationsDao.updateOrganization(organizations);
        assertNotEquals(organizations, fromDao);
        
        fromDao = organizationsDao.getOrganizationById(organizations.getId());
        assertEquals(organizations, fromDao);
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationsDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        Superpower superpower = new Superpower();
        superpower.setPowerName("Test Name");
        superpower.setPowerDescription("Test Description");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Test Name");
        superhero.setSuperheroDescription("Test Description");
        superhero.setSuperpower(superpower);
        superhero = superheroDao.addSuperhero(superhero);
        
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        
        Organizations organizations = new Organizations();
        organizations.setOrganizationName("Test OrgName");
        organizations.setOrganizationDescription("Test OrgDescription");
        organizations.setAddress("Test org address");
        organizations.setContacts("Test contacts");
        organizations.setPhone("Test phone");
        organizations.setMembers(members);
        organizations = organizationsDao.addOrganization(organizations);
        
        Organizations fromDao = organizationsDao.getOrganizationById(organizations.getId());
        assertEquals(organizations, fromDao);
        
        organizationsDao.deleteOrganizationById(organizations.getId());
        
        fromDao = organizationsDao.getOrganizationById(organizations.getId());
        assertNull(fromDao);        
    }

    /**
     * Test of getOrganizationsForSuperhero method, of class OrganizationsDaoDB.
     */
    @Test
    public void testGetOrganizationsForSuperhero() {
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
        
        List<Superhero> members = new ArrayList<>();
        members.add(superhero);
        members.add(superhero2);
        
        List<Superhero> members2 = new ArrayList<>();
        members2.add(superhero2);
        
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
        
        Organizations organizations3 = new Organizations();
        organizations3.setOrganizationName("Test OrgName");
        organizations3.setOrganizationDescription("Test OrgDescription");
        organizations3.setAddress("Test org address");
        organizations3.setContacts("Test contacts");
        organizations3.setPhone("Test phone");
        organizations3.setMembers(members);
        organizations3 = organizationsDao.addOrganization(organizations3); 
        
        List<Organizations> allOrganizations = organizationsDao.getOrganizationsForSuperhero(superhero);
        assertEquals(2, allOrganizations.size());
        assertTrue(allOrganizations.contains(organizations));
        assertFalse(allOrganizations.contains(organizations2));
        assertTrue(allOrganizations.contains(organizations3));
        
        
        
    }
    
}
