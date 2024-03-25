// The WestminsterShoppingManager class implements the ShoppingManager interface
// and serves as a shopping manager for handling products and user interactions.
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {

    // Variables for product details to be used in various methods.
     String pID, pName;
     int pItems;
     double pPrice;

    // Arrays for predefined warranty periods and clothing sizes.
    private static final String[] warrantyList = {"12 weeks", "24 weeks", "36 weeks", "48 weeks", "72 weeks", "2 years", "3 years", "4 years", "5 years"};
    private static final String[] sizeList = {"XS", "S", "M", "L", "XL", "2XL", "3XL"};

    // ArrayList to store the list of products.
    final ArrayList<Product> productList;

    // Constants for the maximum number of products and the filename for saving/loading.
    private static final int numOfProducts = 50;
    private static final String fileName = "productDataFile.txt";

    // Scanner for user input.
    private final Scanner myScanner = new Scanner(System.in);

    // Constructor to initialize the product list.
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<Product>();
    }

    // Implementation of the addNewProductToSystem method from the ShoppingManager interface.
    @Override
    public void addNewProductToSystem(Product product) {
        if (productList.size() < numOfProducts) {
            productList.add(product);
            System.out.println("\nProduct added to the product list successfully!\n");
        } else {
            System.out.println("Can't add more products! No more space in the product list\n");
        }
    }

    // Implementation of the deleteProductFromSystem method from the ShoppingManager interface.
    @Override
    public void deleteProductFromSystem(String productId) {
        if (productList.isEmpty()) {
            System.out.println("No more products available in the product list.\n");
            return;
        }
        boolean productFound = false;

        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                productFound = true;
                productList.remove(product);

                if (product instanceof Electronics) {
                    System.out.println("Electronic product with [ID-" + productId + "] has been deleted successfully.\n");
                } else if (product instanceof Clothing) {
                    System.out.println("Clothing product with [ID-" + productId + "] has been deleted successfully.\n");
                }
                System.out.println("Total number of products left in the system = " + productList.size() + "\n");
                break;
            }
        }
        if (!productFound) {
            System.out.println("Product with [ID-" + productId + "] not found.\n");
        }
    }

    // Implementation of the printListOfProducts method from the ShoppingManager interface.
    @Override
    public void printListOfProducts() {
        if (productList.isEmpty()) {
            System.out.println("Product list is empty!\n");
        }
        Collections.sort(productList);
        for (Product product : productList) {
            if (product instanceof Electronics) {
                System.out.println(
                        "\nProduct Type               : Electronic Device" +
                                "\nProduct ID                 : " + product.getProductID() +
                                "\nProduct Name               : " + product.getProductName() +
                                "\nProduct Price(£)           : " + product.getPrice() +
                                "\nProduct Brand              : " + ((Electronics) product).getBrand() +
                                "\nProduct Warranty Period    : " + ((Electronics) product).getWarrantyPeriod() + "\n");
            } else {
                System.out.println(
                        "\nProduct Type               : Clothing Item" +
                                "\nProduct ID                 : " + product.getProductID() +
                                "\nProduct Name               : " + product.getProductName() +
                                "\nProduct Price(£)           : " + product.getPrice() +
                                "\nProduct Size               : " + ((Clothing) product).getSize() +
                                "\nProduct Color              : " + ((Clothing) product).getColour() + "\n");
            }
        }
    }

    // Method to check if a product ID already exists in the product list.
    public boolean isProductIDAlreadyExists(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return true; // Product with the same ID already exists
            }
        }
        return false; // Product with the given ID does not exist
    }

    // Method to input product details from the user.
    public void productDetails() {
        System.out.print("\nEnter product ID: ");
        pID = myScanner.nextLine();

        // Check if the entered product ID already exists in the list.
        while (isProductIDAlreadyExists(pID)) {
            System.out.println("A product with the same ID already exists! Please enter a different product ID.\n");
            System.out.print("Enter product ID: ");
            pID = myScanner.nextLine();
        }

        System.out.print("Enter product name: ");
        pName = myScanner.nextLine();

        // Input validation for the number of product items.
        while (true) {
            try {
                System.out.print("Enter number of product items: ");
                pItems = myScanner.nextInt();
                myScanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for number of product items! Please enter an integer.\n");
                myScanner.nextLine();
            }
        }

        // Input validation for the product price.
        while (true) {
            try {
                System.out.print("Enter product price(£): ");
                pPrice = myScanner.nextDouble();
                myScanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for product price! Please enter a valid decimal number.\n");
                myScanner.nextLine();
            }
        }
    }

    // Implementation of the saveProductsToFile method from the ShoppingManager interface.
    @Override
    public void saveProductsToFile() {
        Collections.sort(productList);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    writer.write("E," + product.getProductID() + "," + product.getProductName() + ","
                            + product.getAvailableItems() + "," + product.getPrice() + ","
                            + ((Electronics) product).getBrand() + "," + ((Electronics) product).getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    writer.write("C," + product.getProductID() + "," + product.getProductName() + ","
                            + product.getAvailableItems() + "," + product.getPrice() + ","
                            + ((Clothing) product).getSize() + "," + ((Clothing) product).getColour());
                }
                writer.newLine();
            }
            System.out.println("Product list saved to file successfully!\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }
    }

    // Implementation of the readProductsFromFile method from the ShoppingManager interface.
    @Override
    public void readProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) { // Assuming the correct number of attributes for each product type
                    if (parts[0].equals("E")) {
                        Electronics e = new Electronics(parts[1], parts[2], Integer.parseInt(parts[3]),
                                Double.parseDouble(parts[4]), parts[5], parts[6]);
                        productList.add(e);
                    } else if (parts[0].equals("C")) {
                        Clothing c = new Clothing(parts[1], parts[2], Integer.parseInt(parts[3]),
                                Double.parseDouble(parts[4]), parts[5], parts[6]);
                        productList.add(c);
                    }
                }
            }
            System.out.println("Product list loaded from file successfully!\n");
        } catch (Exception e) {
            System.out.println("Error reading products from file: " + e.getMessage());
        }
    }

    // Implementation of the consoleMenu method from the ShoppingManager interface.
    @Override
    public boolean consoleMenu() {
        System.out.println("---------------Westminster Shopping Manager Console Menu---------------\n");
        boolean exit = false;

        try {
            System.out.println("1. To add a new product press '1'");
            System.out.println("2. To delete a product press '2'");
            System.out.println("3. To print the list of products press '3'");
            System.out.println("4. To save product records press '4'");
            System.out.println("5. To load product records press '5'");
            System.out.println("6. To return to main menu press '6'\n");
            System.out.print("Enter your option: ");

            int option = myScanner.nextInt();
            myScanner.nextLine();

            switch (option) {
                case 1:
                    while (true) {
                        System.out.println("Press 'E' if you want to add an Electronic product.");
                        System.out.println("Press 'C' if you want to add a Clothing product.\n");
                        System.out.print("Enter the product type you want to add: ");

                        String pType = myScanner.next().toUpperCase();
                        myScanner.nextLine();

                        if (pType.equals("E")) {
                            this.productDetails();
                            System.out.print("Enter product brand: ");
                            String pBrand = myScanner.nextLine();

                            String pWarranty;
                            while (true) {
                                System.out.print("Enter product warranty period: ");
                                pWarranty = myScanner.nextLine().toLowerCase();

                                // Check if pWarranty is in the warrantyList array
                                boolean validWarranty = false;
                                for (String warranty : warrantyList) {
                                    if (warranty.equals(pWarranty)) {
                                        validWarranty = true;
                                        break;
                                    }
                                }
                                if (validWarranty) {
                                    break;  // Exit the loop if a valid size is entered
                                } else {
                                    System.out.println("""
                                            Please Enter a Valid Warranty Period
                                            (12 weeks, 24 weeks, 36 weeks, 48 weeks, 72 weeks, 2 years, 3 years, 4 years, 5 years)
                                            """);
                                }
                            }

                            Electronics e = new Electronics(pID, pName, pItems, pPrice, pBrand, pWarranty);
                            this.addNewProductToSystem(e);
                            return false;
                        } else if (pType.equals("C")) {
                            this.productDetails();
                            String pSize;
                            while (true) {
                                System.out.print("Enter clothing size: ");
                                pSize = myScanner.nextLine().toUpperCase();

                                // Check if pSize is in the sizeList array
                                boolean validSize = false;
                                for (String size : sizeList) {
                                    if (size.equals(pSize)) {
                                        validSize = true;
                                        break;
                                    }
                                }
                                if (validSize) {
                                    break;  // Exit the loop if a valid size is entered
                                } else {
                                    System.out.println("Please Enter a Valid Clothing Size --> (XS,S,M,L,XL,2XL,3XL)\n");
                                }
                            }

                            System.out.print("Enter clothing colour: ");
                            String pColour = myScanner.nextLine();

                            Clothing c = new Clothing(pID, pName, pItems, pPrice, pSize, pColour);
                            this.addNewProductToSystem(c);
                            return false;
                        } else
                            System.out.println("Invalid input please press 'E' or 'C' \n");
                    }

                case 2:
                    System.out.print("\nEnter product ID to delete: ");
                    String prdID = myScanner.nextLine();
                    this.deleteProductFromSystem(prdID);
                    break;

                case 3:
                    this.printListOfProducts();
                    break;

                case 4:
                    saveProductsToFile();
                    break;

                case 5:
                    readProductsFromFile();
                    break;

                case 6:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid Option!\n");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Invalid Input!\n");
            myScanner.nextLine();
        }
        return exit;
    }
}
