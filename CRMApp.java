import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CRMApp {

    // In-memory database simulation using a List
    private static class Customer {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String customerType;

        public Customer(int id, String name, String email, String phone, String address, String customerType) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.customerType = customerType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        @Override
        public String toString() {
            return "ID: " + id + " | Name: " + name + " | Email: " + email + " | Phone: " + phone;
        }
    }

    // Simulating a data access object (DAO) for customer records
    private static class CustomerDAO {
        private static List<Customer> customers = new ArrayList<>();
        private static int nextId = 1;  // For generating unique IDs for new customers

        public static void addCustomer(Customer customer) {
            customer.setId(nextId++);
            customers.add(customer);
        }

        public static List<Customer> getAllCustomers() {
            return customers;
        }
    }

    private static JFrame mainFrame;
    private static JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize the main frame
            mainFrame = new JFrame("CRM System");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(600, 400);
            mainFrame.setLocationRelativeTo(null);

            // Create a text area to display customer information
            textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            mainFrame.add(scrollPane, BorderLayout.CENTER);

            // Create the menu bar
            JMenuBar menuBar = createMenuBar();
            mainFrame.setJMenuBar(menuBar);

            // Show the main frame
            mainFrame.setVisible(true);
        });
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create the Customers menu
        JMenu customerMenu = new JMenu("Customers");

        // Add Customer menu item
        JMenuItem addCustomerItem = new JMenuItem("Add Customer");
        addCustomerItem.addActionListener(e -> openAddCustomerDialog());

        // View Customers menu item
        JMenuItem viewCustomersItem = new JMenuItem("View Customers");
        viewCustomersItem.addActionListener(e -> viewCustomers());

        // Add items to the Customers menu
        customerMenu.add(addCustomerItem);
        customerMenu.add(viewCustomersItem);

        // Add the Customers menu to the menu bar
        menuBar.add(customerMenu);

        return menuBar;
    }

    // Open dialog to add a customer
    private static void openAddCustomerDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField typeField = new JTextField(20);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Customer Type (Active/Inactive):"));
        panel.add(typeField);

        int option = JOptionPane.showConfirmDialog(mainFrame, panel, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String type = typeField.getText();

            Customer newCustomer = new Customer(0, name, email, phone, address, type);
            CustomerDAO.addCustomer(newCustomer);
            JOptionPane.showMessageDialog(mainFrame, "Customer added successfully.");
        }
    }

    // View all customers and display them in the text area
    private static void viewCustomers() {
        List<Customer> customers = CustomerDAO.getAllCustomers();
        textArea.setText("");  // Clear the text area
        if (customers.isEmpty()) {
            textArea.append("No customers found.\n");
        } else {
            for (Customer customer : customers) {
                textArea.append(customer.toString() + "\n");
            }
        }
    }
}