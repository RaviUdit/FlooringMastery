/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

/**
 *
 * @author raviu
 */
public class FlooringMasteryFieldIsBlankException extends Exception{
    
    public FlooringMasteryFieldIsBlankException(String message){
        super(message);
    }
    
    public FlooringMasteryFieldIsBlankException(String message, Throwable cause){
        super(message, cause);
    }
    
}
