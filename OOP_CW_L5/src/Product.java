//The abstract class Product serves as the base class(parent class) for Electronics class and Clothing class.
public abstract class Product implements Comparable<Product> {
    // Instance variables to store product details
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    //Constructor for the Product class.
    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    //Getter method to retrieve the product ID.
    public String getProductID() {
        return productID;
    }

    //Setter method to set the product ID.
    public void setProductID(String productID) {
        this.productID = productID;
    }

    //Getter method to retrieve the product name.
    public String getProductName() {
        return productName;
    }

    //Setter method to set the product name.
    public void setProductName(String productName) {
        this.productName = productName;
    }

    //Getter method to retrieve the number of available items.
    public int getAvailableItems() {
        return availableItems;
    }

    //Setter method to set the number of available items.
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    //Getter method to retrieve the price of the product.
    public double getPrice() {
        return price;
    }

    //Setter method to set the price of the product.
    public void setPrice(double price) {
        this.price = price;
    }

    //Implementation of the compareTo method from the Comparable interface.
    //Allows sorting products based on their product ID in ascending order.
    @Override
    public int compareTo(Product o) {
        return this.getProductID().compareTo(o.getProductID());
    }
}

