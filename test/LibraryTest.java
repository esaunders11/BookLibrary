import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book sampleBook1;
    private Book sampleBook2;
    private final String validFile = "Library.txt";
    private final String invalidFile = "invalid_file.txt";

    @BeforeEach
    public void setUp() {
        // Initialize sample books and a library instance
        Genre genre = new Genre("Fiction");
        sampleBook1 = new Book("Book One", "Author A", "genre", 300);
        sampleBook2 = new Book("Book Two", "Author B", "genre", 250);
        library = new Library(validFile);  // Assuming valid_books.txt exists and has valid book data
    }

    @Test
    public void testConstructorWithValidFile() {
        // Test if library is created successfully with valid file
        assertNotNull(library);
        assertFalse(library.getBooks().length == 0, "Library should not be empty after loading valid file.");
    }

    @Test
    public void testConstructorWithInvalidFile() {
        // Test exception when using an invalid file
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Library(invalidFile);  // Assuming invalid_books.txt is an invalid file
        });
        assertEquals("Invalid file.", exception.getMessage());
    }

    @Test
    public void testAddBook() {
        // Add a valid book and verify it's added
        assertTrue(library.addBook(sampleBook1), "Book should be added successfully.");
        String[][] books = library.getBooks();
        assertEquals("Book One", books[books.length - 1][0], "The last book in the library should be 'Book One'.");

        // Test adding a duplicate book
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(sampleBook1);
        });
        assertEquals("This book is already in the library.", exception.getMessage());
    }

    @Test
    public void testRemoveBook() {
        library.addBook(sampleBook1);
        String[][] booksBefore = library.getBooks();
        int initialSize = booksBefore.length;

        // Test removing a book
        assertTrue(library.removeBook(0), "Book should be removed successfully.");
        String[][] booksAfter = library.getBooks();
        assertEquals(initialSize - 1, booksAfter.length, "Library should have one less book after removal.");
    }

    @Test
    public void testRemoveBookInvalidIndex() {
        // Test removing book with invalid index
        assertFalse(library.removeBook(100), "Should return false when trying to remove a book with invalid index.");
    }

    @Test
    public void testResetLibrary() {
        // Add a book, then reset the library and check if it's empty
        library.addBook(sampleBook1);
        library.resetLibrary();
        assertEquals(0, library.getBooks().length, "Library should be empty after reset.");
    }

    @Test
    public void testExportLibrary() throws IOException {
        // Add some books and export the library to a file
        library.addBook(sampleBook1);
        library.addBook(sampleBook2);

        String exportFile = "test_export_books.txt";
        library.exportLibrary(exportFile);
        File file = new File(exportFile);
        
        assertTrue(file.exists(), "Exported file should exist.");
        
        // Clean up test file
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testExportLibraryInvalidFile() {
        // Test exception when exporting to an invalid file
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.exportLibrary("/invalid_path/export_books.txt");
        });
        assertEquals("The file cannot be saved.", exception.getMessage());
    }

    @Test
    public void testSetLabel() {
        library.setLabel("My New Library");
        assertEquals("My New Library", library.getLabel(), "Library label should be updated.");
        
        // Test invalid label
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.setLabel(null);
        });
        assertEquals("Invalid Title.", exception.getMessage());
    }
}
