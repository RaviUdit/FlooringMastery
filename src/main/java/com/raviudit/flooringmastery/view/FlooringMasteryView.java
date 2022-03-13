/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.view;

import com.raviudit.flooringmastery.model.Order;
import java.util.List;

/**
 *
 * @author raviu
 */
public class FlooringMasteryView {
    
    private UserIO io; 
    
    public FlooringMasteryView(UserIO io){
        this.io = io; 
    }
    
    //MENUS
    /*
    ** Function Name: flooringMasteryMenu
    ** Return Type: int
    ** Purpose: Displays the main menu and takes the user's input. 
    */  
    
    public int flooringMasteryMenu(){
        
        io.print("*****************************************************");
        io.print("************* << Flooring Program >> ****************");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export all Orders");
        io.print("6. Exit");
        io.print(" ");
        io.print("*****************************************************");
        
        return io.readInt("Please select from the Listed Options", 1, 6);
    }
    
     /*
    ** Function Name: displayOrdersOnDate
    ** Return Type: -
    ** Purpose: Takes in a List of Orders for a predetermined date and displays those orders as text.
    */  
    
    public void displayOrdersOnDate(List<Order> orderList){
        
        for (Order currentOrder: orderList){
            
            String orderInfo = String.format("%s : %s, %s, %s, %s, $%s", 
                    String.valueOf(currentOrder.getOrderNumber()), 
                    currentOrder.getCustomerName(),
                    currentOrder.getState(),
                    currentOrder.getProductType(),
                    currentOrder.getArea().toString(),
                    currentOrder.getTotal().toString());
            
            io.print(orderInfo);
        }
        
        io.readString("Hit Enter To Continue");
    }
    
    public void displayOrder(Order order){
        
        String orderInfo = String.format("%s : %s %s %s %s $%s", 
            String.valueOf(order.getOrderNumber()), 
            order.getCustomerName(),
            order.getState(),
            order.getProductType(),
            order.getArea().toString(),
            order.getTotal().toString());
            
        io.print(orderInfo);
        
        io.readString("Hit Enter To Continue");
    }   
    
    public String getDay(){
        return io.readString("Please enter the Day of the requested order [DD].");
    }
    
    public String getMonth(){
        return io.readString("Please enter the Month of the requested order [MM].");
    }
    
    public String getYear(){
        return io.readString("Please enter the Year of the requested order [YYYY].");
    }
    
    public String getCustomerName(){
        return io.readString("Please enter the name of the customer.");
    }
    
    public String getState(){
        return io.readString("Please enter the State the order will be fufilled in.");
    }
    
    public String getProductType(){
        return io.readString("Please enter the product you would like to purchase.");
    }
    
    public String getArea(){
        return io.readString("Please enter the area(feet squared) that you what to cover. Minimum order size is 100sq. feet.");
    }
    
    public int getOrderNumber(){
        return io.readInt("Please enter the Order Number of the order you wish to remove.");
    }
    
    public String confirmationMessage(String message){
        return io.readString(message);
    }
    
    // BANNERS
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayUnknownCommandBanner(){
        io.print("Unknown Command.");
    }
    
    public void displayExitBanner(){
        io.print("Program Complete.");
    }
}
