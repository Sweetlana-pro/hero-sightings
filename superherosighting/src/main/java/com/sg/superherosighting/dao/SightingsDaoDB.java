/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.LocatioDaoDB.LocationMapper;
import com.sg.superherosighting.dao.SuperheroDaoDB.SuperheroMapper;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @Sweetlana Protsenko
 */
@Repository
public class SightingsDaoDB implements SightingsDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sightings getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sightings WHERE id = ?";
            Sightings sightings = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingsMapper(), id);
            sightings.setLocation(getLocationForSighting(id));
            sightings.setSuperhero(getSuperheroForSighting(id));
            return sightings;            
        }catch(DataAccessException ex) {
            return null;
        }
    }
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l. * FROM location l JOIN sightings s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT * FROM superhero sh JOIN sightings s ON s.superheroId = sh.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);
    }

    @Override
    public List<Sightings> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sightings";
        List<Sightings> allSightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingsMapper());
        associateSuperheroAndLocation(allSightings);
        return allSightings;
    }

    @Override
    @Transactional
    public Sightings addSighting(Sightings sightings) {
        final String INSERT_SIGHTING = "INSERT INTO sightings(superheroId, locationId, sightDate, sightDescription) VALUE(?,?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sightings.getSuperhero().getId(),
                sightings.getLocation().getId(),
                Date.valueOf(sightings.getSightDate()), 
                sightings.getSightDescription());        
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sightings.setId(newId);
        
        return sightings;        
    }
    
    private void associateSuperheroAndLocation(List<Sightings> allSightings) {
        for (Sightings sightings : allSightings) {
            sightings.setSuperhero(getSuperheroForSighting(sightings.getId()));
            sightings.setLocation(getLocationForSighting(sightings.getId()));
        }
    }
    @Override
    @Transactional
    public void updateSighting(Sightings sightings) {
        final String UPDATE_SIGHTINGS = "UPDATE sightings SET superheroId = ?, locationId = ?, sightDate = ?, sightDescription = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTINGS, 
                sightings.getSuperhero().getId(),
                sightings.getLocation().getId(),
                Date.valueOf(sightings.getSightDate()),
                sightings.getSightDescription(),
                sightings.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sightings WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sightings> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sightings WHERE locationId = ?";
        List<Sightings> allSightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingsMapper(), location.getId());
        associateSuperheroAndLocation(allSightings);
        return allSightings;
    }

    @Override
    public List<Sightings> getSightingsByDate(LocalDate date) {
        Date sqlDate = Date.valueOf(date);
        final String SELECT_SIGHTINGS_BY_DATE = "SELECT s.superheroId, s.locationId, s.sightDate, s.sightDescription FROM sightings s WHERE s.sightDate = ?";
        List<Sightings> allSightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingsMapper(), sqlDate);
        
        associateSuperheroAndLocation(allSightings);
        return allSightings;
    }

    public static final class SightingsMapper implements RowMapper<Sightings>{

        @Override
        public Sightings mapRow(ResultSet rs, int index) throws SQLException {
            Sightings sightings = new Sightings();
            sightings.setId(rs.getInt("id"));
            sightings.setSightDate(rs.getDate("sightDate").toLocalDate());
            sightings.setSightDescription(rs.getString("sightDescription"));
            return sightings;      
        }        
    }     
    
}
