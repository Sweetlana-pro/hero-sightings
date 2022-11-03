/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.SuperheroDaoDB.SuperheroMapper;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class LocatioDaoDB implements LocationDao {
   @Autowired
   JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }        
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(locationName, locationDescription, address, coordinates) VALUES(?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getAddress(),
                location.getCoordinates());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
        
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET locationName = ?, locationDescription = ?, address = ?, coordinates = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getAddress(),
                location.getCoordinates(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocation(int id) {
        final String DELETE_SIGHTINGS = "DELETE FROM sightings WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTINGS, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
        
    }

    @Override
    public List<Location> getLocationsForSuperhero(Superhero superhero) {
        final String SELECT_LOCATIONS_FOR_SUPERHERO = "SELECT l.* FROM location l JOIN sightings s ON s.locationId = l.id WHERE s.superheroId = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_SUPERHERO, new LocationMapper(), superhero.getId());
        for (Location location : locations) {
            location.getId();
        }
            
        return locations;
    }
    
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(rs.getString("locationDescription"));
            location.setAddress(rs.getString("address"));
            location.setCoordinates(rs.getString("coordinates"));
            
            return location;
        }
        
    }
    
}
