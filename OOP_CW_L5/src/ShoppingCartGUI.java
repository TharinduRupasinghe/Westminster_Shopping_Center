import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {
    private DefaultTableModel shoppingCartTableModel;

    // Constructor for ShoppingCartGUI class
    public ShoppingCartGUI(Map<String, Object[]> shoppingCartItems) {
        setTitle("Shopping Cart");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents(shoppingCartItems);  // Initialize GUI components
        setLocationRelativeTo(null);
    }

    // Method to initialize GUI components
    public void initComponents(Map<String, Object[]> shoppingCartItems) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Table model and table for displaying shopping cart items
        shoppingCartTableModel = new DefaultTableModel();
        shoppingCartTableModel.addColumn("Product");
        shoppingCartTableModel.addColumn("Quantity");
        shoppingCartTableModel.addColumn("Price(£)");

        JTable shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane shoppingCartScrollPane = new JScrollPane(shoppingCartTable);
        shoppingCartTable.getTableHeader().setPreferredSize(new Dimension(40, 60));
        shoppingCartTable.setRowHeight(40);

        // Center align the text in the header
        JTableHeader header = shoppingCartTable.getTableHeader();
        header.setDefaultRenderer(new CenteredTableHeaderRenderer(header.getDefaultRenderer()));

        // Bold the column headings
        Font headerFont = shoppingCartTable.getTableHeader().getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(headerFont.getAttributes());
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        Font boldHeaderFont = new Font(attributes);
        shoppingCartTable.getTableHeader().setFont(boldHeaderFont);

        // Center the text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < shoppingCartTable.getColumnCount(); i++) {
            shoppingCartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        mainPanel.add(shoppingCartScrollPane, BorderLayout.CENTER);

        // Panel for additional information and totals
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(25, 10, 5, 10);

        // Labels for displaying total, discount, and final total
        JLabel totalLabel = createRightAlignedLabel("Total:  ");
        gbc.gridy = 0;
        bottomPanel.add(totalLabel, gbc);

        JLabel discountLabel = createRightAlignedLabel("Three Items in the Same Category Discount (20%):  ");
        gbc.gridy = 1;
        bottomPanel.add(discountLabel, gbc);

        JLabel finalTotalLabel = createBoldRightAlignedLabel("Final Total:  "); // Create bold label
        gbc.gridy = 2;
        bottomPanel.add(finalTotalLabel, gbc);

        // Populate the shopping cart table with items from the shopping cart
        for (Map.Entry<String, Object[]> entry : shoppingCartItems.entrySet()) {
            String productName = (String) entry.getValue()[0];
            int quantity = (int) entry.getValue()[1];
            double price = (double) entry.getValue()[2];

            shoppingCartTableModel.addRow(new Object[]{productName, quantity, price});
        }

        // Calculate and display the total price
        double totalPrice = calculateTotalPrice(shoppingCartTableModel);
        totalLabel.setText("Total:  " + String.format("%.2f", totalPrice) + "£");

        // Calculate and display the discount
        double discount = calculateDiscount(shoppingCartItems);
        discountLabel.setText("Three Items in the Same Category Discount (20%):  " + String.format("%.2f", discount) + "£");

        // Calculate and display the final total
        double finalTotal = totalPrice - discount;
        finalTotalLabel.setText("Final Total:  " + String.format("%.2f", finalTotal) + "£");
    }

    // Method to create a right-aligned label
    public JLabel createRightAlignedLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(500, label.getPreferredSize().height));
        return label;
    }

    // Method to create a bold, right-aligned label
    public JLabel createBoldRightAlignedLabel(String labelText) {
        JLabel label = createRightAlignedLabel(labelText);
        Font boldFont = label.getFont().deriveFont(Font.BOLD);
        label.setFont(boldFont);
        return label;
    }

    // Method to calculate the total price of items in the shopping cart
    public double calculateTotalPrice(DefaultTableModel model) {
        double totalPrice = 0;
        for (int row = 0; row < model.getRowCount(); row++) {
            double price = (double) model.getValueAt(row, 2);
            totalPrice += price;
        }
        return totalPrice;
    }

    // Method to calculate the discount based on the number of items in the same category
    public double calculateDiscount(Map<String, Object[]> shoppingCartItems) {
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Object[] item : shoppingCartItems.values()) {
            String category = (String) item[3];
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + (int) item[1]);
        }

        double discount = 0;
        boolean discountApplied = false;

        for (int count : categoryCount.values()) {
            if (count >= 3 && !discountApplied) {
                discount += 0.2;  // Apply a 20% discount
                discountApplied = true;
            }
        }

        return discount * calculateTotalPrice(shoppingCartTableModel);
    }

    // Inner class for rendering centered text in the table header
    public class CenteredTableHeaderRenderer implements TableCellRenderer {
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
}
