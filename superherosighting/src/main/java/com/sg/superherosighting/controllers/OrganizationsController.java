/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;


import com.sg.superherosighting.entities.Organizations;

import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.service.DuplicateNameExistsExeption;
import com.sg.superherosighting.service.SuperheroServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @Sweetlana Protsenko
 */
@Controller
public class OrganizationsController {
    @Autowired
    SuperheroServiceImpl service;
    
    Set<ConstraintViolation<Organizations>> violations = new HashSet<>();
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organizations> organizations = service.getAllOrganizations();
        List<Superhero> superheroes = service.getAllSuperheroes();
        
        model.addAttribute("organizations", service.getAllOrganizations());
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("errors", violations);
        return "organizations";        
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organizations organizations, HttpServletRequest request){
        
        String[] superheroIds = request.getParameterValues("superheroId");
       
        List<Superhero> superheroes = new ArrayList<>();
        
       
        for(String superheroId : superheroIds) {
            superheroes.add(service.getSuperheroById(Integer.parseInt(superheroId)));           
           
        } 
        organizations.setMembers(superheroes);   
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organizations);

        if(violations.isEmpty()) {
        service.addOrganization(organizations);
        }
        
        return "redirect:/organizations";
    }
    
  
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        service.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String organizatioDetail(Integer id, Model model) {
        Organizations organizations = service.getOrganizationById(id);
        model.addAttribute("organizations", organizations);
        return "organizationDetail";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organizations organizations = service.getOrganizationById(id);
        List<Superhero> superheroes = service.getAllSuperheroes();
        
        model.addAttribute("organizations", organizations);
        model.addAttribute("superheroes", superheroes);
        
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performeditOrganization(@Valid Organizations organizations, BindingResult result, HttpServletRequest request, Model model) {

        String[] superheroIds = request.getParameterValues("superheroId");                      
        List<Superhero> members = new ArrayList<>();
        if(superheroIds != null){
            for(String superheroId : superheroIds) {
                members.add(service.getSuperheroById(Integer.parseInt(superheroId)));
            }
        }else {
           FieldError error = new FieldError("organizations", "members", "Must include at least one member");
            result.addError(error); 
                      
        }
        organizations.setMembers(members);
        
        if(result.hasErrors()) {
            
            model.addAttribute("superheroes", service.getAllSuperheroes());
            model.addAttribute("organizations", organizations);
            return "editOrganization";
        }        
        service.updateOrganization(organizations);        
        return "redirect:/organizations";
    }
} 