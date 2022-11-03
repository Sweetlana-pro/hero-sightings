/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

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
public class SuperpowerDaoDB implements SuperpowerDao{
    @Autowired
   JdbcTemplate jdbc;

    @Override
    public Superpower getSuperpowerById(int id) {
        try{
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superpower WHERE id = ?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), id);
            
        }catch(DataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM superpower";
        List<Superpower> superpowers = jdbc.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
        return superpowers;
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superpower(powerName, powerDescription) VALUE(?,?)";
        jdbc.update(INSERT_SUPERPOWER, 
                superpower.getPowerName(),
                superpower.getPowerDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);
        return superpower;
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "UPDATE superpower SET powerName = ?, powerDescription = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superpower.getPowerName(),
                superpower.getPowerDescription(),
                superpower.getId());
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        final String DELETE_SUPERHEROORGANIZATIONS = "DELETE so.* FROM superheroOrganizations so JOIN superhero sh ON so.superheroId = sh.id WHERE sh.superpowerId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATIONS, id);
        
        final String DELETE_SIGHTING = "DELETE s.* FROM sightings s JOIN superhero sh ON s.superheroId = sh.id WHERE sh.superpowerId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_SUPERHERO = "DELETE sh.* FROM superhero sh WHERE sh.superpowerId = ?";
        jdbc.update(DELETE_SUPERHERO, id);
        
        final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }
    
    
    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("id"));
            superpower.setPowerName(rs.getString("powerName"));
            superpower.setPowerDescription(rs.getString("powerDescription"));
            
            return superpower;
        }
    }
    
}
