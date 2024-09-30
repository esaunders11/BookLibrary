package book;

/**
 * Comparator that compares two Books author's names
 * 
 * @author Ethan Saunders
 */
public class BookAuthorComparator implements java.util.Comparator<Book>{

    /**
     * Compares one Book's author's name to another
     * @param a Book 1
     * @param b Book 2
     * @return -1,0,1 depending on if author's name is greater then less than or equal to
     */
    @Override
    public int compare(Book a, Book b) {
        return a.getAuthor().compareToIgnoreCase(b.getAuthor());
    }
}
