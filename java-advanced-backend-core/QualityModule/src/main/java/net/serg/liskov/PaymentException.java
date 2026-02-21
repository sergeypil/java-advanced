package net.serg.liskov;

/**
 * Exception for payment processing errors.
 */
public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}