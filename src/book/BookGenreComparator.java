package book;

/**
 * Comparator class that compares two Books by Genre
 * 
 * @author Ethan Saunders
 */
public class BookGenreComparator implements java.util.Comparator<Book> {

    /**
     * Compares two Books by Genre alphabetically
     * @param a Book 1
     * @param b Book 2
     * @return -1,0,1 depeding if Book's Genre is greater than less than or equal to other Book
     */
    @Override
    public int compare(Book a, Book b) {
        String x = a.getGenre().getType();
        String y = b.getGenre().getType();

        return x.compareToIgnoreCase(y);
    }
}
