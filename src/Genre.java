/**
 * Genre class
 * 
 * @author Ethan Saunders
 */
public class Genre {
    /** List of available genres */
    public static final String[] genres = {"Fiction", "Non-Fiction", "Romance", "Mystery", "Fantasy", "Sci-Fi", "Historical Fiction",
    "Western", "Biography", "Memior", "Children", "Horror", "Classic"};
    /** Type of genre */
    private String type;

    /**
     * Creates a genre object
     * @param type Type of genre.
     */
    public Genre(String type) {
        setType(type);
    }

    /**
     * Sets the type of genre to given genre only if genre is in available list
     * @param type Type of genre
     * @throws IllegalArgumentException if genre is not available
     */
    public void setType(String type) {
        for (String g : genres) {
            if (type.equals(g)){
                this.type = type;
            }
        }
        if (this.type == null || "".equals(type)) {
            throw new IllegalArgumentException("Invalid Genre.");
        }
    }

    /**
     * Returns objects type
     * @return Genre String
     */
    public String getType() {
        return this.type;
    }

    /**
     * Generates String of Genre
     */
    @Override
    public String toString() {
        return type;
    }

    
}