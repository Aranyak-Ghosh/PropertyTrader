package methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;

import custombeans.Property;
import custombeans.User;
import custombeans.Transaction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.RowSetProvider;

import util.Singleton;

@ManagedBean(name = "transactionManager")
public class TransactionManager {

    private int propertyID;

    private String owner;

    private String buyer;

    private CachedRowSet crs;

    public TransactionManager() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void init(int propertyID, String buyer, String owner) {
        this.propertyID = propertyID;
        this.buyer = buyer;
        this.owner = owner;
    }

    public String addTransaction() {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a", "b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            String query = "INSERT INTO TRANSACTIONS (PROPERTY_ID,TRANSACTION_ID,BUYER_NAME,SELLER_NAME) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            int id = 0;

            crs.setCommand("SELECT COUNT(*) AS NUMROWS FROM TRANSACTIONS");
            crs.execute();

            while (crs.next()) {
                id = crs.getInt("NUMROWS");
            }

            ps.setInt(1, propertyID);
            ps.setInt(2, id);
            ps.setString(3, buyer);
            ps.setString(4, owner);

            boolean success = ps.execute();

            if (success) {
                String propertyQuery = "SELECT * FROM PROPERTY WHERE PROPERTY_ID = ?";
                crs.setCommand(propertyQuery);
                crs.setInt(1, propertyID);
                crs.execute();
                if (crs.next()) {
                    crs.updateInt("AVAILABLE", 0);
                    crs.updateString("OWNED_BY", buyer);
                    crs.acceptChanges();
                }
                return "addReview.xhtml";

            } else {
                return "error.xhtml";

            }
        } catch (Exception ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error.xhtml";
    }
}
