import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main Banking Application class
 * Demonstrates OOP concepts, user interaction, and system design
 */
public class BankingApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Account> accounts = new HashMap<>();
    private static List<Transaction> allTransactions = new ArrayList<>();
    private static Account currentAccount = null;
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("    Welcome to SecureBank Banking App    ");
        System.out.println("==========================================");
        
        // Create some sample accounts for demonstration
        createSampleAccounts();
        
        boolean running = true;
        while (running) {
            if (currentAccount == null) {
                running = handleAccountSelection();
            } else {
                running = handleBankingOperations();
            }
        }
        
        System.out.println("\nThank you for using SecureBank!");
        System.out.println("Have a great day!");
        scanner.close();
    }
    
    /**
     * Handle account selection or creation
     */
    private static boolean handleAccountSelection() {
        System.out.println("\n=== Account Management ===");
        System.out.println("1. Login to Existing Account");
        System.out.println("2. Create New Account");
        System.out.println("3. List All Accounts");
        System.out.println("4. Exit Application");
        System.out.print("Choose an option (1-4): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    loginToAccount();
                    break;
                case 2:
                    createNewAccount();
                    break;
                case 3:
                    listAllAccounts();
                    break;
                case 4:
                    return false;
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1-4.");
        }
        
        return true;
    }
    
    /**
     * Handle main banking operations menu
     */
    private static boolean handleBankingOperations() {
        System.out.println("\n=== Banking Operations ===");
        System.out.printf("Logged in as: %s (%s)%n", 
                         currentAccount.getAccountHolderName(), 
                         currentAccount.getAccountNumber());
        
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. View Transaction History");
        System.out.println("6. Account Information");
        System.out.println("7. Logout");
        System.out.println("8. Exit Application");
        System.out.print("Choose an option (1-8): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    currentAccount.displayAccountInfo();
                    break;
                case 7:
                    logout();
                    break;
                case 8:
                    return false;
                default:
                    System.out.println("Invalid choice. Please select 1-8.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1-8.");
        }
        
        return true;
    }
    
    /**
     * Create sample accounts for demonstration
     */
    private static void createSampleAccounts() {
        try {
            Account acc1 = new Account("Rakshitha", 1500.00);
            Account acc2 = new Account("Chitra", 2500.00);
            Account acc3 = new Account("Sanvi", 500.00);
            
            accounts.put(acc1.getAccountNumber(), acc1);
            accounts.put(acc2.getAccountNumber(), acc2);
            accounts.put(acc3.getAccountNumber(), acc3);
            
            System.out.println("Sample accounts created for demonstration:");
            System.out.println("- Rakshitha: " + acc1.getAccountNumber());
            System.out.println("- Chitra: " + acc2.getAccountNumber());
            System.out.println("- Sanvi: " + acc3.getAccountNumber());
        } catch (Exception e) {
            System.out.println("Error creating sample accounts: " + e.getMessage());
        }
    }
    
    /**
     * Login to existing account
     */
    private static void loginToAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim().toUpperCase();
        
        if (accounts.containsKey(accountNumber)) {
            currentAccount = accounts.get(accountNumber);
            System.out.println("Login successful!");
            System.out.printf("Welcome back, %s!%n", currentAccount.getAccountHolderName());
        } else {
            System.out.println("Account not found. Please check your account number.");
        }
    }
    
    /**
     * Create new account
     */
    private static void createNewAccount() {
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine().trim();
        
        if (!InputValidator.isValidString(name)) {
            System.out.println("Invalid name. Please enter a valid name.");
            return;
        }
        
        System.out.print("Enter Initial Deposit Amount: $");
        String amountStr = scanner.nextLine().trim();
        double amount = InputValidator.parseAmount(amountStr);
        
        if (amount < 0) {
            return; // Error message already shown by parseAmount
        }
        
        if (amount < 100) {
            System.out.println("Minimum initial deposit is $100.00");
            return;
        }
        
        try {
            Account newAccount = new Account(name, amount);
            accounts.put(newAccount.getAccountNumber(), newAccount);
            
            // Record initial deposit transaction
            Transaction initialDeposit = new Transaction(
                newAccount.getAccountNumber(),
                Transaction.TransactionType.DEPOSIT,
                amount,
                newAccount.getBalance(),
                "Initial deposit - Account creation"
            );
            allTransactions.add(initialDeposit);
            
            System.out.println("\n=== Account Created Successfully! ===");
            newAccount.displayAccountInfo();
            
            System.out.print("Would you like to login to this account now? (y/n): ");
            String loginChoice = scanner.nextLine().trim().toLowerCase();
            if (loginChoice.equals("y") || loginChoice.equals("yes")) {
                currentAccount = newAccount;
                System.out.println("Logged in successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }
    
    /**
     * List all existing accounts
     */
    private static void listAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.println("\n=== All Accounts ===");
        for (Account account : accounts.values()) {
            System.out.printf("Account: %s | Holder: %s | Balance: $%.2f%n",
                             account.getAccountNumber(),
                             account.getAccountHolderName(),
                             account.getBalance());
        }
    }
    
    /**
     * Check current account balance
     */
    private static void checkBalance() {
        currentAccount.checkBalance();
        
        // Record balance inquiry transaction
        Transaction balanceCheck = new Transaction(
            currentAccount.getAccountNumber(),
            Transaction.TransactionType.BALANCE_INQUIRY,
            0.0,
            currentAccount.getBalance(),
            "Balance inquiry"
        );
        allTransactions.add(balanceCheck);
    }
    
    /**
     * Deposit money to current account
     */
    private static void depositMoney() {
        System.out.print("Enter deposit amount: $");
        String amountStr = scanner.nextLine().trim();
        double amount = InputValidator.parseAmount(amountStr);
        
        if (amount <= 0) {
            return; // Error message already shown by parseAmount
        }
        
        double balanceBefore = currentAccount.getBalance();
        boolean success = currentAccount.deposit(amount);
        
        if (success) {
            // Record successful deposit transaction
            Transaction deposit = new Transaction(
                currentAccount.getAccountNumber(),
                Transaction.TransactionType.DEPOSIT,
                amount,
                currentAccount.getBalance(),
                "Cash deposit"
            );
            allTransactions.add(deposit);
            
            System.out.printf("New balance: $%.2f%n", currentAccount.getBalance());
        }
    }
    
    /**
     * Withdraw money from current account
     */
    private static void withdrawMoney() {
        System.out.print("Enter withdrawal amount: $");
        String amountStr = scanner.nextLine().trim();
        double amount = InputValidator.parseAmount(amountStr);
        
        if (amount <= 0) {
            return; // Error message already shown by parseAmount
        }
        
        boolean success = currentAccount.withdraw(amount);
        
        if (success) {
            // Record successful withdrawal transaction
            Transaction withdrawal = new Transaction(
                currentAccount.getAccountNumber(),
                Transaction.TransactionType.WITHDRAWAL,
                amount,
                currentAccount.getBalance(),
                "Cash withdrawal"
            );
            allTransactions.add(withdrawal);
            
            System.out.printf("New balance: $%.2f%n", currentAccount.getBalance());
        }
    }
    
    /**
     * Transfer money to another account
     */
    private static void transferMoney() {
        System.out.print("Enter target account number: ");
        String targetAccountNumber = scanner.nextLine().trim().toUpperCase();
        
        if (!accounts.containsKey(targetAccountNumber)) {
            System.out.println("Target account not found.");
            return;
        }
        
        if (targetAccountNumber.equals(currentAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }
        
        Account targetAccount = accounts.get(targetAccountNumber);
        System.out.printf("Transfer to: %s (%s)%n", 
                         targetAccount.getAccountHolderName(), 
                         targetAccount.getAccountNumber());
        
        System.out.print("Enter transfer amount: $");
        String amountStr = scanner.nextLine().trim();
        double amount = InputValidator.parseAmount(amountStr);
        
        if (amount <= 0) {
            return; // Error message already shown by parseAmount
        }
        
        boolean success = currentAccount.transferTo(targetAccount, amount);
        
        if (success) {
            // Record transfer transactions for both accounts
            Transaction transferOut = new Transaction(
                currentAccount.getAccountNumber(),
                Transaction.TransactionType.TRANSFER_OUT,
                amount,
                currentAccount.getBalance(),
                "Transfer to " + targetAccount.getAccountHolderName()
            );
            allTransactions.add(transferOut);
            
            Transaction transferIn = new Transaction(
                targetAccount.getAccountNumber(),
                Transaction.TransactionType.TRANSFER_IN,
                amount,
                targetAccount.getBalance(),
                "Transfer from " + currentAccount.getAccountHolderName()
            );
            allTransactions.add(transferIn);
            
            System.out.printf("Your new balance: $%.2f%n", currentAccount.getBalance());
        }
    }
    
    /**
     * View transaction history for current account
     */
    private static void viewTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        System.out.printf("Account: %s - %s%n", 
                         currentAccount.getAccountNumber(), 
                         currentAccount.getAccountHolderName());
        
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getAccountNumber().equals(currentAccount.getAccountNumber())) {
                accountTransactions.add(transaction);
            }
        }
        
        if (accountTransactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("Recent transactions (newest first):");
        System.out.println("─".repeat(80));
        
        // Show last 10 transactions
        int count = 0;
        for (int i = accountTransactions.size() - 1; i >= 0 && count < 10; i--, count++) {
            accountTransactions.get(i).displayTransaction();
        }
        
        if (accountTransactions.size() > 10) {
            System.out.printf("... and %d more transactions%n", accountTransactions.size() - 10);
        }
        
        System.out.println("─".repeat(80));
        System.out.printf("Total transactions: %d%n", accountTransactions.size());
    }
    
    /**
     * Logout from current account
     */
    private static void logout() {
        System.out.printf("Goodbye, %s! You have been logged out.%n", 
                         currentAccount.getAccountHolderName());
        currentAccount = null;
    }
}