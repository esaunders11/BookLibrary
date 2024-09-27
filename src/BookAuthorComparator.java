public class BookAuthorComparator implements java.util.Comparator<Book>{
    @Override
    public int compare(Book a, Book b) {
        return a.getAuthor().compareToIgnoreCase(b.getAuthor());
    }
}
