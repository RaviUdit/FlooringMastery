/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

import com.raviudit.flooringmastery.model.Order;
import java.io.FileWriter;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author raviu
 */
public class FlooringMasteryDAOFileImplTest {
    
    FlooringMasteryDAO testDao;
    
    public FlooringMasteryDAOFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFolder = "testOrders";
        String taxesLocation = "Data/Test/Taxes.txt";
        String productLocation = "Data/Test/Products.txt";
        String exportLocation = "Backup/Test/DataExport.txt";
        
        //new FileWriter("testOrders/testOrders.txt");
        testDao = new FlooringMasteryDAOFileImpl(testFolder, taxesLocation, productLocation, exportLocation);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
//    public void testSomeMethod() {
//        fail("The test case is a prototype.");
//    }
    
    public void testAddGetOrder() throws Exception{
        
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
        
        
        
        testDao.addOrder("testOrders/testOrders.txt", testOrder);
        
        Order retrievedOrder = testDao.getOrder("testOrders/testOrders.txt", 1);
        
        assertEquals(testOrder.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking Order Number");
    }
    
    //Tests to complete
    //addOrder
    //getOrder
    //getAllOrdersOnDate
    //removeOrder
    
}
