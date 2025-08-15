/**
 * Account class representing a bank account with basic operations
 * Demonstrates encapsulation and proper data validation
 */
public class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private static int accountCounter = 1000; // For generating unique account numbers
    
    // Constructor
    public Account(String accountHolderName, double initialBalance) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        
        this.accountNumber = "ACC" + (++accountCounter);
        this.accountHolderName = accountHolderName.trim();
        this.balance = initialBalance;
    }
    
    // Getter methods (Encapsulation)
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    /**
     * Deposit money into the account
     * @param amount Amount to deposit
     * @return true if successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (!InputValidator.isValidAmount(amount)) {
            System.out.println("Error: Invalid deposit amount. Amount must be positive.");
            return false;
        }
        
        if (amount > 100000) {
            System.out.println("Error: Daily deposit limit exceeded. Maximum deposit: $100,000");
            return false;
        }
        
        this.balance += amount;
        System.out.printf("Successfully deposited $%.2f%n", amount);
        return true;
    }
    
    /**
     * Withdraw money from the account
     * @param amount Amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (!InputValidator.isValidAmount(amount)) {
            System.out.println("Error: Invalid withdrawal amount. Amount must be positive.");
            return false;
        }
        
        if (amount > balance) {
            System.out.printf("Error: Insufficient funds. Current balance: $%.2f%n", balance);
            return false;
        }
        
        if (amount > 10000) {
            System.out.println("Error: Daily withdrawal limit exceeded. Maximum withdrawal: $10,000");
            return false;
        }
        
        this.balance -= amount;
        System.out.printf("Successfully withdrawn $%.2f%n", amount);
        return true;
    }
    
    /**
     * Display current account balance
     */
    public void checkBalance() {
        System.out.printf("Current Balance: $%.2f%n", balance);
    }
    
    /**
     * Display account information
     */
    public void displayAccountInfo() {
        System.out.println("=== Account Information ===");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.printf("Current Balance: $%.2f%n", balance);
        System.out.println("===========================");
    }
    
    /**
     * Transfer money to another account
     * @param targetAccount Account to transfer to
     * @param amount Amount to transfer
     * @return true if successful, false otherwise
     */
    public boolean transferTo(Account targetAccount, double amount) {
        if (targetAccount == null) {
            System.out.println("Error: Target account not found.");
            return false;
        }
        
        if (!InputValidator.isValidAmount(amount)) {
            System.out.println("Error: Invalid transfer amount. Amount must be positive.");
            return false;
        }
        
        if (amount > balance) {
            System.out.printf("Error: Insufficient funds for transfer. Current balance: $%.2f%n", balance);
            return false;
        }
        
        if (amount > 5000) {
            System.out.println("Error: Transfer limit exceeded. Maximum transfer: $5,000");
            return false;
        }
        
        // Perform the transfer
        this.balance -= amount;
        targetAccount.balance += amount;
        
        System.out.printf("Successfully transferred $%.2f to %s (Account: %s)%n", 
                         amount, targetAccount.getAccountHolderName(), targetAccount.getAccountNumber());
        return true;
    }
}