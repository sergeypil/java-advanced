class FineManager {
    private final FineStrategy fineStrategy = new DefaultFineStrategy();

    public double calculateFine(long overdueDays) {
        return fineStrategy.calculateFine(overdueDays);
    }
}