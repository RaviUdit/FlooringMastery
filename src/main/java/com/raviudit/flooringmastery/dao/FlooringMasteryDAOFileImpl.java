/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

import static com.raviudit.flooringmastery.dao.FlooringMasteryBackupDAOFileImpl.ORDERS_FOLDER;
import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    
    public static final String BACKUP_FILE = "Backup/Orderlist.txt";
    public static final String ORDERS_FOLDER = "Orders";
    public static final String TAXES_LOCATION = "Data/Taxes.txt";
    public static final String PRODUCT_LOCATION = "Data/Products.txt";

    //FUNCTIONS
    
    
    @Override
    public List<Taxes> getTaxes() throws FlooringMasteryFilePersistanceException {
        
        loadTaxesFromFile();
        return new ArrayList(taxes.values());
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryFilePersistanceException {
        
        loadProductsFromFile();
        return new ArrayList(products.values());
    }
    
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
    public void addOrder(String orderDate, Order order)  throws FlooringMasteryFilePersistanceException {
        
       File f = new File(orderDate);
       if (f.exists()){
           loadOrdersFromFile(orderDate);
           
           Order lastOrder = getAllOrdersOnDate(orderDate).get(getAllOrdersOnDate(orderDate).size()-1);
           
           order.setOrderNumber(lastOrder.getOrderNumber() + 1);
           Order newOrder = orders.put(order.getOrderNumber(), order);
           writeOrdersToFile(orderDate);
           
       } else {
           writeOrderToNonexistingFile(orderDate, order);
       }
       
    }
    
    
    @Override
    public void editOrder(String orderDate, Order order) throws FlooringMasteryFilePersistanceException {
        
        loadOrdersFromFile(orderDate);
        orders.put(order.getOrderNumber(), order);
        writeOrdersToFile(orderDate);
        
    }

    @Override
    public Order getOrder(String orderDate, int orderNumber) throws FlooringMasteryFilePersistanceException{
        
        loadOrdersFromFile(orderDate);
        return orders.get(orderNumber);
       
    }

    @Override
    public List<Order> getAllOrdersOnDate(String orderDate) throws FlooringMasteryFilePersistanceException{
       
        loadOrdersFromFile(orderDate);
        return new ArrayList(orders.values());
    }

    @Override
    public Order removeOrder(String orderDate, int orderNumber) throws FlooringMasteryFilePersistanceException{
        
        loadOrdersFromFile(orderDate);
        Order removedOrder = orders.remove(orderNumber);
        writeOrdersToFile(orderDate);
        
        return removedOrder;
       
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
    private void loadOrdersFromFile(String orderDate) throws FlooringMasteryFilePersistanceException{
        
        Scanner scanner;
        
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(orderDate)));
        }catch (FileNotFoundException e) {
            throw new FlooringMasteryFilePersistanceException("File Does Not Exist", e);
        }
        
        String headerString;
        headerString = scanner.nextLine();
        
        String currentLine;
        Order currentOrder;
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        
        scanner.close();
    }
    
    private void writeOrdersToFile(String orderDate) throws FlooringMasteryFilePersistanceException{
        
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(orderDate));
        }catch(IOException e){
            throw new FlooringMasteryFilePersistanceException("Could not load File", e);
        }
        String headerString = "OrderNumber:&:CustomerName:&:State:&:TaxRate:&:ProductType:&:Area:&:CostPerSquareFoot:&:LaborCostPerSquareFoot:&:MaterialCost:&:LaborCost:&:Tax:&:Total";
        out.println(headerString);
        out.flush();
        
        String orderAsText;
        
        List<Order> orderList = this.getAllOrdersOnDate(orderDate);
        for (Order currentOrder : orderList){
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
            out.flush();
        }
        
        out.close();
    }
    
    private void writeOrderToNonexistingFile(String orderDate, Order order) throws FlooringMasteryFilePersistanceException{
        
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(orderDate));
        }catch(IOException e){
            throw new FlooringMasteryFilePersistanceException("Could not Create File", e);
        }
        String headerString = "OrderNumber:&:CustomerName:&:State:&:TaxRate:&:ProductType:&:Area:&:CostPerSquareFoot:&:LaborCostPerSquareFoot:&:MaterialCost:&:LaborCost:&:Tax:&:Total";
        out.println(headerString);
        out.flush();
        
        String orderAsText;
        
        orderAsText = marshallOrder(order);
        out.println(orderAsText);
        out.flush();
        
        out.close();
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
    
    @Override
    public void exportOrderData() throws FlooringMasteryFilePersistanceException {
        
        LocalDateTime timeStamp = LocalDateTime.now();
        File directory = new File(ORDERS_FOLDER);
        
        File[] filesList = directory.listFiles();
        
        for (File file: filesList){
            
            System.out.println("File name: "+file.getName());
        }
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
