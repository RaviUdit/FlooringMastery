/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.model;

import java.math.BigDecimal;

/**
 *
 * @author raviu
 */
/*****
 * Type: Object Definition: Taxes
 * 
 * 
 */
public class Taxes {
    
    private String stateAbbr;
    private String stateName;
    private BigDecimal taxRate; 
    
    // CONSTRUCTOR
    public Taxes(String stateAbbr){
        this.stateAbbr = stateAbbr;
    }
    
    
    // SETTERS AND GETTERS
    public String getStateAbbr() {
        return stateAbbr;
    }

    // public void setStateAbbr(String stateAbbr) {
    //     this.stateAbbr = stateAbbr;
    // }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
    
}
