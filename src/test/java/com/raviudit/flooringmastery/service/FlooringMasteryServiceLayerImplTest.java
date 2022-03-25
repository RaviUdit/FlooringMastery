/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

import com.raviudit.flooringmastery.dao.FlooringMasteryDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author raviu
 */
public class FlooringMasteryServiceLayerImplTest {
    
    private FlooringMasteryServiceLayer service; 
    
    public FlooringMasteryServiceLayerImplTest() {
//        FlooringMasteryDAO dao = new FlooringMasteryDAOStubImpl();
//        service = new FlooringMasteryServiceLayerImpl(dao);

         ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
         service = ctx.getBean("myService", FlooringMasteryServiceLayer.class);
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
    
    @Test
    public void testCheckIfStateExists() throws Exception{
        
        //Arrange
        
        String testString = "KN";
        try{
            service.areServicesAvailableThere(testString);
            fail(" Expected exception was not thrown");
        } catch(FlooringMasteryStateCodeDoesNotExistException e){
            return;
        }
    }
    
    @Test
    public void testCheckIfProductExists() throws Exception{
        
        //Arrange
        
        String testString = "Tile";
        try{
            service.isProductAvailable(testString);
            fail(" Expected exception was not thrown");
        } catch(FlooringMasteryProductDoesNotExistException e){
            return;
        }
    }
    
    @Test
    public void testCheckIfFieldIsBlank(){
      
        String testString = "";
        try{
            service.isFieldBlank(testString);
            fail(" Expected exception was not thrown");
        } catch(FlooringMasteryFieldIsBlankException e){
            return;
        }
    }
    
    @Test
    public void testCheckIfAreaIsValidLowNumber() throws Exception{
        
        String testString = "50";
        try{
            service.isAreaValid(testString);
            fail(" Expected exception was not thrown");
        } catch(FlooringMasteryAreaIsNotValidException e){
            return;
        }
    }
    
    @Test
    public void testCheckIfAreaIsNonNumber() throws Exception{
        
        String testString = "q";
        try{
            service.isAreaValid(testString);
            fail(" Expected exception was not thrown");
        } catch(FlooringMasteryAreaIsNotValidException e){
            return;
        }
    }
    
    @Test
    public void testCheckIfNameIsValid() throws Exception{
        
        String testString = "[]";
        try{
            service.isNameValid(testString);
            fail(" Expected exception was not thrown");
        }catch(FlooringMasteryNameIsNotValidException e){
            return;
        }
    }
    
    @Test
    public void testCheckIfDateIsInTheFuture() throws Exception{
        
//        String testStringDay = "01";
//        String testStringMonth = "01";
//        String testStringYear = "1995";
        
        String testDate = "01/01/1985";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        LocalDate errorDate = LocalDate.parse(testDate, formatter);
        try{
            service.isAppointmentInTheFuture(errorDate);
            fail(" Expected exception was not thrown");
        }catch(FlooringMasteryDateIsNotInTheFutureException e){
            return;
        }
    }
    
    @Test
    public void testCheckIsOrderNumberValidInvalidDate() throws Exception{
        

        String testDate = "01/01/1985";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        LocalDate errorDate = LocalDate.parse(testDate, formatter);
        
        String testOrderNumber = "10";
        try{
            service.isOrderNumberValid(errorDate, testOrderNumber);
            fail(" Expected exception was not thrown");
        }catch(FlooringMasteryOrderNumberIsNotValidException e){
            return;
        }
    }
    
        @Test
    public void testCheckIsOrderNumberValidInvalidnumber() throws Exception{
        

        String testDate = "06/01/1985";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        LocalDate errorDate = LocalDate.parse(testDate, formatter);
        
        String testOrderNumber = "10";
        try{
            service.isOrderNumberValid(errorDate, testOrderNumber);
            fail(" Expected exception was not thrown");
        }catch(FlooringMasteryOrderNumberIsNotValidException e){
            return;
        }
    }
}
