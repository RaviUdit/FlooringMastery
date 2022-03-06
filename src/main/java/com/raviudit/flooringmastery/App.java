/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery;

import com.raviudit.flooringmastery.controller.FlooringMasteryController;
import com.raviudit.flooringmastery.dao.FlooringMasteryDAO;
import com.raviudit.flooringmastery.dao.FlooringMasteryDAOFileImpl;
import com.raviudit.flooringmastery.service.FlooringMasteryServiceLayer;
import com.raviudit.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.raviudit.flooringmastery.view.FlooringMasteryView;
import com.raviudit.flooringmastery.view.UserIO;
import com.raviudit.flooringmastery.view.UserIOConsoleImpl;

/**
 *
 * @author raviu
 */
public class App {
        
    /*
    ** Function Name: main
    ** Return Type: Void
    ** Purpose: Creates an instance of the classes necessary to run the program 
    **          and feeds them into the approppriate method in order to run the
    **          program. 
    */
        
      
    public static void main(String[] args) {
      
        UserIO myIO = new UserIOConsoleImpl();
        FlooringMasteryView myView = new FlooringMasteryView(myIO);
        
        FlooringMasteryDAO myDAO = new FlooringMasteryDAOFileImpl();
        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerImpl(myDAO);
        
        FlooringMasteryController controller = new FlooringMasteryController(myView, myService);
        controller.run();
        
    }
    
}
