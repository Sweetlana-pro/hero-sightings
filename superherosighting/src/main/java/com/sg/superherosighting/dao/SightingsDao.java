/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sightings;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @Sweetlana Protsenko
 */
public interface SightingsDao {
    Sightings getSightingById(int id);
    List<Sightings> getAllSightings();
    Sightings addSighting(Sightings sightings);
    void updateSighting(Sightings sightings);
    void deleteSightingById(int id);
    
    List<Sightings> getSightingsForLocation(Location location);
    List<Sightings> getSightingsByDate(LocalDate date);
    
}
