/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.OrganizationsDaoDB.OrganizationsMapper;
import com.sg.superherosighting.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.sg.superherosighting.entities.Organizations;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
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
public class SuperheroDaoDB implements SuperheroDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            Superhero superhero = jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setSuperpower(getSuperpowerForSuperhero(id));
            superhero.setAllOrganizations(getOrganizationsForSuperhero(id));
            return superhero;
        }catch(DataAccessException ex) {
            return null;
        }        
    }
    
    private List<Organizations> getOrganizationsForSuperhero(int id){
        final String SELECT_ORG_FOR_COURSE = "SELECT o.* FROM organizations o JOIN superheroOrganizations so ON so.organizationId = o.id WHERE so.superheroId = ?";
        return jdbc.query(SELECT_ORG_FOR_COURSE, new OrganizationsMapper(), id);
    }
    
    @Override
    public List<Superhero> getAllSuperheroes() {
        final String GET_ALL_SUPERHEROES = "SELECT * FROM superhero";
        List<Superhero> superheroes = jdbc.query(GET_ALL_SUPERHEROES, new SuperheroMapper());
        associateSuperpower(superheroes);
        return superheroes;
    }
    
    private void associateSuperpower(List<Superhero> superheroes) {
        for (Superhero superhero : superheroes){
            superhero.setSuperpower(getSuperpowerForSuperhero(superhero.getId()));
            superhero.setAllOrganizations(getOrganizationsForSuperhero(superhero.getId()));
        }
    }
    private Superpower getSuperpowerForSuperhero(int id) {
        final String SELECT_SUPERPOWER_FOR_SUPERHERO = "SELECT  sp.* FROM superpower sp JOIN superhero sh ON sh.superpowerId = sp.id WHERE sh.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERHERO, new SuperpowerMapper(), id);}
    
    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
       final String INSERT_SUPERHERO = "INSERT INTO superhero(superheroName, superheroDescription, superpowerId, image) VALUES(?,?,?,?)";
       jdbc.update(INSERT_SUPERHERO,
               superhero.getSuperheroName(),
               superhero.getSuperheroDescription(),
               superhero.getSuperpower().getId(),
               superhero.getSuperheroImage());
       int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
       superhero.setId(newId);
       //insertSuperheroOrganizations(superhero);
       return superhero;
    }
    private void insertSuperheroOrganizations(Superhero superhero) {
        final String INSERT_SUPERHEROORGANIZATIONS = "INSERT INTO superheroOrganizations(superheroId, organizationId) VALUE(?,?)";
        for(Organizations organizations : superhero.getAllOrganizations()){
            jdbc.update(INSERT_SUPERHEROORGANIZATIONS, superhero.getId(),
                    organizations.getId());
        }
    }

    @Override
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET superheroName = ?, superheroDescription =?, superpowerId = ?, image = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO,
               superhero.getSuperheroName(),
               superhero.getSuperheroDescription(),
               superhero.getSuperpower().getId(),
               superhero.getSuperheroImage(),
               superhero.getId());
        final String DELETE_SUPERHEROORGANIZATIONS = "DELETE FROM superheroOrganizations WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATIONS, superhero.getId());
 
    }
    
    
    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHEROORGANIZATIONS = "DELETE so.* FROM superheroOrganizations so WHERE so.superheroId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATIONS, id);
        
        final String DELETE_SIGHTINGS = "DELETE s.* FROM sightings s WHERE s.superheroId = ?";
        jdbc.update(DELETE_SIGHTINGS, id);
        
        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    @Override
    public List<Superhero> getSuperheroesForOrganization(Organizations organizations) {
        final String SELECT_SUPERHEROES_FOR_ORGANIZATION = "SELECT s.* FROM superhero s JOIN superheroOrganizations so ON so.superheroId = s.id WHERE so.organizationId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_ORGANIZATION, new SuperheroMapper(), organizations.getId());
        return superheroes;
    }
 

    public static final class SuperheroMapper implements RowMapper<Superhero> {
        
        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("id"));
            superhero.setSuperheroName(rs.getString("superheroName"));
            superhero.setSuperheroDescription(rs.getString("superheroDescription"));
            superhero.setSuperheroImage(rs.getBytes("image"));
            
            
            return superhero;
        }
    }
}
