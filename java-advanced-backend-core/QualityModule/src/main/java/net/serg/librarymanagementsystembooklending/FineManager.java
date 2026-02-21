package net.serg.librarymanagementsystembooklending;

/**
 * Default fine calculation strategy: $1 per overdue day.
 */
class FineManager {
    private final FineStrategy fineStrategy = new DefaultFineStrategy();

    public double calculateFine(long overdueDays) {
        return fineStrategy.calculateFine(overdueDays);
    }
}