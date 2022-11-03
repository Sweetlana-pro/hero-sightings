/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.superherosighting.controllers;

import com.sg.superherosighting.entities.Superhero;
import com.sg.superherosighting.entities.Superpower;
import com.sg.superherosighting.service.DuplicateNameExistsExeption;
import com.sg.superherosighting.service.SuperheroServiceImpl;
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

/**
 *
 * @Sweetlana Protsenko 
 */
@Controller
public class SuperpowerController {
    
    @Autowired 
    SuperheroServiceImpl service;
        
    @GetMapping("superpowers")
    public String displaySuperpowers(@ModelAttribute("superpower") Superpower superpower,Model model) {
        
        List<Superpower> superpowers = service.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        
        return "superpowers";
        
    }
    @PostMapping("addSuperpower")
    public String addSuperpower(@Valid @ModelAttribute("superpower")Superpower superpower,BindingResult result,Model model) throws DuplicateNameExistsExeption {
        try{
            service.validateSuperpower(superpower);
        }catch(DuplicateNameExistsExeption ex){
            FieldError error = new FieldError("superpower", "powerName", ex.getMessage());
            result.addError(error);
        }
        if (result.hasErrors()) {            
            return "superpowers";
        }      
        
        service.addSuperpower(superpower);       
        return "redirect:/superpowers";
    }
    
        
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteSuperpowerById(id);
        
        return "redirect:/superpowers";
    }
    
    @GetMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model) throws DuplicateNameExistsExeption {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = service.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
          
        
        return "editSuperpower";
    }
    
    @PostMapping("editSuperpower")
    public String updateSuperpower(@Valid Superpower superpower, BindingResult result, Model model) throws DuplicateNameExistsExeption{

        if (result.hasErrors()) {
            
            return "editSuperpower";
        }
        service.updateSuperpower(superpower);
        return "redirect:/superpowers";
    }
}
