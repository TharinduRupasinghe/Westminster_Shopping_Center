// The ShoppingManager interface defines a set of methods for managing products in a shopping system.
public interface ShoppingManager {

    // Method to add a new product to the system.
    void addNewProductToSystem(Product product);

    // Method to delete a product from the system based on its ID.
    void deleteProductFromSystem(String productID);

    // Method to print a list of products in the system.
    void printListOfProducts();

    // Method to save the products to a file.
    void saveProductsToFile();

    // Method to read products from a file and update the system.
    void readProductsFromFile();

    // Method to display a console menu for user interaction.
    boolean consoleMenu();
}
