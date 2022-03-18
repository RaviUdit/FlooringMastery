/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.controller;

import com.raviudit.flooringmastery.dao.FlooringMasteryFilePersistanceException;
import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import com.raviudit.flooringmastery.service.FlooringMasteryServiceLayer;
import com.raviudit.flooringmastery.view.FlooringMasteryView;
import java.util.List;

/**
 *
 * @author raviu
 */
public class FlooringMasteryController {
    
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;
    
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service){
        this.view = view;
        this.service = service;
    }
    
    public void run(){
        
        boolean menuOpen = true;
        int menuSelection = 0;
        
        try{
            while(menuOpen){
                
                menuSelection = getMenuSelection();
             
                switch(menuSelection){
                    case 1:
                        
                        displayOrdersOnDate();
                        
                        break;
                    case 2:
                        
                        addOrder();
                        break;
                    case 3:
                        
                        editOrder();
                        break;
                    case 4:
                        
                        removeOrder();
                        break;
                    case 5:
                        
                        break;
                    case 6:
                        
                        view.displayExitBanner();
                        
                        menuOpen = false; 
                        break;
                    default:
                        unknownCommand();
                }
            }
        
        } catch (FlooringMasteryFilePersistanceException e){
            
            view.displayErrorMessage(e.getMessage());
        }
        
    }
    
    private int getMenuSelection(){
        
        return view.flooringMasteryMenu();
    }
    
    private void displayOrdersOnDate() throws FlooringMasteryFilePersistanceException {
        
        String[] date = new String[3];
        
        date[0] = view.getYear();
        date[1] = view.getMonth();
        date[2] = view.getDay();
        
        List<Order> orderList = service.getAllOrdersOnDate(date[1], date[2], date[0]);
        view.displayOrdersOnDate(orderList);
        
    }
    
    private void addOrder() throws FlooringMasteryFilePersistanceException{
        
        String[] date = new String[3];
        
        date[0] = view.getYear();
        date[1] = view.getMonth();
        date[2] = view.getDay();
        
        String[] orderInfo = new String[4];
        
        orderInfo[0] = view.getCustomerName();
        
        List<Taxes> stateList = service.getTaxes();
        view.displayTaxes(stateList);
        orderInfo[1] = view.getState();
        
        List<Product> productList = service.getProducts();
        view.displayProducts(productList);
        orderInfo[2] = view.getProductType();
        
        orderInfo[3] = view.getArea();
        
        Order newOrder = new Order(1);
        
        newOrder = service.compileOrder(newOrder, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3]);
        
        view.displayOrder(newOrder);
        //service.addOrder(date[1], date[2], date[0], orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3]);
        
        boolean closeFunction = false; 
        
        String confirmOrder = "n";
        confirmOrder = view.confirmationMessage("Would you like to submit this order? (Y/N)");
        
        while (closeFunction == false){
            if (confirmOrder.equalsIgnoreCase("y")){
                
                service.addOrder(date[1], date[2], date[0], newOrder);
                view.confirmationMessage("Order Submitted");
                closeFunction = true;
            } else if (confirmOrder.equalsIgnoreCase("n")){
                
                view.confirmationMessage("Order Not Submitted");
                closeFunction = true;
            } else {

                view.confirmationMessage("Unspecified option. Please choose Yes or No (Y/N)");
                closeFunction = false;
            }
        }
        
    }
    
    private void editOrder() throws FlooringMasteryFilePersistanceException{
        
        String[] date = new String[3];
        
        date[0] = view.getYear();
        date[1] = view.getMonth();
        date[2] = view.getDay();
        
        int orderNumber = view.getOrderNumber();
        
        Order workingOrder = service.getOrder(date[1], date[2], date[0], orderNumber);
        
        view.displayOrder(workingOrder);
        
        String newName = view.getNewCustomerName(workingOrder.getCustomerName());
        
        List<Taxes> stateList = service.getTaxes();
        view.displayTaxes(stateList);
        String newState = view.getNewState(workingOrder.getState());
        
        List<Product> productList = service.getProducts();
        view.displayProducts(productList);
        String newProduct = view.getNewProductType(workingOrder.getProductType());
        
        String newArea = view.getNewArea(workingOrder.getArea().toString());
        
        workingOrder = service.compileOrder(workingOrder, newName, newState, newProduct, newArea);
        
        view.displayOrder(workingOrder);
        
        boolean closeFunction = false;
        
        String confirmEdit = "n";
        confirmEdit = view.confirmationMessage("Would you like to submit this edited order? (Y/N)");
        
        while (closeFunction == false){
            if (confirmEdit.equalsIgnoreCase("y")){
                
                service.editOrder(date[1], date[2], date[0], workingOrder);
                view.confirmationMessage("Order Edited");
                closeFunction = true;
            } else if (confirmEdit.equalsIgnoreCase("n")){
                
                view.confirmationMessage("Order Not Edited");
                closeFunction = true;
            } else {

                view.confirmationMessage("Unspecified option. Please choose Yes or No (Y/N)");
                closeFunction = false;
            }
        }
    }
    
    private void removeOrder() throws FlooringMasteryFilePersistanceException{
        
        String[] date = new String[3];
        
        date[0] = view.getYear();
        date[1] = view.getMonth();
        date[2] = view.getDay();
        
        int orderNumber = view.getOrderNumber();
        
        view.displayOrder(service.getOrder(date[1], date[2], date[0], orderNumber));
        
        String confirmDeletion = "n";
        
        boolean closeFunction = false;
        
        confirmDeletion = view.confirmationMessage("Would you like to delete this order? (Y/N)");
        
        while (closeFunction == false){
            if(confirmDeletion.equalsIgnoreCase("y")){

                service.removeOrder(date[1], date[2], date[0], orderNumber);
                view.confirmationMessage("Order deleted.");
                closeFunction = true;

            } else if (confirmDeletion.equalsIgnoreCase("n")){

                view.confirmationMessage("Order not deleted.");
                closeFunction = true;
            } else {

                view.confirmationMessage("Unspecified option. Please choose Yes or No (Y/N)");
                closeFunction = false;
            }
        }
        
        
        
    }
    
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    
}
