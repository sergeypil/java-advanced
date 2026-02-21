import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Loan {
    private final Book book;
    private final User user;
    private final LocalDate checkoutDate;
    private final int loanPeriodDays = 14;

    public Loan(Book book, User user, LocalDate checkoutDate) {
        this.book = book;
        this.user = user;
        this.checkoutDate = checkoutDate;
    }
    public long getOverdueDays() {
        LocalDate dueDate = checkoutDate.plusDays(loanPeriodDays);
        return Math.max(0, ChronoUnit.DAYS.between(dueDate, LocalDate.now()));
    }
}