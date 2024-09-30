import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import book.Book;
import book.Genre;

public class BookTest {

    private Book book1;
    private Book book2;
    private Genre fiction;
    private Genre nonFiction;

    @BeforeEach
    public void setUp() {
        fiction = new Genre("Fiction");
        nonFiction = new Genre("Non-Fiction");
        book1 = new Book("The Great Gatsby", "Fitzgerald", "Fiction", 180);
        book2 = new Book("1984", "Orwell", "Fiction", 328);
    }

    @Test
    public void testBookCreation() {
        // Verify book object is created correctly
        assertEquals("The Great Gatsby", book1.getTitle(), "Title should be 'The Great Gatsby'");
        assertEquals("Fitzgerald", book1.getAuthor(), "Author should be 'Fitzgerald'");
        assertEquals(fiction, book1.getGenre(), "Genre should be 'Fiction'");
        assertEquals(180, book1.getLength(), "Length should be 180 pages");
    }

    @Test
    public void testSettersAndGetters() {
        // Set new values and verify with getters
        book1.setTitle("Brave New World");
        book1.setAuthor("Huxley");
        book1.setGenre("Non-Fiction");
        book1.setLength(311);

        assertEquals("Brave New World", book1.getTitle());
        assertEquals("Huxley", book1.getAuthor());
        assertEquals(nonFiction, book1.getGenre());
        assertEquals(311, book1.getLength());
    }

    @Test
    public void testInvalidTitle() {
        // Test for invalid title
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Orwell", "Fiction", 328);
        });
        assertEquals("Invalid Title.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            book1.setTitle(null);
        });
        assertEquals("Invalid Title.", exception.getMessage());
    }

    @Test
    public void testInvalidAuthor() {
        // Test for invalid author
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("1984", "", "Fiction", 328);
        });
        assertEquals("Invalid Author.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            book1.setAuthor(null);
        });
        assertEquals("Invalid Author.", exception.getMessage());
    }

    @Test
    public void testInvalidLength() {
        // Test for invalid book length
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("1984", "Orwell", "Fiction", 0);
        });
        assertEquals("Invalid Number of Pages.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            book1.setLength(0);
        });
        assertEquals("Invalid Number of Pages.", exception.getMessage());
    }

    @Test
    public void testToString() {
        // Test the toString method
        assertEquals("The Great Gatsby,Fitzgerald,Fiction,180", book1.toString(), "toString output should match expected format");
    }

    @Test
    public void testEqualsAndHashCode() {
        // Test equals and hashCode
        Book sameBook = new Book("The Great Gatsby", "Fitzgerald", "Fiction", 180);
        assertTrue(book1.equals(sameBook), "Books with same title, author, genre, and length should be equal");
        assertEquals(book1.hashCode(), sameBook.hashCode(), "Books with same attributes should have the same hashCode");

        assertFalse(book1.equals(book2), "Different books should not be equal");
    }
}
    