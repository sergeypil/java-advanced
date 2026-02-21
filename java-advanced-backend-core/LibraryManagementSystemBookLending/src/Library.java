import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public class Library {
    private final ConcurrentHashMap<String, Loan> loans = new ConcurrentHashMap<>();
    private final ReservationManager reservationManager = new ReservationManager();
    private final FineManager fineManager = new FineManager();

    public boolean checkOutBook(Book book, User user) {
        if (loans.containsKey(book.id())) {
            reservationManager.reserveBook(book, user);
            System.out.println("Book unavailable. Reservation placed for " + user.name());
            return false;
        }
        loans.put(book.id(), new Loan(book, user, LocalDate.now()));
        System.out.println("Book checked out to " + user.name());
        return true;
    }

    public boolean returnBook(Book book) {
        Loan loan = loans.remove(book.id());
        if (loan == null) {
            System.out.println("This book was not checked out.");
            return false;
        }
        long overdueDays = loan.getOverdueDays();
        if (overdueDays > 0) {
            double fine = fineManager.calculateFine(overdueDays);
            System.out.println("Book returned. Overdue by " + overdueDays + " days. Fine: $" + fine);
        } else {
            System.out.println("Book returned on time.");
        }
        reservationManager.notifyNextUser(book);
        return true;
    }
}