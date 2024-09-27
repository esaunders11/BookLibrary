public class BookGenreComparator implements java.util.Comparator<Book> {
    @Override
    public int compare(Book a, Book b) {
        String x = a.getGenre().getType();
        String y = b.getGenre().getType();

        return x.compareToIgnoreCase(y);
    }
}
