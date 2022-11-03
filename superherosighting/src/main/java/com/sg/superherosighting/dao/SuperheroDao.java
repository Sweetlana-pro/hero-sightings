/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Organizations;
import com.sg.superherosighting.entities.Superhero;
import java.util.List;

/**
 *
 * @Sweetlana Protsenko
 */
public interface SuperheroDao {
    Superhero getSuperheroById (int id);
    List<Superhero> getAllSuperheroes();
    Superhero addSuperhero(Superhero superhero);
    void updateSuperhero(Superhero superhero);
    void deleteSuperheroById(int id); 
    List<Superhero> getSuperheroesForOrganization(Organizations organizations);
}
