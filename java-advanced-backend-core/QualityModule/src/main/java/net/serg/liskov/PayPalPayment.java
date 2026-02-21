package net.serg.liskov;

/**
 * PayPal payment implementation.
 */
public class PayPalPayment extends PaymentMethod {
    private boolean linkedToBankAccount;

    public PayPalPayment(boolean linkedToBankAccount) {
        this.linkedToBankAccount = linkedToBankAccount;
    }

    @Override
    public boolean validatePaymentDetails() {
        // Validate PayPal account
        return true;
    }

    @Override
    public boolean canProcessPayment() {
        return validatePaymentDetails() && linkedToBankAccount;
    }

    @Override
    public void processPayment(double amount) throws PaymentException {
        if (!canProcessPayment()) {
            throw new PaymentException("PayPal account not linked to a bank account.");
        }
        // Process PayPal payment
    }
}