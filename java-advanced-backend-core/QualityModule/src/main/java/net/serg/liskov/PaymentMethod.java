package net.serg.liskov;

/**
 * Abstract base class for all payment methods.
 */
public abstract class PaymentMethod {
    /**
     * Validates payment details (e.g., card number, account info).
     * @return true if details are valid, false otherwise.
     */
    public abstract boolean validatePaymentDetails();

    /**
     * Checks if payment can be processed (e.g., all preconditions met).
     * @return true if payment can be processed, false otherwise.
     */
    public boolean canProcessPayment() {
        return validatePaymentDetails();
    }

    /**
     * Processes the payment.
     * @param amount Amount to process.
     * @throws PaymentException if payment cannot be processed.
     */
    public abstract void processPayment(double amount) throws PaymentException;
}