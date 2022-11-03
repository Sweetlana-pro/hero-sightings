/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.SuperheroDaoDB.SuperheroMapper;
import com.sg.superherosighting.entities.Organizations;
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
public class OrganizationsDaoDB implements OrganizationsDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override 
    public Organizations getOrganizationById(int id) {
        try{
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organizations WHERE id = ?";
            Organizations organizations = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationsMapper(), id);
            organizations.setMembers(getMembersForOrganization(id));
            return organizations;
        }catch(DataAccessException ex){
            return null;
        }
    }
    
    public List<Superhero> getMembersForOrganization(int id) {
        final String SELECT_MEMBERS_FOR_ORGANIZATION = "SELECT s. * FROM superhero s JOIN superheroOrganizations so ON so.superheroId = s.id WHERE so.organizationId = ?";
        return jdbc.query(SELECT_MEMBERS_FOR_ORGANIZATION, new SuperheroMapper(), id);
    }

    @Override
    public List<Organizations> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organizations";
        List<Organizations> allOrganizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationsMapper());
        associateSuperheroes(allOrganizations);
        return allOrganizations;
    }
    private void associateSuperheroes(List<Organizations> allOrganizations) {
       for (Organizations organizations : allOrganizations){
           organizations.setMembers(getMembersForOrganization(organizations.getId()));           
       }
    }

    @Override
    @Transactional
    public Organizations addOrganization(Organizations organizations) {
        final String INSERT_ORGANIZATION = "INSERT INTO organizations(organizationName, organizationDescription, address, contacts, phone) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organizations.getOrganizationName(),
                organizations.getOrganizationDescription(),
                organizations.getAddress(),
                organizations.getContacts(),
                organizations.getPhone());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organizations.setId(newId);
        insertSuperheroOrganizations(organizations);
        return organizations;        
    }
    private void insertSuperheroOrganizations(Organizations organizations) {
        final String INSERT_SUPERHEROORGANIZATIONS = "INSERT INTO superheroorganizations(superheroId, organizationId) VALUES(?, ?)";
        for(Superhero superhero : organizations.getMembers()){
            jdbc.update(INSERT_SUPERHEROORGANIZATIONS,
                    superhero.getId(),
                    organizations.getId()
                    );
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organizations organizations) {
        final String UPDATE_ORGANIZATION = "UPDATE organizations SET organizationName = ?, organizationDescription = ?, address = ?, contacts = ?, phone = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organizations.getOrganizationName(),
                organizations.getOrganizationDescription(),
                organizations.getAddress(),
                organizations.getContacts(),
                organizations.getPhone(),
                organizations.getId());
        
        final String DELETE_SUPERHEROORGANIZATIONS = "DELETE FROM superheroOrganizations WHERE organizationId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATIONS, organizations.getId());
        insertSuperheroOrganizations(organizations);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_SUPERHEROORGANIZATIONS = "DELETE FROM superheroOrganizations WHERE organizationId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATIONS, id);
        
        final String DELETE_ORGANIZATIONS = "DELETE FROM organizations WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATIONS, id);
    }

    @Override
    public List<Organizations> getOrganizationsForSuperhero(Superhero superhero) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = 
                "SELECT o.* FROM organizations o JOIN superheroOrganizations so ON so.organizationId = o.id WHERE so.superheroId = ?";
        List<Organizations> allOrganizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationsMapper(), superhero.getId());
        associateSuperheroes(allOrganizations);
        return allOrganizations;       
        
    }

    
    public static final class OrganizationsMapper implements RowMapper<Organizations>{

        @Override
        public Organizations mapRow(ResultSet rs, int index) throws SQLException {
            Organizations organizations = new Organizations();
            organizations.setId(rs.getInt("id"));
            organizations.setOrganizationName(rs.getString("organizationName"));
            organizations.setOrganizationDescription(rs.getString("organizationDescription"));
            organizations.setAddress(rs.getString("address"));
            organizations.setContacts(rs.getString("contacts"));
            organizations.setPhone(rs.getString("phone"));
            
            return organizations;
        }        
    }    
}
