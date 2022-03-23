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
import com.raviudit.flooringmastery.service.FlooringMasteryAreaIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryDateIsNotInTheFutureException;
import com.raviudit.flooringmastery.service.FlooringMasteryDayIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryFieldIsBlankException;
import com.raviudit.flooringmastery.service.FlooringMasteryMonthIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryNameIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryOrderNumberIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryProductDoesNotExistException;
import com.raviudit.flooringmastery.service.FlooringMasteryServiceLayer;
import com.raviudit.flooringmastery.service.FlooringMasteryStateCodeDoesNotExistException;
import com.raviudit.flooringmastery.service.FlooringMasteryYearIsNotValidException;
import com.raviudit.flooringmastery.view.FlooringMasteryView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        
//        getDate();
        
//        LocalDate testDate = view.getDate();
//        //LocalDate testDate2 = null; 
//        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        //testDate2 = LocalDate.parse(testDate.toString(), formatter);
//        System.out.println(testDate.toString());
//        String month = String.valueOf(testDate.getMonthValue());
//        if(month.length() < 2){
//            month = "0" + month;
//        }
//        String day = String.valueOf(testDate.getDayOfMonth());
//        if(day.length() < 2){
//            day = "0" + day;
//        }
//        String year = String.valueOf(testDate.getYear());
//        
//        System.out.println( month + day + year);
        
        do{
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

                            exportAll();
                            break;
                        case 6:

                            view.displayExitBanner();

                            menuOpen = false; 
                            break;
                        default:
                            unknownCommand();
                    }
                }

            } catch (FlooringMasteryFilePersistanceException | FlooringMasteryFieldIsBlankException | 
                    FlooringMasteryYearIsNotValidException |  FlooringMasteryMonthIsNotValidException |
                    FlooringMasteryDayIsNotValidException | FlooringMasteryOrderNumberIsNotValidException e){

                view.displayErrorMessage(e.getMessage());
            }
        }while(menuOpen);
        
    }
    
    private int getMenuSelection(){
        
        return view.flooringMasteryMenu();
    }
    
    private void displayOrdersOnDate() throws FlooringMasteryFilePersistanceException,
                                              FlooringMasteryFieldIsBlankException,
                                              FlooringMasteryYearIsNotValidException,
                                              FlooringMasteryMonthIsNotValidException,
                                              FlooringMasteryDayIsNotValidException{
        
        String[] date = getDate();
        
//        boolean functionError = false;
//        
//        LocalDate orderDate = view.getDate();
//        orderDate.toString();
//        
//        do{
//            try{
//                
//                date[0] = view.getYear();
//                service.isFieldBlank(date[0]);
//                service.isYearValid(date[0]);
//                
//                functionError = false;
//                
//            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryYearIsNotValidException e){
//                
//                functionError = true; 
//                view.displayErrorMessage(e.getMessage());
//            }
//        } while (functionError);
//        
//        do{
//            try{
//                
//                date[1] = view.getMonth();
//                service.isFieldBlank(date[1]);
//                service.isMonthValid(date[1]);
//                
//                functionError = false;
//                
//            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryMonthIsNotValidException e){
//                
//                functionError = true; 
//                view.displayErrorMessage(e.getMessage());
//            }
//        } while (functionError);
//        
//        do{
//            try{
//                
//                date[2] = view.getDay();
//                service.isFieldBlank(date[2]);
//                service.isDateValid(date[2], date[1], date[0]);
//                //service.isDateValid("01", "06", "2022");
//                
//                functionError = false;
//                
//            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryDayIsNotValidException e){
//                
//                functionError = true; 
//                view.displayErrorMessage(e.getMessage());
//            }
//        } while (functionError);
        
        
        List<Order> orderList = service.getAllOrdersOnDate(date[0], date[1], date[2]);
        view.displayOrdersOnDate(orderList);
        
    }
    
    private void addOrder() throws FlooringMasteryFilePersistanceException,
                                   FlooringMasteryYearIsNotValidException,
                                   FlooringMasteryMonthIsNotValidException,
                                   FlooringMasteryDayIsNotValidException{
        
        boolean functionError = false; 
        
        
        //GET DATE
        String[] date = new String[3];
        
        do{
            try{
                date = getDate();
                service.isAppointmentInTheFuture(date[1], date[0], date[2]);
                
                functionError = false;
            } catch ( FlooringMasteryDateIsNotInTheFutureException e){
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while (functionError);

        
        
        //date[0] = view.getYear();
        //date[1] = view.getMonth();
        //date[2] = view.getDay();
//        
//        do{
//            try{
//                do{
//                    try{
//
//                        date[0] = view.getYear();
//                        service.isFieldBlank(date[0]);
//                        service.isYearValid(date[0]);
//
//                        functionError = false;
//
//                    } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryYearIsNotValidException e){
//
//                        functionError = true; 
//                        view.displayErrorMessage(e.getMessage());
//                    }
//                } while (functionError);
//
//                do{
//                    try{
//
//                        date[1] = view.getMonth();
//                        service.isFieldBlank(date[1]);
//                        service.isMonthValid(date[1]);
//
//                        functionError = false;
//
//                    } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryMonthIsNotValidException e){
//
//                        functionError = true; 
//                        view.displayErrorMessage(e.getMessage());
//                    }
//                } while (functionError);
//
//                do{
//                    try{
//
//                        date[2] = view.getDay();
//                        service.isFieldBlank(date[2]);
//                        service.isDateValid(date[2], date[1], date[0]);
//                        //service.isDateValid("01", "06", "2022");
//
//                        functionError = false;
//
//                    } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryDayIsNotValidException e){
//
//                        functionError = true; 
//                        view.displayErrorMessage(e.getMessage());
//                    }
//                    
//                } while (functionError);
//                
//                service.isAppointmentInTheFuture(date[2], date[1], date[0]);
//            } catch (FlooringMasteryDateIsNotInTheFutureException e){
//                
//                functionError = true; 
//                view.displayErrorMessage(e.getMessage());
//            } 
//        
//        }while (functionError);
        
        String[] orderInfo = new String[4];
        
        //GET CUSTOMER NAME
        do{
            try{
                orderInfo[0] = view.getCustomerName();
                service.isFieldBlank(orderInfo[0]);
                service.isNameValid(orderInfo[0]);  

                functionError = false;
            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryNameIsNotValidException e){
              
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        //GET STATE OF PURCHASE
        List<Taxes> stateList = service.getTaxes();
        view.displayTaxes(stateList);
        
        do{
            try{
                orderInfo[1] = view.getState();
                service.isFieldBlank(orderInfo[1]);
                service.areServicesAvailableThere(orderInfo[1]);
                
                functionError = false; 
            } catch( FlooringMasteryFieldIsBlankException | FlooringMasteryStateCodeDoesNotExistException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        //GET PRODUCT
        List<Product> productList = service.getProducts();
        view.displayProducts(productList);
        
        do{
            try{
                orderInfo[2] = view.getProductType();
                service.isFieldBlank(orderInfo[2]);
                service.isProductAvailable(orderInfo[2]);
                
                functionError = false;
            } catch( FlooringMasteryFieldIsBlankException | FlooringMasteryProductDoesNotExistException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        // GET AREA
        do{
            try{
                orderInfo[3] = view.getArea();
                service.isFieldBlank(orderInfo[3]);
                service.isAreaValid(orderInfo[3]);
                
                functionError = false;
            } catch( FlooringMasteryFieldIsBlankException | FlooringMasteryAreaIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        
        Order newOrder = new Order(1);
        
        newOrder = service.compileOrder(newOrder, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3]);
        
        view.displayOrder(newOrder);
        //service.addOrder(date[1], date[2], date[0], orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3]);
        
        boolean closeFunction = false; 
        
        String confirmOrder = "n";
        confirmOrder = view.confirmationMessage("Would you like to submit this order? (Y/N)");
        
        while (closeFunction == false){
            if (confirmOrder.equalsIgnoreCase("y")){
                
                service.addOrder(date[0], date[1], date[2], newOrder);
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
    
    private void editOrder() throws FlooringMasteryFilePersistanceException, FlooringMasteryYearIsNotValidException,
                                    FlooringMasteryMonthIsNotValidException, FlooringMasteryDayIsNotValidException,
                                    FlooringMasteryOrderNumberIsNotValidException{
        
        boolean functionError = false;
        
        String[] date = getDate();
        
        //date[0] = view.getYear();
        //date[1] = view.getMonth();
        //date[2] = view.getDay();
//        
//        do{
//            try{
//
//                date[0] = view.getYear();
//                service.isFieldBlank(date[0]);
//                service.isYearValid(date[0]);
//
//                functionError = false; 
//            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryYearIsNotValidException e){
//     
//                functionError = true;     
//                view.displayErrorMessage(e.getMessage());
//            }
//        } while (functionError);
//
//                
//        do{
//            try{
//                
//                date[1] = view.getMonth();
//                service.isFieldBlank(date[1]);
//                service.isMonthValid(date[1]);
//                
//                functionError = false;
//            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryMonthIsNotValidException e){
//               
//                functionError = true; 
//                view.displayErrorMessage(e.getMessage());  
//            }
//        } while (functionError);
//
//                
//        do{
//            try{
//        
//                date[2] = view.getDay();        
//                service.isFieldBlank(date[2]);        
//                service.isDateValid(date[2], date[1], date[0]);
//                        //service.isDateValid("01", "06", "2022");         
//                functionError = false;
//            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryDayIsNotValidException e){
//
//                functionError = true; 
//                view.displayErrorMessage(e.getMessage());
//            }        
//        } while (functionError);
        
        
        //GET ORDERNUMBER
        String ordernum = "0";
        
        do{
            try{
                
                ordernum = view.getOrderNumber();
                service.isFieldBlank(ordernum);
                service.isOrderNumberValid(date[1], date[0], date[2], ordernum);
                
                functionError = false;
            } catch(FlooringMasteryFieldIsBlankException | FlooringMasteryOrderNumberIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        int orderNumber = Integer.parseInt(ordernum);
        
        Order workingOrder = service.getOrder(date[0], date[1], date[2], orderNumber);
        
        view.displayOrder(workingOrder);
        
        //GET NAME
        String newName = "0";
        do{
            try{
                newName = view.getNewCustomerName(workingOrder.getCustomerName());
                service.isNameValid(newName);
                
                functionError = false;
            }catch(FlooringMasteryNameIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        
        //GET TAXES
        List<Taxes> stateList = service.getTaxes();
        view.displayTaxes(stateList);
        String newState = "0";
        
        do{
            try{
                newState = view.getNewState(workingOrder.getState());
                
                if(newState.isBlank()){
                    newState = workingOrder.getState();
                }
                service.areServicesAvailableThere(newState);
                
                functionError = false;
            }catch(FlooringMasteryStateCodeDoesNotExistException | FlooringMasteryFilePersistanceException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
            
        }while(functionError);
        
        //GET PRODUCT
        List<Product> productList = service.getProducts();
        view.displayProducts(productList);
        String newProduct = "0";
        
        do {
            try{
                newProduct = view.getNewProductType(workingOrder.getProductType());
                
                if(newProduct.isBlank()){
                    newProduct = workingOrder.getProductType();
                }
                service.isProductAvailable(newProduct);
                
                functionError = false;
            }catch(FlooringMasteryProductDoesNotExistException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        //GET AREA
        String newArea = "0";
        
        do{
            try{
                newArea = view.getNewArea(workingOrder.getArea().toString());
                
                if (newArea.isBlank()){
                    newArea = workingOrder.getArea().toString();
                }
                service.isAreaValid(newArea);
                
                functionError = false;
            } catch (FlooringMasteryAreaIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        workingOrder = service.compileOrder(workingOrder, newName, newState, newProduct, newArea);
        
        view.displayOrder(workingOrder);
        
        boolean closeFunction = false;
        
        String confirmEdit = "n";
        confirmEdit = view.confirmationMessage("Would you like to submit this edited order? (Y/N)");
        
        while (closeFunction == false){
            if (confirmEdit.equalsIgnoreCase("y")){
                
                service.editOrder(date[0], date[1], date[2], workingOrder);
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
        boolean functionError = false;
        
        //GET DATE
        //date[0] = view.getYear();
        //date[1] = view.getMonth();
        //date[2] = view.getDay();
        
        do{
            try{

                date[0] = view.getYear();
                service.isFieldBlank(date[0]);
                service.isYearValid(date[0]);

                functionError = false; 
            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryYearIsNotValidException e){
     
                functionError = true;     
                view.displayErrorMessage(e.getMessage());
            }
        } while (functionError);

                
        do{
            try{
                
                date[1] = view.getMonth();
                service.isFieldBlank(date[1]);
                service.isMonthValid(date[1]);
                
                functionError = false;
            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryMonthIsNotValidException e){
               
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);

                
        do{
            try{
        
                date[2] = view.getDay();        
                service.isFieldBlank(date[2]);        
                service.isDateValid(date[2], date[1], date[0]);
                                 
                functionError = false;
            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryDayIsNotValidException e){

                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }        
        } while (functionError);
        
        
        //GET ORDERNUMBER
        String ordernum = "0";
        
        do{
            try{
                
                ordernum = view.getOrderNumber();
                service.isFieldBlank(ordernum);
                service.isOrderNumberValid(date[2], date[1], date[0], ordernum);
                
                functionError = false;
            } catch(FlooringMasteryFieldIsBlankException | FlooringMasteryOrderNumberIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        int orderNumber = Integer.parseInt(ordernum);
        
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
    
    private void exportAll() throws FlooringMasteryFilePersistanceException{
        
        service.exportOrderData();
        view.confirmationMessage("Order Exported.");
    }
    
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    
    private String[] getDate(){
        
        String date[] = new String[3];
        
        LocalDate testDate = view.getDate();
        //LocalDate testDate2 = null; 
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //testDate2 = LocalDate.parse(testDate.toString(), formatter);
        //System.out.println(testDate.toString());
        String month = String.valueOf(testDate.getMonthValue());
        if(month.length() < 2){
            month = "0" + month;
        }
        String day = String.valueOf(testDate.getDayOfMonth());
        if(day.length() < 2){
            day = "0" + day;
        }
        String year = String.valueOf(testDate.getYear());
        
        date[0] = month;
        date[1] = day;
        date[2] = year;
        
        //System.out.println( date[0] + date[1] + date[2]);
        
        return date;
    }
    
}
