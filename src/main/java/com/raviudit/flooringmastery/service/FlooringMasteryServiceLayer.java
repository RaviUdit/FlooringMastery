/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

import com.raviudit.flooringmastery.dao.FlooringMasteryFilePersistanceException;
import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface FlooringMasteryServiceLayer {
    
    Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException;
    
    Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException;
    
    Order addOrder(String month, String day, String year, String customerName, String stateName, String productType, String area)
                                                 throws FlooringMasteryFilePersistanceException;
    
    Order editOrder(String month, String day, String year, String customerName, String stateName, String productType, String area)
                                                 throws FlooringMasteryFilePersistanceException;
    
    Order getOrder(String month, String day, String year, int orderNumber)throws FlooringMasteryFilePersistanceException;
    
    List<Order> getAllOrdersOnDate(String month, String day, String year) throws FlooringMasteryFilePersistanceException;
    
    Order removeOrder(String month, String day, String year, int orderNumber)throws FlooringMasteryFilePersistanceException;
    
}
