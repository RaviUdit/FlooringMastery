/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

import com.raviudit.flooringmastery.dao.FlooringMasteryDAO;
import com.raviudit.flooringmastery.dao.FlooringMasteryFilePersistanceException;
import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author raviu
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{
    
    FlooringMasteryDAO dao;
    
    public FlooringMasteryServiceLayerImpl(FlooringMasteryDAO dao){
        this.dao = dao;
    }

    //PASSTHROUGH FUNCTIONS
    @Override
    public List<Taxes> getTaxes() throws FlooringMasteryFilePersistanceException {
        
        return dao.getTaxes();
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryFilePersistanceException {
        return dao.getProducts();
    }

    @Override
    public Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException {
        return dao.getTaxesByState(stateAbbr);
    }

    @Override
    public Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException {
        return dao.getProductByName(productName);
    }

    /*
    ** Function Name: getAllOrdersOnDate()
    ** Return Type: List<Order>
    ** Purpose: Passes a series of Strings representing the date to the 
                respective DAO function and returns a List of Order objects.
    */ 
    @Override
    public List<Order> getAllOrdersOnDate(LocalDate orderDate) throws FlooringMasteryFilePersistanceException {
        
        String[] dateString = getDate(orderDate);
        String orderString = compileDate(dateString[0], dateString[1], dateString[2]);
        
        return dao.getAllOrdersOnDate(orderString);
    }
 
    /*
    ** Function Name: addOrder()
    ** Return Type: Order
    ** Purpose: Passes a series of Strings representing the date, and an Order object 
                to the respective DAO function and returns an Order object.
    */ 
    @Override
    public Order addOrder(LocalDate orderDate,
                          Order newOrder) throws FlooringMasteryFilePersistanceException{
        
        //String orderString = compileDate(month, day, year);
        
        String[] dateString = getDate(orderDate);
        String orderString = compileDate(dateString[0], dateString[1], dateString[2]);
        
        dao.addOrder(orderString, newOrder);
        
        return newOrder;
    }

    /*
    ** Function Name: compileOrder()
    ** Return Type: Order
    ** Purpose: Passes a series of Strings representing data used to create an
                order and calculate the needed variables. Sets the calculated
                values to the their respective Order values. Finally, returns 
                the completed order.
    */ 
    @Override
    public Order compileOrder(Order editOrder, String customerName, String stateName, String productType, String area) throws FlooringMasteryFilePersistanceException {
        
        Order editedOrder = editOrder;
        
        if (customerName.isBlank() == false){
            editedOrder.setCustomerName(customerName);
        }
        
        if (stateName.isBlank() == false){
            editedOrder.setState(stateName);
        }
        
        if (productType.isBlank() == false){
            editedOrder.setProductType(productType);
        }
        
        if (area.isBlank() == false){
            BigDecimal orderArea = new BigDecimal(area);
            editedOrder.setArea(orderArea);
        }
        
        Taxes orderTaxes = getTaxesByState(editedOrder.getState());
        Product orderProduct = getProductByName(editedOrder.getProductType());
        
        BigDecimal materialCost = orderProduct.getCostPerSquareFoot().multiply(editedOrder.getArea());
        BigDecimal laborCost = orderProduct.getLaborCostPerSquareFoot().multiply(editedOrder.getArea());
        BigDecimal costBeforeTax = materialCost.add(laborCost);
        
        BigDecimal taxDivisor = new BigDecimal("100");
        BigDecimal orderTaxRate = orderTaxes.getTaxRate().divide( taxDivisor, 2, RoundingMode.HALF_UP);
        BigDecimal orderTax = costBeforeTax.multiply(orderTaxRate);
        
        BigDecimal orderTotal = costBeforeTax.add(orderTax);
        
        editedOrder.setTaxRate(orderTaxes.getTaxRate());
        editedOrder.setCostPerSquareFoot(orderProduct.getCostPerSquareFoot());
        editedOrder.setLaborCostPerSquareFoot(orderProduct.getLaborCostPerSquareFoot());
        editedOrder.setMaterialCost(materialCost);
        editedOrder.setLaborCost(laborCost);
        
        editedOrder.setTax(orderTax.setScale(2, RoundingMode.HALF_UP));
        editedOrder.setTotal(orderTotal.setScale(2, RoundingMode.HALF_UP));
        
        return editedOrder;
        
        
    }
    
     /*
    ** Function Name: editOrder()
    ** Return Type: void
    ** Purpose: Passes a series of Strings representing the date, and an Order object 
                to the respective DAO function.
    */ 
    @Override
    public void editOrder(LocalDate orderDate, Order editedOrder) throws FlooringMasteryFilePersistanceException {
        
        //String orderString = compileDate(month, day, year);
        
        String[] dateString = getDate(orderDate);
        String orderString = compileDate(dateString[0], dateString[1], dateString[2]);
        
        dao.editOrder(orderString, editedOrder);
    }

     /*
    ** Function Name: getOrder()
    ** Return Type: Order
    ** Purpose: Passes a series of Strings representing the date, and an int 
                representing the order number to the representitive DAO function.
                Calls the respective DAO function and returns the Order object 
                returned by the DAO. 
    */     
    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber)throws FlooringMasteryFilePersistanceException {
        
        //String orderString = compileDate(month, day, year);       
        String[] dateString = getDate(orderDate);
        String orderString = compileDate(dateString[0], dateString[1], dateString[2]);
        
        return dao.getOrder(orderString, orderNumber);   
    }

    
     /*
    ** Function Name: removeOrder()
    ** Return Type: Order
    ** Purpose: Passes a series of Strings representing the date, and an int 
                representing the order number to the representitive DAO function.
                Calls the respective DAO function and to delete the defined 
                Order object from the order list. 
    */   
    @Override
    public Order removeOrder(LocalDate orderDate,  int orderNumber)throws FlooringMasteryFilePersistanceException{
        
        //String orderString = compileDate(month, day, year);
        String[] dateString = getDate(orderDate);
        String orderString = compileDate(dateString[0], dateString[1], dateString[2]);
        
        return dao.removeOrder(orderString, orderNumber);
        
    }
    
    //PASSTHROUGH FUNCTION
    @Override
    public void exportOrderData() throws FlooringMasteryFilePersistanceException {
    
        dao.exportOrderData();
    }

     /*
    ** Function Name: compileDate()
    ** Return Type: String
    ** Purpose: Passes a series of Strings representing the date, and returns a
                string representing the file to write information to. 
    */   
    private String compileDate(String month, String day, String year){
        
        String orderDate = "Orders/Orders_" + month + day + year + ".txt";
        
        return orderDate;
    }
    
    // Exceptions
     /*
    ** Function Name: checkIfStateExists()
    ** Return Type: void
    ** Purpose: Creates a list of Taxes objects and checks if the state defined 
                by  String stateCode exists in that list. If the state does not
                exists, throws an error.
    */  
    private void checkIfStateExists(String stateCode) throws FlooringMasteryStateCodeDoesNotExistException,
                                                             FlooringMasteryFilePersistanceException{
        
        List<String> queryList = dao.getTaxes().stream().map((p)->p.getStateAbbr()).collect(Collectors.toList());
        boolean stateExists = queryList.contains(stateCode);
        
        if (stateExists != true){
            throw new FlooringMasteryStateCodeDoesNotExistException("We do not offer services in " + stateCode + ".");
        }
    }

      /*
    ** Function Name: areServiceAvailableThere()
    ** Return Type: void
    ** Purpose: calls checkIfStateExists().
    */  
    @Override
    public void areServicesAvailableThere(String stateCode) throws FlooringMasteryStateCodeDoesNotExistException,
                                                                   FlooringMasteryFilePersistanceException{
        checkIfStateExists(stateCode);
    }
    
     /*
    ** Function Name: checkIfProductExists()
    ** Return Type: void
    ** Purpose: Creates a list of Product objects and checks if the product 
                defined by  String product exists in that list. If the product 
                does not exists, throws an error.
    */ 
    private void checkIfProductExists(String product) throws FlooringMasteryProductDoesNotExistException,
                                                             FlooringMasteryFilePersistanceException{
        
        List<String> queryList = dao.getProducts().stream().map((p)->p.getProductType()).collect(Collectors.toList());
        boolean productExists = queryList.contains(product);
        
        if (productExists != true){
            throw new FlooringMasteryProductDoesNotExistException(product + " is unavailable to order.");
        }
    }
    
         /*
    ** Function Name: isProductAvailable()
    ** Return Type: void
    ** Purpose: Calls checkIfProductExists().
    */ 
    @Override
    public void isProductAvailable(String product) throws FlooringMasteryProductDoesNotExistException,
                                                          FlooringMasteryFilePersistanceException{
        checkIfProductExists(product);
    }

     /*
    ** Function Name: checkIfFieldIsBlank()
    ** Return Type: void
    ** Purpose: Checks if the passed String field object is blank. If the field
                is blank, throws an error.
    */     
    private void checkIfFieldIsBlank(String field) throws FlooringMasteryFieldIsBlankException{
        
        if (field.isBlank() == true){
            throw new FlooringMasteryFieldIsBlankException("This field cannot be blank. Please choose from the listed options.");
        }
    }
    
     /*
    ** Function Name: isFieldBlank()
    ** Return Type: void
    ** Purpose: Calls checkIfFieldIsBlank().
    */  
    @Override
    public void isFieldBlank(String field) throws FlooringMasteryFieldIsBlankException{
        checkIfFieldIsBlank(field);
    }
    
    /*
    ** Function Name: checkIfAreaIsValid()
    ** Return Type: void
    ** Purpose: First: Checks if the passed string value has any non-numeric 
                characters. If the string does, the function throws an error.
                Second: If the field contains only numbers, checks to make sure
                the field contains a number over 100. If it doesn't, the 
                function throws an error. 
    */   
    private void checkIfAreaIsValid(String area) throws FlooringMasteryAreaIsNotValidException{
        
        Pattern p = Pattern.compile("[^0-9.]");//setting up regex.
        Matcher m = p.matcher(area);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryAreaIsNotValidException("Area in sq. ft. must be represented in numbers, the minimum value accepted is 100.");
        }
        
        BigDecimal inputArea = new BigDecimal(area);
        BigDecimal minimumArea = new BigDecimal("100");
        
        int comparison = inputArea.compareTo(minimumArea);
        
        if (comparison == -1){
            throw new FlooringMasteryAreaIsNotValidException( area + "sq. ft. is lower than the minimum allowed area (100 sq. ft.) ");
        }
    }
    
    /*
    ** Function Name: isAreaValid()
    ** Return Type: void
    ** Purpose: Calls checkIfAreaIsValid().
    */  
    @Override
    public void isAreaValid(String area) throws FlooringMasteryAreaIsNotValidException{
        checkIfAreaIsValid(area);
    }
    
    /*
    ** Function Name: checkIfAreaIsValid()
    ** Return Type: void
    ** Purpose: Checks if the passed string value has any non-defined 
                characters. If the string does, the function throws an error.
                
    */   
    private void checkIfNameIsValid(String name)  throws FlooringMasteryNameIsNotValidException{
        
        //Regex, matches for any non comma, period, or alphanumeric character.
        Pattern p = Pattern.compile("[^a-z0-9., ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryNameIsNotValidException("Names cannot contain special characters.");
        }
    }
    
    /*
    ** Function Name: isNameValid()
    ** Return Type: void
    ** Purpose: Calls checkIfNameIsValid().
    */  
    @Override
    public void isNameValid(String name) throws FlooringMasteryNameIsNotValidException{
        
        checkIfNameIsValid(name);
    }
    
    private void checkIfMonthIfValid(String month) throws FlooringMasteryMonthIsNotValidException{
        
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(month);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryMonthIsNotValidException("Months must be represented through 01-12");
        }
        
        int checkValue = Integer.parseInt(month);
        
        if (checkValue < 1 || checkValue > 12){
            throw new FlooringMasteryMonthIsNotValidException("Months must be through 01-12.");
        }
    }
    
    @Override
    public void isMonthValid(String month) throws FlooringMasteryMonthIsNotValidException{
        
        checkIfMonthIfValid(month);
    }
    
    private void checkIfDayIsValid(String day, String month, String year) throws FlooringMasteryDayIsNotValidException{
        
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(day);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryDayIsNotValidException("Dates nust be represented by numberic values.");
        }
        
        
        boolean dateValid = true;
        
        String date = year + "-" + month + "-" + day;
        
        try{
            LocalDate.parse( date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            
            dateValid = true;
        } catch (DateTimeParseException e){
            
            dateValid = false; 
        }
        
        if (dateValid == false){
            throw new FlooringMasteryDayIsNotValidException("This is not a valid Date.");
        }
        
        
    }

    @Override
    public void isDateValid(String day, String month, String year) throws FlooringMasteryDayIsNotValidException {
       
        checkIfDayIsValid(day, month, year);
    }
    
    private void checkIfYearIsValid(String year) throws FlooringMasteryYearIsNotValidException{
    
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(year);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryYearIsNotValidException("Years nust be represented by numberic values.");
        }
    }

    @Override
    public void isYearValid(String year) throws FlooringMasteryYearIsNotValidException {
        
        checkIfYearIsValid(year);
    }
    
    /*
    ** Function Name: checkIfDateIsInTheFuture()
    ** Return Type: void
    ** Purpose: Takes in a series of Strings that represents the date entered by 
                the user. Creates a string from the passed strings to compare to
                a LocalDate object that represents the date the program is run.
                Compares the two dates, and determines if the user inputted date
                is before or on the current date. If so, throws an error. 
                
    */   
    private void checkIfDateIsInTheFuture(LocalDate orderDate) throws FlooringMasteryDateIsNotInTheFutureException{
        
        LocalDate ld = LocalDate.now();
        //String date = year + "-" + month + "-" + day;
        //String date = month + "/" + day + "/" + year;
        
        //String date = "2022-07-13";
        //LocalDate compareDate = LocalDate.parse(month + "/" + day + "/" + year, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        //LocalDate compareDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate compareDate = orderDate;
        
        boolean pastDate = false;
        
        pastDate = ld.isBefore(compareDate);
        
        if (!pastDate){
            throw new FlooringMasteryDateIsNotInTheFutureException ("We only accept orders for future dates.");
        }
        
    }

    /*
    ** Function Name: isAppointmentInTheFuture()
    ** Return Type: void
    ** Purpose: Calls checkIfDateIsInTheFuture().
    */ 
    @Override
    public void isAppointmentInTheFuture(LocalDate orderDate) throws FlooringMasteryDateIsNotInTheFutureException {
        
        checkIfDateIsInTheFuture(orderDate);
    }
    
    /*
    ** Function Name: checkIfOrderNumberIsValid()
    ** Return Type: void
    ** Purpose: Takes in a series of Strings that represents the date entered by 
                the user and the order number. If the number field is a 
                non-numeric character, the function throws an error. 
    
                If the number is valid, the function makes a list of Ints 
                representing the order numbers of the file defined by the date
                Strings. If the order does not exist, throws an error.
                
    */   
    private void checkIfOrderNumberIsValid(LocalDate orderDate, String orderNumber) throws FlooringMasteryOrderNumberIsNotValidException,
                                                                                                             FlooringMasteryFilePersistanceException{
        
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(orderNumber);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryOrderNumberIsNotValidException("Order Numbers nust be represented by numberic values.");
        }
        
        String[] dateString = getDate(orderDate);
        String fileDate = compileDate(dateString[0], dateString[1], dateString[2]);
        
        //List<String> queryList = dao.getProducts().stream().map((p)->p.getProductType()).collect(Collectors.toList());
        List<Integer> queryList = dao.getAllOrdersOnDate(fileDate).stream().map((o)->o.getOrderNumber()).collect(Collectors.toList());
        int queryNumber = Integer.parseInt(orderNumber);
        
        boolean orderExists = queryList.contains(queryNumber);
        
        if (orderExists != true){
            String errorDate = orderDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            throw new FlooringMasteryOrderNumberIsNotValidException(orderNumber + " on " + errorDate +  " is not a preexisting order.");
        }
        
    }

    /*
    ** Function Name: isOrderNumberValid()
    ** Return Type: void
    ** Purpose: Calls checkIfOrderNumberIsValid().
    */ 
    @Override
    public void isOrderNumberValid(LocalDate orderDate, String orderNumber) throws FlooringMasteryOrderNumberIsNotValidException,
                                                                                                     FlooringMasteryFilePersistanceException{
        
        checkIfOrderNumberIsValid(orderDate, orderNumber);
    }
    
    /** Function Name: getDate
    ** Return Type: String array
    ** Purpose: gets a LocalDate object from the user using the getDate() 
        function  in view.FlooringMasteryView. Breaks the date object into three
        separate strings used to represent the Month, Day, and Year. 
    */ 
    private String[] getDate(LocalDate ld){
        
        String date[] = new String[3];
        
        LocalDate functionDate = ld;
        
        String month = String.valueOf(functionDate.getMonthValue());
        if(month.length() < 2){
            month = "0" + month;
        }
        String day = String.valueOf(functionDate.getDayOfMonth());
        if(day.length() < 2){
            day = "0" + day;
        }
        String year = String.valueOf(functionDate.getYear());
        
        date[0] = month;
        date[1] = day;
        date[2] = year;
        
        //System.out.println( date[0] + date[1] + date[2]);
        
        return date;
    }
    
}
