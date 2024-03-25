// Class representing Electronics, which is a subclass of the Product class
public class Electronics extends Product {
    // Private fields specific to Electronics
    private String brand;
    private String warrantyPeriod;

    // Constructor for creating an Electronics object with specified attributes
    public Electronics(String productID, String productName, int availableItems, double price, String brand, String warrantyPeriod) {
        // Calling the constructor of the superclass (Product) to initialize common attributes
        super(productID, productName, availableItems, price);

        // Initializing Electronics-specific attributes
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter method for retrieving the brand of the Electronics
    public String getBrand() {
        return brand;
    }

    // Setter method for setting the brand of the Electronics
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter method for retrieving the warranty period of the Electronics
    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Setter method for setting the warranty period of the Electronics
    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
