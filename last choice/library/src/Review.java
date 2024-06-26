import java.util.List;
import java.util.ArrayList;

public class Review {
    private String reviewer;
    private String comment;
    private int rating;
    private List<Review> reviews ;

    public Review(String reviewer, String comment, int rating, List<Review> reviews) {
        this.reviewer = reviewer;
        this.comment = comment;
        this.rating = rating;
        this.reviews = new ArrayList<>();
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
     

    public void addReviews(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}