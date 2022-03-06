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

 /*
** Name: UserIO
** Type: interface
*/
public interface UserIO {
    
    void print (String msg);
    
    String readString(String prompt);
    
    double readDouble(String promtp);
    
    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
 
}
