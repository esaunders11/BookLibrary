import java.io.IOException;
import java.util.ArrayList;

/**
 * Library Class. Holds Books that can be added, removed and exported
 * 
 * @author Ethan Saunders
 */
public class Library {
    /** Title of Library */
    private String label;
    /** List of all the Library Books */
    private ArrayList<Book> books;

    /**
     * Creates Library with default label and imported books
     * @param file name of file
     * @throws IllegalArgumentException if file is invalid
     */
    public Library(String file) {
        this.books = new ArrayList<Book>();
        this.label = "Library";
        try {
            this.books = BookRecordIO.readBookRecords(file);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file.");
        }
    }

    /**
     * Add book to the library
     * @param book Book object
     * @return boolean if Book was successfully added
     * @throws IllegalArgumentException if Book is already in the Library
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        for (Book b : books) {
            if (b.equals(book)) {
                throw new IllegalArgumentException("This book is already in the library.");
            }
        }
        books.add(book);
        return true;
    }

    /**
     * Removes Book at given index
     * @param idx index of Book you want to remove
     * @return boolean if Book was successfully removed
     */
    public boolean removeBook(int idx) {
        try {
            books.remove(idx);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Resets Library to emtpy ArrayList
     */
    public void resetLibrary() {
        this.books = new ArrayList<Book>();
    }

    /**
     * Sets the label of the Library
     * @param label label of Library
     */
    public void setLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Invalid Title.");
        }
        this.label = label;
    }

    /**
     * Return the label of the Library
     * @return label of Library
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Exports the Libraries contents into a file
     * @param file name of file
     * @throws IllegalArgumentException if unable to write to file
     */
    public void exportLibrary(String file) {
        try {
            BookRecordIO.writeBookRecors(file, books);
        } catch (IOException e) {
            throw new IllegalArgumentException("The file cannot be saved.");
        }
    }

}