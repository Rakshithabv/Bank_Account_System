import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction class to represent individual transactions
 * Demonstrates composition and data encapsulation
 */
public class Transaction {
    private String transactionId;
    private String accountNumber;
    private TransactionType type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String description;
    private static int transactionCounter = 1000;
    
    // Enum for transaction types
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT, BALANCE_INQUIRY
    }
    
    // Constructor
    public Transaction(String accountNumber, TransactionType type, double amount, 
                      double balanceAfter, String description) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description != null ? description : "";
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Display transaction details
     */
    public void displayTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("ID: %s | %s | %s | $%.2f | Balance: $%.2f | %s%n",
                         transactionId,
                         timestamp.format(formatter),
                         type.toString().replace("_", " "),
                         amount,
                         balanceAfter,
                         description);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s - %s: $%.2f (Balance: $%.2f)",
                           timestamp.format(formatter),
                           type.toString().replace("_", " "),
                           amount,
                           balanceAfter);
    }
}