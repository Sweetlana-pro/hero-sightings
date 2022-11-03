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
import com.sg.superherosighting.entities.Superpower;
import com.sg.superherosighting.service.DuplicateNameExistsExeption;
import com.sg.superherosighting.service.SuperheroServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import static org.apache.catalina.startup.ExpandWar.validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Sweetlana Protsenko
 * email: svitprotsenko@gmail.com
 */
@Controller
public class LocationController {
    @Autowired 
    SuperheroServiceImpl service;  
           
    @GetMapping("locations")
    public String displayLocations(@ModelAttribute("location") Location location,Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(@Valid @ModelAttribute("location")Location location,BindingResult result,HttpServletRequest request) {
        try{
            service.validateLocation(location);
        }catch(DuplicateNameExistsExeption ex){
            FieldError error = new FieldError("location", "locationName", ex.getMessage());
            result.addError(error);            
        }
        if(result.hasErrors()) {
        return "locations";
    }      
       
        service.addLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteLocation(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = service.getLocationById(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result, HttpServletRequest request) {
        
        if(result.hasErrors()) {
        return "editLocation";
    }
        service.updateLocation(location);

        return "redirect:/locations";
    }
}
   
