package DomainLayer;

public class Product {
    private int productId;
    public String productName;
    private double productWeight;

    public Product(int productId, String productName, double productWeight) {
        this.productId = productId;
        this.productName = productName;
        this.productWeight = productWeight;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductWeight() {
        return productWeight;
    }
    public void printProduct()
    {
        System.out.println(productName + ": " + productId );
    }
}