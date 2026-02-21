package net.serg.librarymanagementsystembooklending;

interface FineStrategy {
    double calculateFine(long overdueDays);
}