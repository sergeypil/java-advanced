package net.serg.librarymanagementsystembooklending;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles book checkouts, returns, and queries in the library.
 */
public class Library {
    private final ConcurrentHashMap<String, Loan> loans = new ConcurrentHashMap<>();
    private final ReservationManager reservationManager = new ReservationManager();
    private final FineManager fineManager = new FineManager();


    /**
     * Attempts to check out a book to a user.
     * If the book is unavailable, places a reservation for the user.
     * @param book The book to check out.
     * @param user The user who wants to check out the book.
     * @return true if the book was checked out, false if reserved.
     */
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

    /**
     * Returns a book to the library.
     * If the book is overdue, calculates and displays the fine.
     * Notifies the next user in the reservation queue if any.
     * @param book The book to return.
     * @return true if the book was returned, false if it was not checked out.
     */
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