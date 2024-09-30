package book;

/**
 * Comparator class that compares two Book's number of pages
 * @author Ethan Saunders
 */
public class BookPagesComparator implements java.util.Comparator<Book>{

    /**
     * Compares two Books by the number of pages
     * @param a Book 1
     * @param b Book 2
     * @return -1,0,1 if Book's length is greater than, less than, or equal to other Book
     */
    @Override
    public int compare(Book a, Book b) {
        Integer x = a.getLength();
        Integer y = b.getLength();
        return Integer.compare(x, y);
    }
}
