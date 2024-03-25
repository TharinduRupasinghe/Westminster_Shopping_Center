// The Clothing class extends the Product class, indicating a relationship between the two classes.
public class Clothing extends Product {

    // Private instance variables to store information specific to clothing items.
    private String size;
    private String colour;

    // Constructor for creating a Clothing object with specified attributes.
    public Clothing(String productID, String productName, int availableItems, double price, String size, String colour) {
        // Call the constructor of the superclass (Product) to initialize common attributes.
        super(productID, productName, availableItems, price);

        // Initialize the size and colour specific to the Clothing class.
        this.size = size;
        this.colour = colour;
    }

    // Getter method to retrieve the size of the clothing.
    public String getSize() {
        return size;
    }

    // Setter method to set the size of the clothing.
    public void setSize(String size) {
        this.size = size;
    }

    // Getter method to retrieve the colour of the clothing.
    public String getColour() {
        return colour;
    }

    // Setter method to set the colour of the clothing.
    public void setColour(String colour) {
        this.colour = colour;
    }
}
