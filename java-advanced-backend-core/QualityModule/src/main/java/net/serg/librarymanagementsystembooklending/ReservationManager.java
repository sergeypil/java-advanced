package net.serg.librarymanagementsystembooklending;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages reservations and notifies users when books become available.
 */
class ReservationManager {
    private final Map<String, Queue<User>> reservations = new ConcurrentHashMap<>();

    public void reserveBook(Book book, User user) {
        reservations.computeIfAbsent(book.id(), k -> new LinkedList<>()).add(user);
    }

    public void notifyNextUser(Book book) {
        Queue<User> queue = reservations.get(book.id());
        if (queue != null && !queue.isEmpty()) {
            User nextUser = queue.poll();
            System.out.println("Notification: Book '" + book.title() + "' is now available for " + nextUser.name());
        }
    }
}