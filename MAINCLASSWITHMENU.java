import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Bank Management System ===");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.print("Enter email: ");
                    String email = scanner.next();
                    System.out.print("Enter phone: ");
                    String phone = scanner.next();
                    System.out.print("Enter initial deposit: ");
                    double deposit = scanner.nextDouble();
                    BankManagementSystem.createAccount(name, email, phone, deposit);
                    break;
                case 2:
                    System.out.print("Enter customer ID: ");
                    int customerIdDeposit = scanner.nextInt();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    BankManagementSystem.deposit(customerIdDeposit, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter customer ID: ");
                    int customerIdWithdraw = scanner.nextInt();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    BankManagementSystem.withdraw(customerIdWithdraw, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter customer ID: ");
                    int customerIdBalance = scanner.nextInt();
                    BankManagementSystem.checkBalance(customerIdBalance);
                    break;
                case 5:
                    System.out.print("Enter customer ID: ");
                    int customerIdHistory = scanner.nextInt();
                    BankManagementSystem.viewTransactionHistory(customerIdHistory);
                    break;
                case 6:
                    System.out.println("Exiting system. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
