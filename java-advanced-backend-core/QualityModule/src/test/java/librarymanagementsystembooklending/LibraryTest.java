package librarymanagementsystembooklending;

import net.serg.librarymanagementsystembooklending.Book;
import net.serg.librarymanagementsystembooklending.BookFactory;
import net.serg.librarymanagementsystembooklending.Library;
import net.serg.librarymanagementsystembooklending.User;
import net.serg.librarymanagementsystembooklending.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {
    
    @Test
    public void testCheckOutAndReturn() {
        Library library = new Library();
        
        Book book = BookFactory.createBook("BK001", "Test Book");
        User user = UserFactory.createUser("USR001", "Test User");
        
        assertTrue(library.checkOutBook(book, user));
        assertTrue(library.returnBook(book));
    }

    @Test
    public void testReservation() {
        Library library = new Library();
        Book book = BookFactory.createBook("BK002", "Another Book");
        User user1 = UserFactory.createUser("USR001", "User1");
        User user2 = UserFactory.createUser("USR002", "User2");
        
        library.checkOutBook(book, user1);
        
        assertFalse(library.checkOutBook(book, user2)); // Should reserve
    }
}