/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

/**
 *
 * @author raviu
 */
public class FlooringMasteryFilePersistanceException extends Exception{
    
    public FlooringMasteryFilePersistanceException(String message){
        super(message);
    }
    
    public FlooringMasteryFilePersistanceException(String message, Throwable cause){
        super(message, cause);
    }
}
