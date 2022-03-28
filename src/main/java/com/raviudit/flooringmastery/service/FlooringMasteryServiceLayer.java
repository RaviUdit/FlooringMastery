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
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author raviu
 */

/*****
 * Type: Interface
 * 
 * 
 */
public interface FlooringMasteryServiceLayer {
    
    List<Taxes> getTaxes() throws FlooringMasteryFilePersistanceException;
    
    List<Product> getProducts() throws FlooringMasteryFilePersistanceException;
    
    Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException;
    
    Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException;
    
    Order addOrder(LocalDate orderDate, Order newOrder)
                                                 throws FlooringMasteryFilePersistanceException;
    
    Order compileOrder(Order editOrder, String customerName, String stateName, String productType, String area) throws FlooringMasteryFilePersistanceException;
    
    void editOrder(LocalDate orderDate, Order editedOrder)
                                                 throws FlooringMasteryFilePersistanceException;
    
    Order getOrder(LocalDate orderDate, int orderNumber)throws FlooringMasteryFilePersistanceException;
    
    List<Order> getAllOrdersOnDate(LocalDate ld) throws FlooringMasteryFilePersistanceException;
    
    Order removeOrder(LocalDate orderDate, int orderNumber)throws FlooringMasteryFilePersistanceException;
    
    void exportOrderData() throws FlooringMasteryFilePersistanceException;
    
    // Exceptions
    
    void areServicesAvailableThere(String stateCode) throws FlooringMasteryStateCodeDoesNotExistException, 
                                                            FlooringMasteryFilePersistanceException;
    
    void isProductAvailable(String product) throws FlooringMasteryProductDoesNotExistException,
                                                   FlooringMasteryFilePersistanceException;
    
    void isFieldBlank(String field) throws FlooringMasteryFieldIsBlankException;
    
    void isAreaValid(String area) throws FlooringMasteryAreaIsNotValidException;
    
    void isNameValid(String name) throws FlooringMasteryNameIsNotValidException;
  
    void isAppointmentInTheFuture(LocalDate orderDate) throws FlooringMasteryDateIsNotInTheFutureException;
    
    void isOrderNumberValid(LocalDate orderDate, String orderNumber) throws FlooringMasteryOrderNumberIsNotValidException,
                                                                            FlooringMasteryFilePersistanceException;
      
//    void isMonthValid(String name) throws FlooringMasteryMonthIsNotValidException;
//    
//    void isDateValid(String day, String month, String year) throws FlooringMasteryDayIsNotValidException;
//    
//    void isYearValid(String year) throws FlooringMasteryYearIsNotValidException;  
}
