/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author raviu
 */
public class FlooringMasteryDAOFileImpl implements FlooringMasteryDAO{

    //Maps
    private Map<Integer, Order> orders = new HashMap<>();
    private Map<String, Taxes> taxes =  new HashMap<>();    
    private Map<String, Product> products =  new HashMap<>();
    
    
    //File Code
    public static final String DELIMITER = ":&:";
    
    public static final String TAXES_LOCATION = "Data/Taxes.txt";
    public static final String PRODUCT_LOCATION = "Data/Products.txt";

    //FUNCTIONS
    
    @Override
    public Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException {
        
        loadTaxesFromFile();
        return taxes.get(stateAbbr);
    }

    @Override
    public Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException {
        
        loadProductsFromFile();
        return products.get(productName);
    }   
    
    @Override
    public Order addOrder(String orderDate, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order getOrder(String orderDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getAllOrdersOnDate(String month, String day, String year) throws FlooringMasteryFilePersistanceException{
        loadOrdersFromFile( month, day, year);
        return new ArrayList(orders.values());
    }

    @Override
    public Order removeOrder(String orderDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
    //Code to unmarshall and load Taxes    
    private void loadTaxesFromFile() throws FlooringMasteryFilePersistanceException{
        
        Scanner scanner; 
        
        try{
            scanner = new Scanner(new BufferedReader( new FileReader(TAXES_LOCATION)));
        }catch (FileNotFoundException e){
            throw new FlooringMasteryFilePersistanceException("File Does Not Exist", e);
        }
        
        String currentLine;
        Taxes currentTax;
        
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentTax = unmarshallTaxes(currentLine);
            
            taxes.put(currentTax.getStateAbbr(), currentTax);
        }
        
        scanner.close();
        
    }
    
    private Taxes unmarshallTaxes(String taxesAsString){
        
        String[] taxToken = taxesAsString.split(DELIMITER);
        
        String stateAbbr = taxToken[0];
        
        Taxes taxFromFile = new Taxes(stateAbbr);
        taxFromFile.setStateName(taxToken[1]);
        taxFromFile.setTaxRate(new BigDecimal(taxToken[2]));
        
        return taxFromFile;
    }
    
    
    
    //Code to unmarshall and load Products
    
    private void loadProductsFromFile() throws FlooringMasteryFilePersistanceException{
        
        Scanner scanner; 
        
        try{
            scanner = new Scanner(new BufferedReader( new FileReader(PRODUCT_LOCATION)));
        }catch (FileNotFoundException e){
            throw new FlooringMasteryFilePersistanceException("File Does Not Exist", e);
        }
        
        String currentLine;
        Product currentProduct;
        
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProducts(currentLine);
            
            products.put(currentProduct.getProductType(), currentProduct);
        }
        
        scanner.close();
    }
        
    private Product unmarshallProducts(String productsAsString){
        
        String[] productToken = productsAsString.split(DELIMITER);
        
        String productName = productToken[0];
        
        Product productFromFile = new Product(productName);
        productFromFile.setCostPerSquareFoot(new BigDecimal(productToken[1]));
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productToken[2]));
        
        return productFromFile;
    }
    
    


    //Code to load and write orders to file
    private void loadOrdersFromFile(String month, String day, String year) throws FlooringMasteryFilePersistanceException{
        
        Scanner scanner;
        
        try{
            scanner = new Scanner(new BufferedReader(new FileReader("Orders/Orders_" + month + day + year + ".txt")));
        }catch (FileNotFoundException e) {
            throw new FlooringMasteryFilePersistanceException("File Does Not Exist", e);
        }
        
        String currentLine;
        Order currentOrder;
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        
        scanner.close();
    }
    
    private void writeOrdersToFile(String month, String day, String year) throws FlooringMasteryFilePersistanceException{
        
    }
    
    private Order unmarshallOrder(String orderAsText){
        
        String[] orderToken = orderAsText.split(DELIMITER);
        
        //Converts String to int
        int orderNumber = Integer.valueOf(orderToken[0]);
        
        //Creating a new Order
        Order orderFromFile = new Order(orderNumber);
        
        orderFromFile.setCustomerName(orderToken[1]);
        orderFromFile.setState(orderToken[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderToken[3]));
        orderFromFile.setProductType(orderToken[4]);
        orderFromFile.setArea(new BigDecimal(orderToken[5]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderToken[6]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderToken[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderToken[8])); 
        orderFromFile.setLaborCost(new BigDecimal(orderToken[9]));
        orderFromFile.setTax(new BigDecimal(orderToken[10]));
        orderFromFile.setTotal(new BigDecimal(orderToken[11]));
        
        return orderFromFile;
        
    }
    
    private String marshallOrder(Order order){
        
        String orderAsText = String.valueOf(order.getOrderNumber()) + DELIMITER;
        
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate().toString() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea().toString() + DELIMITER;
        orderAsText += order.getCostPerSquareFoot().toString() + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot().toString() + DELIMITER;
        orderAsText += order.getMaterialCost().toString() + DELIMITER;
        orderAsText += order.getLaborCost().toString() + DELIMITER;
        orderAsText += order.getTax().toString() + DELIMITER;
        orderAsText += order.getTotal().toString();
        
        return orderAsText;
    }

    
}
