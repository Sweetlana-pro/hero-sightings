/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

/**
 *
 * @Sweetlana Protsenko
 * email: svitprotsenko@gmail.com
 */
public class DuplicateNameExistsExeption extends Exception {

    DuplicateNameExistsExeption(String message) {
        super (message);
    }
    DuplicateNameExistsExeption(String message, Throwable cause) {
        super (message, cause);
    }
   
}
