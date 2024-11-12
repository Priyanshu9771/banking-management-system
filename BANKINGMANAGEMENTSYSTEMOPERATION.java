import java.sql.*;

public class BankManagementSystem {

    private static final Connection connection = DatabaseConnection.getConnection();

    // Create a new account
    public static void createAccount(String name, String email, String phone, double initialDeposit) {
        try {
            String query = "INSERT INTO Customer (name, email, phone, balance) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setDouble(4, initialDeposit);
            statement.executeUpdate();
            System.out.println("Account created successfully with an initial deposit of $" + initialDeposit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deposit money
    public static void deposit(int customerId, double amount) {
        try {
            String query = "UPDATE Customer SET balance = balance + ? WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, amount);
            statement.setInt(2, customerId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Deposit successful!");
                logTransaction(customerId, "Deposit", amount);
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Withdraw money
    public static void withdraw(int customerId, double amount) {
        try {
            // Check if the customer has sufficient balance
            String balanceCheckQuery = "SELECT balance FROM Customer WHERE customer_id = ?";
            PreparedStatement balanceStatement = connection.prepareStatement(balanceCheckQuery);
            balanceStatement.setInt(1, customerId);
            ResultSet rs = balanceStatement.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance >= amount) {
                    // Proceed with withdrawal
                    String query = "UPDATE Customer SET balance = balance - ? WHERE customer_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setDouble(1, amount);
                    statement.setInt(2, customerId);
                    statement.executeUpdate();

                    System.out.println("Withdrawal successful!");
                    logTransaction(customerId, "Withdraw", amount);
                } else {
                    System.out.println("Insufficient funds.");
                }
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check account balance
    public static void checkBalance(int customerId) {
        try {
            String query = "SELECT balance FROM Customer WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("Current balance: $" + balance);
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View transaction history
    public static void viewTransactionHistory(int customerId) {
        try {
            String query = "SELECT transaction_type, amount, transaction_date FROM Transactions WHERE customer_id = ? ORDER BY transaction_date DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();

            System.out.println("Transaction History:");
            while (rs.next()) {
                String type = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                Timestamp date = rs.getTimestamp("transaction_date");
                System.out.printf("Type: %s, Amount: $%.2f, Date: %s\n", type, amount, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Log transaction for tracking
    private static void logTransaction(int customerId, String type, double amount) {
        try {
            String query = "INSERT INTO Transactions (customer_id, transaction_type, amount) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, type);
            statement.setDouble(3, amount);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
