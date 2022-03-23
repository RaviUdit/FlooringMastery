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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raviu
 */
public class FlooringMasteryDAOStubImpl implements FlooringMasteryDAO{
    
    public Order testOrder;
    public Taxes testTaxes;
    public Product testProduct; 
    
    public FlooringMasteryDAOStubImpl(){
        BigDecimal taxRate = new BigDecimal("5.5");
        BigDecimal area = new BigDecimal("100");
        BigDecimal cpsFoot = new BigDecimal("200");
        BigDecimal lcpsFoot = new BigDecimal("300");
        BigDecimal matCost = new BigDecimal("400");
        BigDecimal labCost = new BigDecimal("500");
        BigDecimal tax = new BigDecimal("600");
        BigDecimal total = new BigDecimal("700");
        
        Order testOrder = new Order(1);
        testOrder.setCustomerName("Test Customer");
        testOrder.setState("GR");
        testOrder.setTaxRate(taxRate);
        testOrder.setProductType("Oak");
        testOrder.setArea(area);
        testOrder.setCostPerSquareFoot(cpsFoot);
        testOrder.setLaborCostPerSquareFoot(lcpsFoot);
        testOrder.setMaterialCost(matCost);
        testOrder.setLaborCost(labCost);
        testOrder.setTax(tax);
        testOrder.setTotal(total);
        
        Taxes testTaxes = new Taxes("CA");
        testTaxes.setStateName("Canada");
        testTaxes.setTaxRate(taxRate);
        
        Product testProduct = new Product("Wood");
        testProduct.setCostPerSquareFoot(cpsFoot);
        testProduct.setLaborCostPerSquareFoot(lcpsFoot);
    }
    
    public FlooringMasteryDAOStubImpl(Order passedOrder, Taxes passedTaxes, Product passedProduct){
        this.testOrder = passedOrder;
        this.testTaxes = passedTaxes;
        this.testProduct = passedProduct;
    }

    @Override
    public List<Taxes> getTaxes() throws FlooringMasteryFilePersistanceException {
        
        List<Taxes> testList = new ArrayList<>();
        testList.add(testTaxes);
        
        return testList;
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryFilePersistanceException {
        
        List<Product> testList = new ArrayList<>();
        testList.add(testProduct);
        
        return testList;
    }

    @Override
    public Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException {
        return testTaxes;
    }

    @Override
    public Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException {
        return testProduct;
    }

    @Override
    public void addOrder(String orderDate, Order order) throws FlooringMasteryFilePersistanceException {
        //do nothing
    }

    @Override
    public void editOrder(String orderDate, Order order) throws FlooringMasteryFilePersistanceException {
        //do nothing
    }

    @Override
    public Order getOrder(String orderDate, int orderNumber) throws FlooringMasteryFilePersistanceException {
        return testOrder;
    }

    @Override
    public List<Order> getAllOrdersOnDate(String orderDate) throws FlooringMasteryFilePersistanceException {
        
        List<Order> orderList = new ArrayList<>();
        orderList.add(testOrder);
        
        return orderList;
    }

    @Override
    public Order removeOrder(String orderDate, int orderNumber) throws FlooringMasteryFilePersistanceException {
        
        if( orderNumber == testOrder.getOrderNumber() ){
            return testOrder;
        } else {
            return null; 
        }
    }

    @Override
    public void exportOrderData() throws FlooringMasteryFilePersistanceException {
        //do nohing
    }
    
}
