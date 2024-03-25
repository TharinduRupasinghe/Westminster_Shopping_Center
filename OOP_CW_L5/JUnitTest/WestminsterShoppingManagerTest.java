import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager shoppingManager;

    @BeforeEach
    void setUp() {
        shoppingManager = new WestminsterShoppingManager();
    }

    @Test
    void addNewProductToSystem() {
        // Creating a sample product for testing
        Product sampleProduct = new Electronics("1", "TestProduct", 10, 99.99, "TestBrand", "12 weeks");

        // Adding the sample product
        shoppingManager.addNewProductToSystem(sampleProduct);

        // Checking if the product was added successfully
        assertEquals(1, shoppingManager.productList.size());
        assertTrue(shoppingManager.productList.contains(sampleProduct));
    }

    @Test
    void deleteProductFromSystem() {
        // Creating a sample product for testing
        Product sampleProduct = new Electronics("1", "TestProduct", 10, 99.99, "TestBrand", "12 weeks");

        // Adding the sample product
        shoppingManager.addNewProductToSystem(sampleProduct);

        // Deleting the sample product
        shoppingManager.deleteProductFromSystem("1");

        // Checking if the product was deleted successfully
        assertEquals(0, shoppingManager.productList.size());
        assertFalse(shoppingManager.productList.contains(sampleProduct));
    }

    @Test
    void printListOfProducts() {
        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Creating sample products for testing
        Product electronicsProduct = new Electronics("1", "ElectronicsProduct", 10, 99.99, "Brand", "12 weeks");
        Product clothingProduct = new Clothing("2", "ClothingProduct", 5, 49.99, "M", "Red");

        // Adding the sample products
        shoppingManager.addNewProductToSystem(electronicsProduct);
        shoppingManager.addNewProductToSystem(clothingProduct);

        // Printing the list of products
        shoppingManager.printListOfProducts();

        // Resetting System.out
        System.setOut(System.out);

        // Verifying the printed output
        String expectedOutput = String.format(
                "Product Type               : Electronic Device%n" +
                        "Product ID                 : 1%n" +
                        "Product Name               : ElectronicsProduct%n" +
                        "Product Price(£)           : 99.99%n" +
                        "Product Brand              : Brand%n" +
                        "Product Warranty Period    : 12 weeks%n" +
                        "%n" +
                        "Product Type               : Clothing Item%n" +
                        "Product ID                 : 2%n" +
                        "Product Name               : ClothingProduct%n" +
                        "Product Price(£)           : 49.99%n" +
                        "Product Size               : M%n" +
                        "Product Color              : Red%n");
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void isProductIDAlreadyExists() {
        // Creating a sample product for testing
        Product sampleProduct = new Electronics("1", "TestProduct", 10, 99.99, "TestBrand", "12 weeks");

        // Adding the sample product
        shoppingManager.addNewProductToSystem(sampleProduct);

        // Checking if the product ID already exists
        assertTrue(shoppingManager.isProductIDAlreadyExists("1"));
        assertFalse(shoppingManager.isProductIDAlreadyExists("2"));
    }

    @Test
    void productDetails() {
        // Redirecting System.in to provide input for productDetails
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\nTestProduct\n10\n99.99\n".getBytes());
        System.setIn(inputStream);

        // Creating a new WestminsterShoppingManager instance
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Calling productDetails
        shoppingManager.productDetails();

        // Resetting System.in
        System.setIn(System.in);

        // Verifying the product details
        assertEquals("1", shoppingManager.pID);
        assertEquals("TestProduct", shoppingManager.pName);
        assertEquals(10, shoppingManager.pItems);
        assertEquals(99.99, shoppingManager.pPrice, 0.001); // Using delta for double comparison
    }

    @Test
    void saveProductsToFile() {
        // Creating sample products for testing
        Product electronicsProduct = new Electronics("1", "ElectronicsProduct", 10, 99.99, "Brand", "12 weeks");
        Product clothingProduct = new Clothing("2", "ClothingProduct", 5, 49.99, "M", "Red");

        // Adding the sample products
        shoppingManager.addNewProductToSystem(electronicsProduct);
        shoppingManager.addNewProductToSystem(clothingProduct);

        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Calling saveProductsToFile
        shoppingManager.saveProductsToFile();

        // Resetting System.out
        System.setOut(System.out);

        // Verifying the printed output
        String expectedOutput = "Product list saved to file successfully!\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void readProductsFromFile() {
        // Creating sample products for testing
        Product electronicsProduct = new Electronics("1", "ElectronicsProduct", 10, 99.99, "Brand", "12 weeks");
        Product clothingProduct = new Clothing("2", "ClothingProduct", 5, 49.99, "M", "Red");

        // Adding the sample products
        shoppingManager.addNewProductToSystem(electronicsProduct);
        shoppingManager.addNewProductToSystem(clothingProduct);

        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Calling readProductsFromFile
        shoppingManager.readProductsFromFile();

        // Resetting System.out
        System.setOut(System.out);

        // Verifying the printed output
        String expectedOutput = "Product list loaded from file successfully!\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void consoleMenu() {
        // Redirecting System.in to provide input for consoleMenu
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\nE\n1\nTestProduct\n10\n99.99\nTestBrand\n12 weeks\n6\n".getBytes());
        System.setIn(inputStream);

        // Creating a new WestminsterShoppingManager instance
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Calling consoleMenu
        shoppingManager.consoleMenu();

        // Resetting System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Verifying the printed output
        String expectedOutput = """
                ---------------Westminster Shopping Manager Console Menu---------------\s
                1. To add a new product press '1'\s
                2. To delete a product press '2'\s
                3. To print the list of products press '3'\s
                4. To save product records press '4'\s
                5. To load product records press '5'\s
                6. To return to main menu press '6'\s
                \s
                Enter your option: Press 'E' if you want to add an Electronic product.\s
                Press 'C' if you want to add a Clothing product.\s
                \s
                Enter the product type you want to add: Enter product ID: Enter product name: Enter number of product items: Enter product price(£): Enter product brand: Enter product warranty period: Product added to the product list successfully!\s
                ---------------Westminster Shopping Manager Console Menu---------------\s
                1. To add a new product press '1'\s
                2. To delete a product press '2'\s
                3. To print the list of products press '3'\s
                4. To save product records press '4'\s
                5. To load product records press '5'\s
                6. To return to main menu press '6'\s
                \s
                Enter your option: \s
                """;
        assertEquals(expectedOutput, outputStream.toString());
    }
}