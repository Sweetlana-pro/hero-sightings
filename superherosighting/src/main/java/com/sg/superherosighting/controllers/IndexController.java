/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.entities.Sightings;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.service.SuperheroServiceImpl;
import java.util.Base64;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @Sweetlana Protsenko
 */
@Controller
public class IndexController {
    
    @Autowired 
    SuperheroServiceImpl service;  
    
    @GetMapping("/")
    public String getIndexPage(Model model) {
        
        List<Sightings> recentSightings = service.getAllSightings();
        int size = recentSightings.size() >= 10 ? 10 : recentSightings.size();
        recentSightings = recentSightings.subList(0, size-1);
            
        model.addAttribute("sightings", recentSightings);
       
        return "index";
    }
    
//    This code displays 1 image for all superheroes@GetMapping("/")
//    public String getIndexPage(Model model) {
//        
//        List<Sightings> recentSightings = service.getAllSightings();
//        int size = recentSightings.size() >= 10 ? 10 : recentSightings.size();
//        recentSightings = recentSightings.subList(0, size);
//        //Superhero superhero = service.getSuperheroById(id);
//        byte[] superheroImage = service.getSuperheroById(size).getSuperheroImage();
//        String superheroImageData = Base64.getMimeEncoder().encodeToString(superheroImage);
//        
//    
//        model.addAttribute("sightings", recentSightings);
//        model.addAttribute("superhero", service.getSuperheroById(size));
//        model.addAttribute("superheroImage", superheroImageData);
//        return "index";
//    }
    
}
