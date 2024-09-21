import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void testValidGenre() {
        // Test with a valid genre
        Genre genre = new Genre("Fiction");
        assertEquals("Fiction", genre.getType(), "Genre should be 'Fiction'");

        genre = new Genre("Mystery");
        assertEquals("Mystery", genre.getType(), "Genre should be 'Mystery'");
    }

    @Test
    public void testInvalidGenre() {
        // Test with an invalid genre
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Genre("InvalidGenre");
        });
        assertEquals("Invalid Genre.", exception.getMessage(), "Should throw 'Invalid Genre.' message");

        // Test with an empty genre
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Genre("");
        });
        assertEquals("Invalid Genre.", exception.getMessage(), "Should throw 'Invalid Genre.' message");

        // Test with null genre
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Genre(null);
        });
        assertEquals("Invalid Genre.", exception.getMessage(), "Should throw 'Invalid Genre.' message");
    }

    @Test
    public void testSetTypeValid() {
        // Test setting the genre with a valid value
        Genre genre = new Genre("Fiction");
        genre.setType("Biography");
        assertEquals("Biography", genre.getType(), "Genre should be 'Biography'");
    }

    @Test
    public void testSetTypeInvalid() {
        // Test setting the genre with an invalid value
        Genre genre = new Genre("Fiction");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            genre.setType("InvalidGenre");
        });
        assertEquals("Invalid Genre.", exception.getMessage(), "Should throw 'Invalid Genre.' message");
    }
}
