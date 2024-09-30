import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import IO.BookRecordIO;
import book.Book;

import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.util.ArrayList;

public class BookRecordIOTest {

    private static final String VALID_BOOKS_FILE = "test_books.txt";
    private static final String OUTPUT_BOOKS_FILE = "output_books.txt";
    private ArrayList<Book> sampleBooks;

    @BeforeEach
    public void setUp() throws IOException {
        // Set up a list of sample books
        sampleBooks = new ArrayList<>();
        sampleBooks.add(new Book("Title1", "Author1", "Fiction", 300));
        sampleBooks.add(new Book("Title2", "Author2", "Non-Fiction", 200));
        sampleBooks.add(new Book("Title3", "Author3", "Mystery", 250));

        // Write sample books to a temporary file
        try (PrintStream out = new PrintStream(new File(VALID_BOOKS_FILE))) {
            for (Book book : sampleBooks) {
                out.println(book.toString());
            }
        }
    }

    @Test
    public void testReadBookRecords() throws FileNotFoundException {
        // Test reading books from a file
        ArrayList<Book> books = BookRecordIO.readBookRecords(VALID_BOOKS_FILE);
        assertEquals(3, books.size(), "Expected 3 books to be read");
        
        assertEquals("Title1", books.get(0).getTitle());
        assertEquals("Author1", books.get(0).getAuthor());
        assertEquals("Fiction", books.get(0).getGenre().getType());
        assertEquals(300, books.get(0).getLength());

        assertEquals("Title2", books.get(1).getTitle());
        assertEquals("Author2", books.get(1).getAuthor());
        assertEquals("Non-Fiction", books.get(1).getGenre().getType());
        assertEquals(200, books.get(1).getLength());

        assertEquals("Title3", books.get(2).getTitle());
        assertEquals("Author3", books.get(2).getAuthor());
        assertEquals("Mystery", books.get(2).getGenre().getType());
        assertEquals(250, books.get(2).getLength());
    }

    @Test
    public void testWriteBookRecords() throws IOException {
        // Write the list of books to a new file
        BookRecordIO.writeBookRecors(OUTPUT_BOOKS_FILE, sampleBooks);

        // Read the file back to verify its contents
        ArrayList<Book> writtenBooks = BookRecordIO.readBookRecords(OUTPUT_BOOKS_FILE);
        assertEquals(3, writtenBooks.size(), "Expected 3 books in the output file");

        for (int i = 0; i < writtenBooks.size(); i++) {
            assertEquals(sampleBooks.get(i), writtenBooks.get(i), "Book " + (i + 1) + " does not match.");
        }
    }

    @Test
    public void testReadBookRecordsInvalid() {
        // Create a file with invalid data
        String invalidFile = "invalid_books.txt";
        try (PrintStream out = new PrintStream(new File(invalidFile))) {
            out.println("Invalid data line");
        } catch (FileNotFoundException e) {
            fail("Setup failed for invalid file.");
        }

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BookRecordIO.readBookRecords(invalidFile);
        });
        assertEquals("Invalid file.", exception.getMessage(), "Expected invalid file exception");
    }
}
