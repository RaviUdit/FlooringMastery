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

    @Override
    public List<Order> getAllOrdersOnDate(String month, String day, String year) throws FlooringMasteryFilePersistanceException {
        
        String orderString = compileDate(month, day, year);
        
        return dao.getAllOrdersOnDate(orderString);
    }
    
    @Override
    public Order addOrder(String month, String day, String year,
                          Order newOrder) throws FlooringMasteryFilePersistanceException{
        
        String orderString = compileDate(month, day, year);
//        
//        Taxes orderTaxes = getTaxesByState(stateName);
//        Product orderProduct = getProductByName(productType);
//        
//        BigDecimal orderArea = new BigDecimal(area);
//        BigDecimal materialCost = orderProduct.getCostPerSquareFoot().multiply(orderArea);
//        BigDecimal laborCost = orderProduct.getLaborCostPerSquareFoot().multiply(orderArea);
//        BigDecimal costBeforeTax = materialCost.add(laborCost);
//        
//        BigDecimal taxDivisor = new BigDecimal("100");
//        BigDecimal orderTaxRate = orderTaxes.getTaxRate().divide( taxDivisor, 2, RoundingMode.HALF_UP);
//        BigDecimal orderTax = costBeforeTax.multiply(orderTaxRate);
//        
//        BigDecimal orderTotal = costBeforeTax.add(orderTax);
//        
//        Order newOrder = new Order(1);
//        newOrder.setCustomerName(customerName);
//        newOrder.setState(stateName);
//        newOrder.setTaxRate(orderTaxes.getTaxRate());
//        newOrder.setProductType(productType);
//        newOrder.setArea(orderArea);
//        newOrder.setCostPerSquareFoot(orderProduct.getCostPerSquareFoot());
//        newOrder.setLaborCostPerSquareFoot(orderProduct.getLaborCostPerSquareFoot());
//        newOrder.setMaterialCost(materialCost);
//        newOrder.setLaborCost(laborCost);
//        newOrder.setTax(orderTax.setScale(2, RoundingMode.HALF_UP));
//        newOrder.setTotal(orderTotal.setScale(2, RoundingMode.HALF_UP));
        dao.addOrder(orderString, newOrder);
        
        return newOrder;
    }
 
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
    
    @Override
    public void editOrder(String month, String day, String year, Order editedOrder) throws FlooringMasteryFilePersistanceException {
        
        String orderString = compileDate(month, day, year);
        
        dao.editOrder(orderString, editedOrder);
    }
    
    @Override
    public Order getOrder(String month, String day, String year, int orderNumber)throws FlooringMasteryFilePersistanceException {
        
        String orderString = compileDate(month, day, year);       
        return dao.getOrder(orderString, orderNumber);   
    }

    

    @Override
    public Order removeOrder(String month, String day, String year,  int orderNumber)throws FlooringMasteryFilePersistanceException{
        
        String orderString = compileDate(month, day, year);
        
        return dao.removeOrder(orderString, orderNumber);
        
    }
    
    @Override
    public void exportOrderData() throws FlooringMasteryFilePersistanceException {
    
        dao.exportOrderData();
    }

    private String compileDate(String month, String day, String year){
        
        String orderDate = "Orders/Orders_" + month + day + year + ".txt";
        
        return orderDate;
    }
    
    // Exceptions

    private void checkIfStateExists(String stateCode) throws FlooringMasteryStateCodeDoesNotExistException,
                                                             FlooringMasteryFilePersistanceException{
        
        List<String> queryList = dao.getTaxes().stream().map((p)->p.getStateAbbr()).collect(Collectors.toList());
        boolean stateExists = queryList.contains(stateCode);
        
        if (stateExists != true){
            throw new FlooringMasteryStateCodeDoesNotExistException("We do not offer services in " + stateCode + ".");
        }
    }

    @Override
    public void areServicesAvailableThere(String stateCode) throws FlooringMasteryStateCodeDoesNotExistException,
                                                                   FlooringMasteryFilePersistanceException{
        checkIfStateExists(stateCode);
    }
    
    private void checkIfProductExists(String product) throws FlooringMasteryProductDoesNotExistException,
                                                             FlooringMasteryFilePersistanceException{
        
        List<String> queryList = dao.getProducts().stream().map((p)->p.getProductType()).collect(Collectors.toList());
        boolean productExists = queryList.contains(product);
        
        if (productExists != true){
            throw new FlooringMasteryProductDoesNotExistException(product + " is unavailable to order.");
        }
    }
    
    @Override
    public void isProductAvailable(String product) throws FlooringMasteryProductDoesNotExistException,
                                                          FlooringMasteryFilePersistanceException{
        checkIfProductExists(product);
    }
    
    private void checkIfFieldIsBlank(String field) throws FlooringMasteryFieldIsBlankException{
        
        if (field.isBlank() == true){
            throw new FlooringMasteryFieldIsBlankException("This field cannot be blank. Please choose from the listed options.");
        }
    }
    
    @Override
    public void isFieldBlank(String field) throws FlooringMasteryFieldIsBlankException{
        checkIfFieldIsBlank(field);
    }
    
    private void checkIfAreaIsValid(String area) throws FlooringMasteryAreaIsNotValidException{
        
        Pattern p = Pattern.compile("[^0-9]");
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
    
    @Override
    public void isAreaValid(String area) throws FlooringMasteryAreaIsNotValidException{
        checkIfAreaIsValid(area);
    }
    
    private void checkIfNameIsValid(String name)  throws FlooringMasteryNameIsNotValidException{
        
        //Regex, matches for any non comma, period, or alphanumeric character.
        Pattern p = Pattern.compile("[^a-z0-9., ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryNameIsNotValidException("Names cannot contain special characters.");
        }
    }
    
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
    
    private void checkIfDateIsInTheFuture(String day, String month, String year) throws FlooringMasteryDateIsNotInTheFutureException{
        
        LocalDate ld = LocalDate.now();
        String date = year + "-" + month + "-" + day;
        
        //String date = "2022-07-13";
        //LocalDate compareDate = LocalDate.parse(month + "/" + day + "/" + year, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate compareDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d"));
        
        boolean pastDate = false;
        
        pastDate = ld.isBefore(compareDate);
        
        if (!pastDate){
            throw new FlooringMasteryDateIsNotInTheFutureException ("We only accept orders for future dates.");
        }
        
    }

    @Override
    public void isAppointmentInTheFuture(String day, String month, String year) throws FlooringMasteryDateIsNotInTheFutureException {
        
        checkIfDateIsInTheFuture(day, month, year);
    }
    
    private void checkIfOrderNumberIsValid(String day, String month, String year, String orderNumber) throws FlooringMasteryOrderNumberIsNotValidException,
                                                                                                             FlooringMasteryFilePersistanceException{
        
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(orderNumber);
        
        boolean notValid = m.find();
        
        if (notValid == true){
            throw new FlooringMasteryOrderNumberIsNotValidException("Order Numbers nust be represented by numberic values.");
        }
        
        String orderDate = compileDate(month, day, year);
        
        //List<String> queryList = dao.getProducts().stream().map((p)->p.getProductType()).collect(Collectors.toList());
        List<Integer> queryList = dao.getAllOrdersOnDate(orderDate).stream().map((o)->o.getOrderNumber()).collect(Collectors.toList());
        int queryNumber = Integer.parseInt(orderNumber);
        
        boolean orderExists = queryList.contains(queryNumber);
        
        if (orderExists != true){
            String orderDate2 = month + "-" + day + "-" + year;
            throw new FlooringMasteryOrderNumberIsNotValidException(orderNumber + " on " + orderDate2 +  " is not a preexisting order.");
        }
        
    }

    @Override
    public void isOrderNumberValid(String day, String month, String year, String orderNumber) throws FlooringMasteryOrderNumberIsNotValidException,
                                                                                                     FlooringMasteryFilePersistanceException{
        
        checkIfOrderNumberIsValid(day, month, year, orderNumber);
    }
 }