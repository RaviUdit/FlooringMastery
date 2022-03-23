/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.view;

/**
 *
 * @author raviu
 */

/**
 *
 * @author raviu
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class UserIOConsoleImpl implements UserIO{
    
      
    // Scanner used to capture user input. 
    final private Scanner userIOScanner = new Scanner(System.in);
    
    
    /*
    ** Function Name: print
    ** Return Type: void
    ** Parameters: msg
    ** Purpose: Prints out an inputed message to console. 
    **          
    */
    @Override 
    public void print(String msg){
        System.out.println(msg);
    }
    
    /*
    ** Function Name: readString
    ** Return Type: String
    ** Parameters: msgPrompt
    ** Purpose: Prints out an inputted message to console and waits for the user
    **          to input a responce.
    **          
    */
    @Override
    public String readString(String msgPrompt){
        System.out.println(msgPrompt);
        return userIOScanner.nextLine();
    }
    
    /*
    ** Function Name: readInt
    ** Return Type: int
    ** Parameters: msgPrompt
    ** Purpose: Prints out a Invalid Entry message if the user inputted responce 
    **          to readLong(msgPrompt, min, max) is not within acceptable 
    **          boundaries. 
    */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(msgPrompt);
                num = Integer.parseInt(stringValue); 
                invalidInput = false; 
            } catch (NumberFormatException e) {
                this.print("Invalid entry. Try again.");
            }
        }
        return num;
    }
    
    /*
    ** Function Name: readInt
    ** Return Type: int
    ** Parameters: msgPrompt, min, mix
    ** Purpose: Returns the inputted number unless that number is outside the 
    **          specified range.
    */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }
    
    /*
    ** Function Name: readLong
    ** Return Type: long
    ** Parameters: msgPrompt
    ** Purpose: Prints out a Invalid Entry message if the user inputted responce 
    **          to readLong(msgPrompt, min, max) is not within acceptable 
    **          boundaries. 
    */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Invalid entry. Try again.");
            }
        }
    }
    
    /*
    ** Function Name: readLong
    ** Return Type: long
    ** Parameters: msgPrompt, min, mix
    ** Purpose: Returns the inputted number unless that number is outside the 
    **          specified range.
    */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }
    
    /*
    ** Function Name: readFloat
    ** Return Type: float
    ** Parameters: msgPrompt
    ** Purpose: Prints out a Invalid Entry message if the user inputted responce 
    **          to readFloat(msgPrompt, min, max) is not within acceptable 
    **          boundaries. 
    */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Invalid entry. Try again.");
            }
        }
    }
    
    /*
    ** Function Name: readFloat
    ** Return Type: float
    ** Parameters: msgPrompt, min, mix
    ** Purpose: Returns the inputted number unless that number is outside the 
    **          specified range.
    */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }
    
    /*
    ** Function Name: readDouble
    ** Return Type: double
    ** Parameters: msgPrompt
    ** Purpose: Prints out a Invalid Entry message if the user inputted responce 
    **          to readDouble(msgPrompt, min, max) is not within acceptable 
    **          boundaries. 
    */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Invalid entry. Try again.");
            }
        }
    }
    
    /*
    ** Function Name: readDouble
    ** Return Type: double
    ** Parameters: msgPrompt, min, mix
    ** Purpose: Returns the inputted number unless that number is outside the 
    **          specified range.
    */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

//    public LocalDate readLocalDate(String prompt) {
//        boolean invalidInput = true;
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        LocalDate date = null;
//        while (invalidInput) {
//            try {
//                String inputValue = this.readString(prompt);
//                date = LocalDate.parse(inputValue, dateTimeFormatter);
//                invalidInput = false;
//            } catch (DateTimeParseException ex) {
//                this.print("Please Enter Valid Date: " + ex.getMessage());
//            }
//        }
//        //While loop will keep going until a valid date is inputted as mm/dd/yyyy
//        return date;
//    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        
        boolean validInput = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = null;
        
        do{
            try{
                String input = this.readString(prompt);
                date = LocalDate.parse(input, formatter);
                validInput = true; 
            } catch (DateTimeParseException x) {
             
                this.print("We require a valid date: " + x.getMessage() );
                validInput = false;
            }
        }while (!validInput);
        
        return date;
    }
    
}