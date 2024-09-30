package book;

/**
 * Comparator class that compares two Books by Title alphabetically
 * @author Ethan Saunders
 */
public class BookTitleComparator implements java.util.Comparator<Book> {

    /**
     * Compares two Books by their Titles alphabetically
     * @param a Book 1
     * @param b Book 2
     * @return -1,0,1 if Book's title is greater than, less than, or equal to other Book
     */
    @Override
    public int compare(Book a, Book b) {
        return a.getTitle().compareToIgnoreCase(b.getTitle());
    }
}
