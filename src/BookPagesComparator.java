public class BookPagesComparator implements java.util.Comparator<Book>{
    @Override
    public int compare(Book a, Book b) {
        Integer x = a.getLength();
        Integer y = b.getLength();
        return Integer.compare(x, y);
    }
}
