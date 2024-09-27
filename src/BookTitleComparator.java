public class BookTitleComparator implements java.util.Comparator<Book> {
    @Override
    public int compare(Book a, Book b) {
        return a.getTitle().compareToIgnoreCase(b.getTitle());
    }
}
