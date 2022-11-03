/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.entities.Organizations;
import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
import com.sg.superherosighting.service.DuplicateNameExistsExeption;
import com.sg.superherosighting.service.SuperheroServiceImpl;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sweetlana Protsenko
 * email: svitprotsenko@gmail.com
 */
@Controller
public class SuperheroController {
    @Autowired 
    SuperheroServiceImpl service;
    
    @GetMapping("superheroes")
    public String displaySuperheroes(@ModelAttribute("superhero") Superhero superhero,Model model) {
        
        List<Superhero> superheroes = service.getAllSuperheroes();
        List<Superpower> superpowers = service.getAllSuperpowers();
        List<Organizations> allOrganizations = service.getAllOrganizations();
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", allOrganizations);
        return "superheroes";
    }
    
    @PostMapping("addSuperhero")
    public String addSuperhero(@Valid @ModelAttribute("superhero") Superhero superhero,BindingResult result,
            HttpServletRequest request,@RequestParam("superheroSaveImage")MultipartFile file,
            Model model) {
        try{
            superhero.setSuperheroImage(file.getBytes());
        }catch(IOException ex){
            FieldError error = new FieldError("superhero", "superheroImage", ex.getMessage());
            result.addError(error);
        }
        try{
            service.validateSuperhero(superhero);
        }catch(DuplicateNameExistsExeption ex){
            FieldError error = new FieldError("superhero", "superheroName", ex.getMessage());
            result.addError(error);            
        }            
        String superpowerId = request.getParameter("superpowerId");       
        superhero.setSuperpower(service.getSuperpowerById(Integer.parseInt(superpowerId)));        
        
        if (result.hasErrors()) {
            model.addAttribute("superpowers", service.getAllSuperpowers());
            model.addAttribute("superhero", superhero);
            
            return "superheroes";
        }        
        
        service.addSuperhero(superhero);                
        return "redirect:/superheroes";      
    }

    @GetMapping("superheroDetail")
    public String superheroDetail(Integer id, Model model) {
        Superhero superhero = service.getSuperheroById(id);
        byte[] superheroImage = superhero.getSuperheroImage();
        String superheroImageData = null;
        if(superheroImage != null){
            superheroImageData = Base64.getMimeEncoder().encodeToString(superheroImage);
        }
        
        model.addAttribute("superhero", superhero);
        model.addAttribute("superheroImage", superheroImageData);
        model.addAttribute("organizations", service.getAllOrganizations());
        return "superheroDetail";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        service.deleteSuperheroById(id);
        return "redirect:/superheroes";
    }

    @GetMapping("editSuperheroes")
    public String editSuperhero(Integer id, Model model) {
          
        Superhero superhero = service.getSuperheroById(id);   
        
        List<Superpower> superpowers = service.getAllSuperpowers();
        
        byte[] superheroImage = superhero.getSuperheroImage();
        String superheroImageData = null;
        if(superheroImage != null){
            superheroImageData = Base64.getMimeEncoder().encodeToString(superheroImage);
        }
        
        model.addAttribute("superhero", superhero);        
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("superheroImage", superheroImageData);
        return "editSuperheroes";
    }
    
    @PostMapping("editSuperheroes")
    public String performEditSuperhero(@Valid Superhero superhero, BindingResult result, HttpServletRequest request, @RequestParam("superheroSaveImage")MultipartFile file, Model model) {
        try{
            superhero.setSuperheroImage(file.getBytes());
        }catch(IOException ex){
            FieldError error = new FieldError("superhero", "superheroImage", ex.getMessage());
            result.addError(error);
        }
                    
        String superpowerId = request.getParameter("superpowerId");       
        superhero.setSuperpower(service.getSuperpowerById(Integer.parseInt(superpowerId)));        
        
        if (result.hasErrors()) {
            model.addAttribute("superpowers", service.getAllSuperpowers());
            model.addAttribute("superhero", superhero);
            
            return "editSuperheroes";
        }          
        service.updateSuperhero(superhero);                
        return "redirect:/superheroes";   
    }  
}
    
