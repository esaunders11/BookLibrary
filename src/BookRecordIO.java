import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Class that reads and write Library files
 * @author Ethan Saunders
 */
public class BookRecordIO {
    
    /**
     * Reads in ArrayList of Books from a File
     * @param filename name of file
     * @return ArrayList of Books
     * @throws FileNotFoundException if file is not found
     */
    public static ArrayList<Book> readBookRecords(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(filename));
        ArrayList<Book> books = new ArrayList<Book>();
        
        while (scan.hasNextLine()) {
            try {
                Book book = readBook(scan.nextLine());

                boolean duplicate = false;

                for (int i = 0; i < books.size(); i++) {
                    Book current = books.get(i);

                    if (current.equals(book)) {
                        duplicate =  true;
                        break;
                    }
                }

                if (!duplicate) {
                    String[] info = BookAPI.searchBook(book.getTitle());
                    book.addInfo(info);
                    books.add(book);
                }

            } catch (IllegalArgumentException e) {
                continue;
            }
        
        }
        scan.close();
        return books;
    }

    /**
     * Creates a Book object from a given line
     * @param line line in a file
     * @return Book object
     */
    private static Book readBook(String line) {
        Scanner scan = new Scanner(line);
        scan.useDelimiter(",");
        
        try {
            String title = scan.next();
            String author = scan.next();
            String genre = scan.next();
            int length = scan.nextInt();

            if (scan.hasNext()) {
                scan.close();
                throw new IllegalArgumentException("Invalid file.");
            }
            else {
                scan.close();
                return new Book(title, author, genre, length);
            }
        } catch (Exception e) {
            scan.close();
            throw new IllegalArgumentException("Invalid file.");
        }
    }

    /**
     * Writes a file of the libraries contents
     * @param filename name of file to write in 
     * @param books ArrayList of Books
     * @throws IOException if unable to write to file
     */
    public static void writeBookRecors(String filename, ArrayList<Book> books) throws IOException {
        PrintStream writer = new PrintStream(new File(filename));

        for (Book b : books) {
            writer.println(b.toString());
        }

        writer.close();
    }
}