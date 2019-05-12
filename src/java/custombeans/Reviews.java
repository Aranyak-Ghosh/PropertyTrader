package custombeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

/**
 *
 * @author yaseenfarooqui
 */
@ManagedBean(name = "review")
public class Reviews implements Serializable {

    private String review;
    private int rating;
    private String author;
    private int reviewID;
    private int propertyID;

    public Reviews() {

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

    public ArrayList<SelectItem> getRatingList() {
        ArrayList<SelectItem> list = new ArrayList<SelectItem>();
        for (int i = 1; i <= 5; i++) {
            SelectItem temp = new SelectItem();
            temp.setLabel("" + i);
            temp.setValue(i);
            list.add(temp);
        }
        return list;
    }
}
