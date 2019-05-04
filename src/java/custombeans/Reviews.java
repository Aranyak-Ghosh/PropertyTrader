package custombeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author yaseenfarooqui
 */
@ManagedBean(name = "reviews")
public class Reviews implements Serializable {
    private String review;
    private int rating;
    private String author;
    private int reviewID;
    private int propertyID;

    public Reviews(){

    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    
}