// Importing necessary Java Swing and Scanner classes
import javax.swing.*;
import java.util.Scanner;

// Main class that serves as the entry point of the program
public class Main {
    // Main method, the starting point of the program
    public static void main(String[] args) {
        // Creating a Scanner object to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Displaying a welcome message
        System.out.println("                    -----------------||| WELCOME |||----------------");

        // Main program loop
        while (true) {
            // Displaying user options
            System.out.println("\n-> If you are a 'Manager' press [1]");
            System.out.println("-> If you are a 'Customer' press [2]");
            System.out.println("-> If you want to 'Exit' Press [0]\n");

            // Prompting the user to select an option
            System.out.print("Please Select an Option: ");

            // Variable to store user's choice
            int userTypeChoice;

            // Handling potential exceptions when parsing user input
            try {
                // Parsing the user input to an integer
                userTypeChoice = Integer.parseInt(scanner.nextLine());

                // Checking user's choice
                if (userTypeChoice == 0) {
                    // Exiting the program if the user chooses to exit
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                } else if (userTypeChoice == 1 || userTypeChoice == 2) {
                    // Creating instances based on user's choice
                    if (userTypeChoice == 1) {
                        // Creating a ShoppingManager object for the Manager
                        ShoppingManager sManager = new WestminsterShoppingManager();
                        boolean exit = false;

                        // Manager menu loop
                        while (!exit) {
                            exit = sManager.consoleMenu();
                        }
                    } else {
                        // Creating a WestminsterShoppingCenterGUI object for the Customer
                        WestminsterShoppingCenterGUI shoppingCenter = new WestminsterShoppingCenterGUI();

                        // Displaying the GUI on the Swing thread
                        SwingUtilities.invokeLater(() -> {
                            shoppingCenter.setVisible(true);
                        });
                    }
                } else {
                    // Handling invalid user input
                    System.out.println("Invalid option! Please enter [1] for Manager, [2] for Customer, or [0] to Exit.\n");
                }
            } catch (NumberFormatException e) {
                // Handling invalid data type input
                System.out.println("Invalid data type input! Please enter a number (0, 1, or 2).\n");
            }
        }

        // Closing the Scanner to prevent resource leaks
        scanner.close();
    }
}
