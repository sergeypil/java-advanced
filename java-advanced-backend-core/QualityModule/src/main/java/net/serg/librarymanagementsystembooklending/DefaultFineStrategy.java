package net.serg.librarymanagementsystembooklending;

class DefaultFineStrategy implements FineStrategy {
    public double calculateFine(long overdueDays) {
        return overdueDays * 1.0; // $1 per day
    }
}