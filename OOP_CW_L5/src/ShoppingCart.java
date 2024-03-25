// The ShoppingCart class represents a shopping cart that holds a list of products.
import java.util.ArrayList;

public class ShoppingCart {

    // Private instance variable to store the list of products in the shopping cart.
    private ArrayList<Product> cartProductList;

    // Constructor to initialize an empty shopping cart.
    public ShoppingCart() {
        // Initialize the shopping cart as an ArrayList of products.
        this.cartProductList = new ArrayList<>();
    }

    // Method to add a product to the shopping cart.
    public boolean addProductToCart(Product product) {
        try {
            // Attempt to add the specified product to the shopping cart.
            this.cartProductList.add(product);
            return true;
        } catch (Exception e) {
            // Return false if an exception occurs during the addition.
            return false;
        }
    }

    // Method to find a product in the shopping cart based on its ID.
    public Product findProduct(String Id) {
        // Initialize a variable to store the found product.
        Product searchItem = null;

        // Iterate through the products in the shopping cart.
        for (Product product : this.cartProductList) {
            // Check if the ID of the current product matches the specified ID.
            if (product.getProductID().equals(Id)) {
                // Set the found product and break out of the loop.
                searchItem = product;
                break;
            }
        }
        // Return the found product (or null if not found).
        return searchItem;
    }

    // Method to remove a product from the shopping cart based on its ID.
    public boolean removeProductFromCart(String productID) {
        try {
            // Attempt to remove the product with the specified ID from the shopping cart.
            cartProductList.remove(findProduct(productID));
            return true;
        } catch (Exception e) {
            // Return false if an exception occurs during the removal.
            return false;
        }
    }

    // Method to calculate the total cost of all products in the shopping cart.
    public double calculateTotalCost() {
        // Initialize a variable to store the total cost.
        double totalCost = 0;

        // Iterate through the products in the shopping cart.
        for (Product product : cartProductList) {
            // Calculate the total cost of the current product and add it to the overall total.
            double productTotal = product.getPrice() * product.getAvailableItems();
            totalCost += productTotal;
        }
        // Return the calculated total cost.
        return totalCost;
    }
}
