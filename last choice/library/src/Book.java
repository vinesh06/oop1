import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private String genre;
    private List<Review> reviews ;

    public Book(String title, String author, String genre, List<Review> reviews) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.reviews = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + genre + ")";
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
