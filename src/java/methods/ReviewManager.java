package methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;

import custombeans.Property;
import custombeans.Reviews;
import custombeans.User;
import custombeans.Transaction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.RowSetProvider;

import util2.Singleton;

@ManagedBean(name = "reviewManager")
@SessionScoped
public class ReviewManager {

    @ManagedProperty(value = "#{review}")
    private Reviews review;

    private CachedRowSet crs;

    public ReviewManager() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReviewManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReviewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Reviews getReview() {
        return review;
    }

    public void setReview(Reviews review) {
        this.review = review;
    }

    public void init(int propertyID, String Author) {
        this.review.setAuthor(Author);
        this.review.setPropertyID(propertyID);
    }

    public String addReview() {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a", "b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            String query = "INSERT INTO REVIEWS (REVIEW,PROPERTY_ID,RATING,AUTHOR,REVIEW_ID) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            int id = 0;

            crs.setCommand("SELECT COUNT(*) AS NUMROWS FROM REVIEWS");
            crs.execute();

            while (crs.next()) {
                id = crs.getInt("NUMROWS");
            }

            ps.setString(1, review.getReview());
            ps.setInt(2, review.getPropertyID());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getAuthor());
            ps.setInt(5, id);

            boolean success = ps.execute();

            return "success.xhtml";

        } catch (Exception ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error.xhtml";
    }
}
