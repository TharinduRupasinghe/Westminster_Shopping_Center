import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WestminsterShoppingCenterGUI extends JFrame {
    private JComboBox<String> productTypeComboBox;
    private DefaultTableModel productTableModel;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private int itemsAvailable;

    // Map to store shopping cart items directly in WestminsterShoppingCenterGUI
    private final Map<String, Object[]> shoppingCart = new HashMap<>();

    // Constructor for the WestminsterShoppingCenterGUI class
    public WestminsterShoppingCenterGUI() {
        setTitle("Westminster Shopping Center");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();  // Initialize GUI components
        loadProducts("productDataFile.txt");  // Load products from file
    }

    // Method to initialize GUI components
    public void initComponents() {
        // Panels for organizing components
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JPanel topSubPanel = new JPanel(new FlowLayout());
        topSubPanel.setBackground(Color.WHITE);
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        // Adding panels to the JFrame
        topPanel.add(topSubPanel, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Components in the top panel
        topSubPanel.add(new JLabel("Select Product Category: "));
        String[] productTypes = {"All", "Electronics", "Clothing"};
        productTypeComboBox = new JComboBox<>(productTypes);
        productTypeComboBox.addActionListener(e -> filterProducts());
        topSubPanel.add(productTypeComboBox);

        // Table model and table for displaying products
        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Product ID");
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Price(£)");
        productTableModel.addColumn("Info");

        productTable = new JTable(productTableModel);
        JScrollPane productTableScrollPane = new JScrollPane(productTable);
        productTable.getTableHeader().setPreferredSize(new Dimension(30, 50));
        productTable.setRowHeight(25);
//        for (int i = 0; i < productTableModel.getColumnCount(); i++) {
//            productTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer(3)); // Assuming availability is in the 4th column
//        }

        // Center align the text in the header
        JTableHeader header = productTable.getTableHeader();
        header.setDefaultRenderer(new CenteredTableHeaderRenderer(header.getDefaultRenderer()));

        Font headerFont = productTable.getTableHeader().getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(headerFont.getAttributes());
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        Font boldHeaderFont = new Font(attributes);
        productTable.getTableHeader().setFont(boldHeaderFont);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Constraints for placing the table in the center panel
        GridBagConstraints gbcTable = new GridBagConstraints();
        gbcTable.gridx = 0;
        gbcTable.gridy = 0;
        gbcTable.insets = new Insets(45, 90, -100, 90);
        gbcTable.weightx = 1.0;
        gbcTable.weighty = 1.0;
        gbcTable.fill = GridBagConstraints.BOTH;

        // Constraints for placing the text area in the center panel
        GridBagConstraints gbcTextArea = new GridBagConstraints();
        gbcTextArea.gridx = 0;
        gbcTextArea.gridy = 50;
        gbcTextArea.insets = new Insets(140, 130, 50, 50); // Add top margin for spacing
        gbcTextArea.anchor = GridBagConstraints.WEST; // Align to the left
        gbcTextArea.fill = GridBagConstraints.HORIZONTAL;

        // Adding components to the center panel
        centerPanel.add(productTableScrollPane, gbcTable);

        productDetailsTextArea = new JTextArea();
        centerPanel.add(productDetailsTextArea, gbcTextArea);

        // Listener for handling selection changes in the product table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    displaySelectedRowDetails(productTable.getSelectedRow());
                }
            }
        });

        // Button for opening the shopping cart window
        JButton shoppingCartButton = new JButton("Shopping Cart");
        topPanel.add(shoppingCartButton, BorderLayout.EAST);
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartWindow();
            }
        });

        // Button for adding the selected product to the shopping cart
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        shoppingCartButton.setSize(30,60);
        bottomPanel.add(addToCartButton);
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
    }

    // Method to load products from a file
    public void loadProducts(String filePath) {
        productTableModel.setRowCount(0);  // Clear existing rows

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String category = parts[0].equals("E") ? "Electronics" : "Clothing";
                String productId = parts[1];
                String name = parts[2];
                itemsAvailable = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);

                String info = getInfo(category, parts);

                productTableModel.addRow(new Object[]{productId, name, category, price, info});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to extract and format additional information based on product category
    public String getInfo(String category, String[] parts) {
        StringBuilder infoBuilder = new StringBuilder();

        if (category.equals("Electronics")) {
            infoBuilder.append(parts[5]).append(", ");
            infoBuilder.append(parts[6]).append(" warranty");
        } else {
            infoBuilder.append(parts[5]).append(", ");
            infoBuilder.append(parts[6]);
        }

        return infoBuilder.toString();
    }

    // Method to filter products based on the selected category
    public void filterProducts() {
        String selectedCategory = (String) productTypeComboBox.getSelectedItem();

        if (selectedCategory.equals("All")) {
            loadProducts("productDataFile.txt");
        } else {
            List<String> filteredProducts = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("productDataFile.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String category = line.split(",")[0].equals("E") ? "Electronics" : "Clothing";
                    if ((category.equals("Electronics") && selectedCategory.equals("Electronics")) ||
                            (category.equals("Clothing") && selectedCategory.equals("Clothing"))) {
                        filteredProducts.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            updateTable(filteredProducts);
        }
    }

    // Method to update the table with a new set of products
    public void updateTable(List<String> products) {
        productTableModel.setRowCount(0);  // Clear existing rows

        for (String product : products) {
            String[] parts = product.split(",");
            String category = parts[0].equals("E") ? "Electronics" : "Clothing";
            String productId = parts[1];
            String name = parts[2];
            double price = Double.parseDouble(parts[4]);

            String info = getInfo(category, parts);

            productTableModel.addRow(new Object[]{productId, name, category, price, info});
        }
    }

    // Method to display details of the selected row in the text area
    public void displaySelectedRowDetails(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < productTableModel.getRowCount()) {
            StringBuilder detailsDisplay = new StringBuilder();
            for (int colIndex = 0; colIndex < productTableModel.getColumnCount(); colIndex++) {
                String columnName = productTableModel.getColumnName(colIndex);
                Object columnValue = productTableModel.getValueAt(rowIndex, colIndex);

                if (columnName.equals("Info")) {
                    // Extract and display relevant information based on the category
                    String category = (String) productTableModel.getValueAt(rowIndex, 2);
                    String[] infoParts = columnValue.toString().split(", ");

                    if (category.equals("Electronics")) {
                        detailsDisplay.append("Brand: ").append(infoParts[0]).append("\n\n");
                        detailsDisplay.append("Warranty: ").append(infoParts[1]).append("\n\n");
                    } else {
                        detailsDisplay.append("Size: ").append(infoParts[0]).append("\n\n");
                        detailsDisplay.append("Color: ").append(infoParts[1]).append("\n\n");
                    }
                } else {
                    detailsDisplay.append(columnName)
                            .append(": ")
                            .append(columnValue)
                            .append("\n\n");
                }

                // Check if the column is "Product ID" and update itemsAvailable accordingly
                if (columnName.equals("Product ID")) {
                    String productId = (String) columnValue;
                    // Assuming productId is unique and matches the product file
                    updateItemsAvailable(productId, 0);  // Pass the quantity parameter (0 in this case)
                }
            }

            // Add the "Items Available" information as the last row in the text area
            detailsDisplay.append("Items Available: ").append(itemsAvailable);

            productDetailsTextArea.setText(">>> Selected Product - Details <<<\n\n" + detailsDisplay.toString());
        }
    }

    public void updateItemsAvailable(String productId, int quantity) {
        // Implement logic to update itemsAvailable based on productId
        // For simplicity, assuming itemsAvailable is retrieved from the product file

        try (BufferedReader br = new BufferedReader(new FileReader("productDataFile.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("tempProductDataFile.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String currentProductId = parts[1];

                if (productId.equals(currentProductId)) {
                    // Assuming itemsAvailable is stored in the 4th column of the product file
                    itemsAvailable = Integer.parseInt(parts[3]);
                    itemsAvailable -= quantity;  // Reduce itemsAvailable by the selected quantity
                    parts[3] = Integer.toString(itemsAvailable);
                }

                // Write the updated line to the temporary file
                bw.write(String.join(",", parts));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file
        File tempFile = new File("tempProductDataFile.txt");
        File originalFile = new File("productDataFile.txt");
        tempFile.renameTo(originalFile);
    }

    // Method to add the selected product to the shopping cart
    public void addToCart() {
        int selectedRowIndex = productTable.getSelectedRow();
        if (selectedRowIndex >= 0) {
            String productId = (String) productTable.getValueAt(selectedRowIndex, 0);
            String productName = (String) productTable.getValueAt(selectedRowIndex, 1);
            double productPrice = (double) productTable.getValueAt(selectedRowIndex, 3);
            String category = (String) productTable.getValueAt(selectedRowIndex, 2);
            String info = (String) productTable.getValueAt(selectedRowIndex, 4);

            // Check if there are available items
            if (itemsAvailable > 0) {
                // Check if the product is already in the shopping cart
                if (shoppingCart.containsKey(productId)) {
                    // If yes, update quantity and price
                    Object[] cartItem = shoppingCart.get(productId);
                    int quantity = (int) cartItem[1];
                    double totalPrice = (double) cartItem[2];
                    quantity++;
                    totalPrice += productPrice;
                    cartItem[1] = quantity;
                    cartItem[2] = totalPrice;
                } else {
                    // If not, add a new entry to the shopping cart map
                    shoppingCart.put(productId, new Object[]{"ID- " + productId + " (" + productName + ", " + info + ")", 1, productPrice, category});
                }

                // Update itemsAvailable and display the updated value dynamically
                updateItemsAvailable(productId, 1);
                displayItemsAvailable();

                JOptionPane.showMessageDialog(this, "Product added to the shopping cart");
            } else {
                JOptionPane.showMessageDialog(this, "No items available for this product!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add to the shopping cart !");
        }
    }

    // Method to display the updated "Items Available" value dynamically
    public void displayItemsAvailable() {
        // Clear previous itemsAvailable information
        productDetailsTextArea.setText(">>> Selected Product - Details <<<\n\n");

        // Display details of the selected row
        int selectedRowIndex = productTable.getSelectedRow();
        displaySelectedRowDetails(selectedRowIndex);
    }

    // Method to open the shopping cart window
    public void openShoppingCartWindow() {
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart);
        shoppingCartGUI.setVisible(true);
    }

    // Inner class for rendering centered text in the table header
    public static class CenteredTableHeaderRenderer implements TableCellRenderer {
        private final TableCellRenderer defaultRenderer;

        public CenteredTableHeaderRenderer(TableCellRenderer defaultRenderer) {
            this.defaultRenderer = defaultRenderer;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel headerLabel = (JLabel) defaultRenderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

            return headerLabel;
        }
    }

//    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
//        private final int availabilityColumnIndex;
//
//        public CustomTableCellRenderer(int availabilityColumnIndex) {
//            this.availabilityColumnIndex = availabilityColumnIndex;
//        }
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//                                                       boolean hasFocus, int row, int column) {
//            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//            // Check the availability column
//            int availability = Integer.parseInt(table.getValueAt(row, availabilityColumnIndex).toString());
//
//            // Set the background color based on availability
//            if (availability < 3) {
//                component.setBackground(Color.RED);
//                component.setForeground(Color.WHITE); // Set text color to white for better visibility
//            } else {
//                // Reset the default background color
//                component.setBackground(table.getBackground());
//                component.setForeground(table.getForeground());
//            }
//
//            return component;
//        }
//    }
}
