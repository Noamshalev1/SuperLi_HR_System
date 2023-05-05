package DomainLayer;

import java.util.HashMap;
import java.util.Map;

public class OrderDocument {
    public static int documentNextId=1;
    private final int documentId;
    private final Supplier source;
    private final Store destination;
    private double totalWeight; //detailed weight ??
    private Map<Product, Double> productsList;


    public OrderDocument(Supplier source, Store destination) {
        this.documentId = documentNextId;
        documentNextId++;
        this.source = source;
        this.destination = destination;
        this.totalWeight=0;
        this.productsList = new HashMap<>();
    }
    public int getDocumentId() {
        return documentId;
    }
    public Supplier getSource() {
        return source;
    }
    public Store getDestination() {
        return destination;
    }
    public double getTotalWeight() {
        return totalWeight;
    }
    public Map<Product, Double> getProductsList() {
        return productsList;
    }
    public void setProductsList(Map<Product, Double> newProductsList) {
        productsList = newProductsList;
    }
    public void setWeight(double weight) {
        totalWeight=weight;
    }
    public void printOrderProductList() {
        for (Map.Entry<Product, Double> entry : productsList.entrySet()) {
            String product = entry.getKey().getProductName();
            Double amount = entry.getValue();
            System.out.println("\t" + product + " : " + amount);
        }
    }
    public void printOrder(){
        System.out.println("Document id: " + this.documentId);
        System.out.println("Source: " + source.address);
        System.out.println("Destination: " + destination.address);
        System.out.println("Total weight: " + totalWeight);
        System.out.println("Products in order: ");
        printOrderProductList();
    }
    public void removeProductFromOrder(Product product){
        double amountToReduce = productsList.get(product);
        productsList.remove(product);
        totalWeight-= amountToReduce;
    }
    public void printOrderDestination() {
        System.out.println("Destination is: " + destination.address);
    }
    public void printOrderSource(){
        System.out.println("Source is: " + source.address);
    }
    public void printOrderId() {
        System.out.println("Order Id is: " + documentId);
    }
}