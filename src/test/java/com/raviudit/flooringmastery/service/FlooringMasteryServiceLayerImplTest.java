/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

import com.raviudit.flooringmastery.dao.FlooringMasteryDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author raviu
 */
public class FlooringMasteryServiceLayerImplTest {
    
    private FlooringMasteryServiceLayer service; 
    
    public FlooringMasteryServiceLayerImplTest() {
        FlooringMasteryDAO dao = new FlooringMasteryDAOStubImpl();
        service = new FlooringMasteryServiceLayerImpl(dao);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

//    @Test
//    public void testSomeMethod() {
//        fail("The test case is a prototype.");
//    }
    
}
