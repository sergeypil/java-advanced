package net.serg.liskov;

/**
 * Payment processor using the Template Method pattern.
 */
public class PaymentProcessor {
    public void makePayment(PaymentMethod payment, double amount) {
        if (payment.canProcessPayment()) {
            try {
                payment.processPayment(amount);
            } catch (PaymentException e) {
                System.out.println("Payment failed: " + e.getMessage());
            }
        } else {
            System.out.println("Payment details invalid or preconditions not met.");
        }
    }
}