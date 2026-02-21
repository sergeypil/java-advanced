package net.serg.liskov;

/**
 * Credit card payment implementation.
 */
public class CreditCardPayment extends PaymentMethod {
    @Override
    public boolean validatePaymentDetails() {
        // Validate credit card details
        return true;
    }

    @Override
    public void processPayment(double amount) throws PaymentException {
        // Process credit card payment
    }
}