/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

import com.raviudit.flooringmastery.dao.FlooringMasteryDAO;
import com.raviudit.flooringmastery.dao.FlooringMasteryFilePersistanceException;
import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.util.List;

/**
 *
 * @author raviu
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{
    
    FlooringMasteryDAO dao;
    
    public FlooringMasteryServiceLayerImpl(FlooringMasteryDAO dao){
        this.dao = dao;
    }

    @Override
    public Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException {
        return dao.getTaxesByState(stateAbbr);
    }

    @Override
    public Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException {
        return dao.getProductByName(productName);
    }

    @Override
    public List<Order> getAllOrdersOnDate(String month, String day, String year) throws FlooringMasteryFilePersistanceException {
        return dao.getAllOrdersOnDate(month, day, year);
    }
    
    @Override
    public Order addOrder(String orderDate, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order getOrder(String orderDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public Order removeOrder(String orderDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
