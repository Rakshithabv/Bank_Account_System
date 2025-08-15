/**
 * Utility class for input validation
 * Demonstrates static methods and validation logic
 */
public class InputValidator {
    
    /**
     * Validate if the amount is positive and not zero
     * @param amount Amount to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0 && !Double.isNaN(amount) && !Double.isInfinite(amount);
    }
    
    /**
     * Validate if the string is not null or empty
     * @param input String to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    /**
     * Validate menu choice
     * @param choice User's menu choice
     * @param minChoice Minimum valid choice
     * @param maxChoice Maximum valid choice
     * @return true if valid, false otherwise
     */
    public static boolean isValidMenuChoice(int choice, int minChoice, int maxChoice) {
        return choice >= minChoice && choice <= maxChoice;
    }
    
    /**
     * Clean and validate numeric input
     * @param input String input to parse
     * @return parsed double value, or -1 if invalid
     */
    public static double parseAmount(String input) {
        try {
            double amount = Double.parseDouble(input.trim());
            if (amount < 0) {
                System.out.println("Error: Amount cannot be negative.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter a valid amount.");
            return -1;
        }
    }
}