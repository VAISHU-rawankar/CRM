import java.io.*;
import java.util.*;
import javax.swing.*;

public class CRMSystem {

    // Enum for user roles
    enum Role { ADMIN, MANAGER, CUSTOMER_SERVICE }

    // User class for login
    static class User {
        private String username;
        private String password;
        private Role role;

        public User(String username, String password, Role role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Role getRole() {
            return role;
        }
    }

    // Customer class
    static class Customer implements Serializable {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String address;

        public Customer(int id, String name, String email, String phone, String address) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
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

        @Override
        public String toString() {
            return "ID: " + id + " | Name: " + name + " | Email: " + email + " | Phone: " + phone + " | Address: " + address;
        }
    }

    // Database class
    static class CRMDatabase {
        private static List<Customer> customers = new ArrayList<>();
        private static int nextCustomerId = 1;

        public static void addCustomer(Customer customer) {
            customer.setId(nextCustomerId++);
            customers.add(customer);
        }

        public static List<Customer> getCustomers() {
            return customers;
        }

        public static Customer getCustomerById(int id) {
            for (Customer customer : customers) {
                if (customer.getId() == id) {
                    return customer;
                }
            }
            return null;
        }

        public static void deleteCustomer(int id) {
            customers.removeIf(customer -> customer.getId() == id);
        }
    }

    // Main method
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User("admin", "admin123", Role.ADMIN));
        users.add(new User("manager", "manager123", Role.MANAGER));
        users.add(new User("service", "service123", Role.CUSTOMER_SERVICE));

        User loggedInUser = login(users);
        if (loggedInUser != null) {
            showMenu(loggedInUser);
        }
    }

    private static User login(List<User> users) {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = { "Username:", usernameField, "Password:", passwordField };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    return user;
                }
            }
            JOptionPane.showMessageDialog(null, "Invalid credentials!");
        }
        return null;
    }

    private static void showMenu(User user) {
        String[] adminOptions = { "View Reports", "Logout" };
        String[] managerOptions = { "Add Customer", "Edit Customer", "Delete Customer", "Logout" };
        String[] serviceOptions = { "View Customers", "Search Customer", "Logout" };

        String[] options;
        switch (user.getRole()) {
            case ADMIN:
                options = adminOptions;
                break;
            case MANAGER:
                options = managerOptions;
                break;
            case CUSTOMER_SERVICE:
                options = serviceOptions;
                break;
            default:
                return;
        }

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Select an option", "CRM System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == -1 || options[choice].equals("Logout")) {
                JOptionPane.showMessageDialog(null, "Logged out.");
                break;
            }

            handleMenuSelection(user, options[choice]);
        }
    }

    private static void handleMenuSelection(User user, String choice) {
        switch (choice) {
            case "View Reports":
                JOptionPane.showMessageDialog(null, "Total Customers: " + CRMDatabase.getCustomers().size());
                break;
            case "Add Customer":
                addCustomer();
                break;
            case "Edit Customer":
                editCustomer();
                break;
            case "Delete Customer":
                deleteCustomer();
                break;
            case "View Customers":
                viewCustomers();
                break;
            case "Search Customer":
                searchCustomer();
                break;
        }
    }

    private static void addCustomer() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        Object[] message = { "Name:", nameField, "Email:", emailField, "Phone:", phoneField, "Address:", addressField };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Customer customer = new Customer(0, nameField.getText(), emailField.getText(), phoneField.getText(), addressField.getText());
            CRMDatabase.addCustomer(customer);
            JOptionPane.showMessageDialog(null, "Customer added successfully.");
        }
    }

    private static void editCustomer() {
        String input = JOptionPane.showInputDialog("Enter Customer ID to Edit:");
        if (input != null && !input.isEmpty()) {
            int customerId = Integer.parseInt(input);
            Customer customer = CRMDatabase.getCustomerById(customerId);
            if (customer != null) {
                JTextField nameField = new JTextField(customer.getName());
                JTextField emailField = new JTextField(customer.getEmail());
                JTextField phoneField = new JTextField(customer.getPhone());
                JTextField addressField = new JTextField(customer.getAddress());

                Object[] message = { "Name:", nameField, "Email:", emailField, "Phone:", phoneField, "Address:", addressField };

                int option = JOptionPane.showConfirmDialog(null, message, "Edit Customer", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    customer.setName(nameField.getText());
                    customer.setEmail(emailField.getText());
                    customer.setPhone(phoneField.getText());
                    customer.setAddress(addressField.getText());
                    JOptionPane.showMessageDialog(null, "Customer updated successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found.");
            }
        }
    }

    private static void deleteCustomer() {
        String input = JOptionPane.showInputDialog("Enter Customer ID to Delete:");
        if (input != null && !input.isEmpty()) {
            int customerId = Integer.parseInt(input);
            CRMDatabase.deleteCustomer(customerId);
            JOptionPane.showMessageDialog(null, "Customer deleted successfully.");
        }
    }

    private static void viewCustomers() {
        List<Customer> customers = CRMDatabase.getCustomers();
        StringBuilder builder = new StringBuilder();
        for (Customer customer : customers) {
            builder.append(customer.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, builder.toString().isEmpty() ? "No customers found." : builder.toString());
    }

    private static void searchCustomer() {
        String input = JOptionPane.showInputDialog("Enter name or email to search:");
        if (input != null && !input.isEmpty()) {
            List<Customer> customers = CRMDatabase.getCustomers();
            StringBuilder builder = new StringBuilder();
            for (Customer customer : customers) {
                if (customer.getName().contains(input) || customer.getEmail().contains(input)) {
                    builder.append(customer.toString()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, builder.toString().isEmpty() ? "No customers match your search." : builder.toString());
        }
    }
}