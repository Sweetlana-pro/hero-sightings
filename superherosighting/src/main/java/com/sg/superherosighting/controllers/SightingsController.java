/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationsDao;
import com.sg.superherosighting.dao.SightingsDao;
import com.sg.superherosighting.dao.SuperheroDao;
import com.sg.superherosighting.dao.SuperpowerDao;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
import com.sg.superherosighting.service.SuperheroServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @Sweetlana Protsenko
 */
@Controller
public class SightingsController {
    @Autowired
    SuperheroServiceImpl service;
    
Set<ConstraintViolation<Sightings>> violations = new HashSet<>();
 

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sightings> allSightings = service.getAllSightings();
        List<Superhero> superheroes = service.getAllSuperheroes();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("sightings", allSightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request){
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");
       
        String sightDate = request.getParameter("sightDate");
         
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(sightDate, formatter);    
        sightDate = date.format(formatter);
             
        String sightDescription = request.getParameter("sightDescription");
        
        Sightings sighting = new Sightings();
        sighting.setSuperhero(service.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(service.getLocationById(Integer.parseInt(locationId)));
      
        sighting.setSightDate(date);
        
        sighting.setSightDescription(sightDescription);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {
        
        service.addSighting(sighting);            
        }
        return "redirect:/sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        service.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sightings sightings = service.getSightingById(id);
        model.addAttribute("sightings", sightings);
        return "sightingDetail";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sightings sightings = service.getSightingById(id);
        
        List<Superhero> superheroes = service.getAllSuperheroes();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "editSighting";
        
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        String id = request.getParameter("id");
       
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");
        
        String sightDate = request.getParameter("sightDate");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(sightDate, formatter);    
        sightDate = date.format(formatter);
        String sightDescription = request.getParameter("sightDescription");
        
        Sightings sighting = new Sightings();
        sighting.setId(Integer.parseInt(id));
        sighting.setSuperhero(service.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(service.getLocationById(Integer.parseInt(locationId)));
        sighting.setSightDate(date);
        sighting.setSightDescription(sightDescription);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {
        service.updateSighting(sighting);
        }
        return "redirect:/sightings";
    }
    
//    @GetMapping("sightingsByDate")
//    public String sightingsByDate(HttpServletRequest request, Model model){
//        String sightDate = request.getParameter("sightDate");
//        List<Sightings> sightings = service.getSightingsByDate(LocalDate.parse(sightDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        return "sightings";
//    }
}    

