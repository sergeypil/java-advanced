package net.serg.librarymanagementsystembooklending;

public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();
        Book book = BookFactory.createBook("BK001", "Effective Java");
        User user1 = UserFactory.createUser("USR001", "Alice");
        User user2 = UserFactory.createUser("USR002", "Bob");

        library.checkOutBook(book, user1); // Alice checks out
        library.checkOutBook(book, user2); // Bob reserves
        library.returnBook(book);          // Alice returns, Bob notified
    }
}