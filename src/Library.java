import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Library Class. Holds Books that can be added, removed and exported
 * 
 * @author Ethan Saunders
 */
public class Library {
    /** List of all the Library Books */
    private ArrayList<Book> books;
    /** List of imported Books */
    private ArrayList<Book> importLibrary;

    /**
     * Creates Library with default label and emtpy books
     * @param file name of file
     * @throws IllegalArgumentException if file is invalid
     */
    public Library() {
        setBooks();
    }

    /**
     * Imports a library from a file and adds it to the table of books
     * @param file filename
     */
    public void addLibrary(String file) {
        try {
            this.importLibrary = BookRecordIO.readBookRecords(file);
            for (Book b : importLibrary) {
                this.books.add(b);
            }
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
     * Sets library to an empty ArrayList
     */
    public void setBooks() {
        this.books = new ArrayList<Book>();
    }

    /**
     * Returns the Books ArrayList
     * @return ArrayList of Books
     */
    public ArrayList<Book> getBooks() {
        return books;
    }   

    public Book getBook(String title, String author) {
        for (Book b : books) {
            if (b.getTitle().equals(title) && b.getAuthor().equals(author)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Returns a 2D string of the Library
     * @return 2D string of library
     */
    public String[][] getBookString() {
        String[][] library = new String[books.size()][4];
        for (int i = 0; i < library.length; i++) {
            library[i][0] = books.get(i).getTitle();
            library[i][1] = books.get(i).getAuthor();
            library[i][2] = "" + books.get(i).getGenre();
            library[i][3] = "" + books.get(i).getLength();
        }
        return library;
    }

    /**
     * Resets Library to emtpy ArrayList
     */
    public void resetLibrary() {
        this.books.clear();
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

    /**
     * Sorts the Library alphabetically by Authors last name
     */
    public static void sortBooksAuthor(Library L) {
        Collections.sort(L.getBooks(), new BookAuthorComparator());
    }

    /**
     * Sorts the Library alphabetically by Title
     */
    public static void sortBooksTitle(Library L) {
        Collections.sort(L.getBooks(), new BookTitleComparator());
    }

    /**
     * Sorts the Library by number of pages
     */
    public static void sortBooksLength(Library L) {
        Collections.sort(L.getBooks(), new BookPagesComparator());
    }

    /**
     * Sorts the library by Genre Alphabetically
     */
    public static void sortBooksGenre(Library L) {
        Collections.sort(L.getBooks(), new BookGenreComparator());
    }

    /**
     * Hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((books == null) ? 0 : books.hashCode());
        result = prime * result + ((importLibrary == null) ? 0 : importLibrary.hashCode());
        return result;
    }

    /**
     * Equals method if two Libraries are the same
     * @param obj another object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Library other = (Library) obj;
        if (books == null) {
            if (other.books != null)
                return false;
        } else if (!books.equals(other.books))
            return false;
        if (importLibrary == null) {
            if (other.importLibrary != null)
                return false;
        } else if (!importLibrary.equals(other.importLibrary))
            return false;
        return true;
    }

    

}