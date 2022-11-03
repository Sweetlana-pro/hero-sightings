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
public interface OrganizationsDao {
    Organizations getOrganizationById(int id);
    List<Organizations> getAllOrganizations();
    Organizations addOrganization(Organizations organizations);
    void updateOrganization(Organizations organizations);
    void deleteOrganizationById(int id);
        
    List<Organizations> getOrganizationsForSuperhero(Superhero superhero);
}
