/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface FlooringMasteryDAO {
    
    
    Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException;
    
    Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException;
    
    Order addOrder(String orderDate, Order order)  throws FlooringMasteryFilePersistanceException;
    
    Order getOrder(String orderDate);
    
    List<Order> getAllOrdersOnDate(String orderDate) throws FlooringMasteryFilePersistanceException;
    
    Order removeOrder(String orderDate);
    
    
}
