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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        
        String orderString = compileDate(month, day, year);
        
        return dao.getAllOrdersOnDate(orderString);
    }
    
    @Override
    public Order addOrder(String month, String day, String year,
                          String customerName, String stateName, String productType, String area) throws FlooringMasteryFilePersistanceException{
        
        String orderString = compileDate(month, day, year);
        
        Taxes orderTaxes = getTaxesByState(stateName);
        Product orderProduct = getProductByName(productType);
        
        BigDecimal orderArea = new BigDecimal(area);
        BigDecimal materialCost = orderProduct.getCostPerSquareFoot().multiply(orderArea);
        BigDecimal laborCost = orderProduct.getLaborCostPerSquareFoot().multiply(orderArea);
        BigDecimal costBeforeTax = materialCost.add(laborCost);
        
        BigDecimal taxDivisor = new BigDecimal("100");
        BigDecimal orderTaxRate = orderTaxes.getTaxRate().divide( taxDivisor, 2, RoundingMode.HALF_UP);
        BigDecimal orderTax = costBeforeTax.multiply(orderTaxRate);
        
        BigDecimal orderTotal = costBeforeTax.add(orderTax);
        
        Order newOrder = new Order(1);
        newOrder.setCustomerName(customerName);
        newOrder.setState(stateName);
        newOrder.setTaxRate(orderTaxes.getTaxRate());
        newOrder.setProductType(productType);
        newOrder.setArea(orderArea);
        newOrder.setCostPerSquareFoot(orderProduct.getCostPerSquareFoot());
        newOrder.setLaborCostPerSquareFoot(orderProduct.getLaborCostPerSquareFoot());
        newOrder.setMaterialCost(materialCost);
        newOrder.setLaborCost(laborCost);
        newOrder.setTax(orderTax.setScale(2, RoundingMode.HALF_UP));
        newOrder.setTotal(orderTotal.setScale(2, RoundingMode.HALF_UP));
        
        dao.addOrder(orderString, newOrder);
        
        return newOrder;
    }
 
    @Override
    public Order editOrder(String month, String day, String year, String customerName, String stateName, String productType, String area) throws FlooringMasteryFilePersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Order getOrder(String month, String day, String year, int orderNumber)throws FlooringMasteryFilePersistanceException {
        
        String orderString = compileDate(month, day, year);       
        return dao.getOrder(orderString, orderNumber);   
    }

    

    @Override
    public Order removeOrder(String month, String day, String year,  int orderNumber)throws FlooringMasteryFilePersistanceException{
        
        String orderString = compileDate(month, day, year);
        
        return dao.removeOrder(orderString, orderNumber);
        
    }
    
    private String compileDate(String month, String day, String year){
        
        String orderDate = "Orders/Orders_" + month + day + year + ".txt";
        
        return orderDate;
    }


}