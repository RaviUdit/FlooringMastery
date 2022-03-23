/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

import com.raviudit.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

//    @Test
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
        
        //Order retrievedOrder = testDao.getOrder("testOrders/testOrders.txt", 1);
        Order retrievedOrder = testDao.getAllOrdersOnDate("testOrders/testOrders.txt").get(testDao.getAllOrdersOnDate("testOrders/testOrders.txt").size()-1);
        
        assertEquals(testOrder.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking Order Number");
    }
    
//    @Test
    public void testAddGetAllOrders() throws Exception{
        
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
        
        testDao.addOrder("testOrders/testOrders2.txt", testOrder);
        
        Order testOrder2 = new Order(2);
        testOrder2.setCustomerName("Test Customer 2");
        testOrder2.setState("RD");
        testOrder2.setTaxRate(taxRate);
        testOrder2.setProductType("Teak");
        testOrder2.setArea(area.add(area));
        testOrder2.setCostPerSquareFoot(cpsFoot.add(area));
        testOrder2.setLaborCostPerSquareFoot(lcpsFoot.add(area));
        testOrder2.setMaterialCost(matCost.add(area));
        testOrder2.setLaborCost(labCost.add(area));
        testOrder2.setTax(tax.add(area));
        testOrder2.setTotal(total.add(area));
        
        testDao.addOrder("testOrders/testOrders2.txt", testOrder2);
        
        List<Order> allOrders = testDao.getAllOrdersOnDate("testOrders/testOrders2.txt");
        
        assertNotNull(allOrders, "The list may not be null.");
        assertEquals(2, allOrders.size(), "List should have 2 elements.");
        
        assertTrue(allOrders.contains(testOrder), "The list contains testOrder.");
        assertTrue(allOrders.contains(testOrder2), "The list contains testOrder2.");
        
    }
    
//    @Test
    public void testRemoveOrder() throws Exception{
        
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
        
        testDao.addOrder("testOrders/testOrders3.txt", testOrder);
        
        Order testOrder2 = new Order(2);
        testOrder2.setCustomerName("Test Customer 2");
        testOrder2.setState("RD");
        testOrder2.setTaxRate(taxRate);
        testOrder2.setProductType("Teak");
        testOrder2.setArea(area.add(area));
        testOrder2.setCostPerSquareFoot(cpsFoot.add(area));
        testOrder2.setLaborCostPerSquareFoot(lcpsFoot.add(area));
        testOrder2.setMaterialCost(matCost.add(area));
        testOrder2.setLaborCost(labCost.add(area));
        testOrder2.setTax(tax.add(area));
        testOrder2.setTotal(total.add(area));
        
        testDao.addOrder("testOrders/testOrders3.txt", testOrder2);
        
        Order removedOrder = testDao.removeOrder("testOrders/testOrders3.txt", testOrder.getOrderNumber());
        
        assertEquals(removedOrder, testOrder, "The first order should be removed.");
        
        List<Order> allOrders = testDao.getAllOrdersOnDate("testOrders/testOrders3.txt");
        
        assertNotNull(allOrders, "The list may not be null.");
        assertEquals(1, allOrders.size(), "List should have 1 element.");
    }
    
    
}
