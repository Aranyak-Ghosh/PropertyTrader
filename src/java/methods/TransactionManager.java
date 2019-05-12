package methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;

import custombeans.Property;
import custombeans.User;
import custombeans.Transaction;

import util.DBSingleton;

@ManagedBean(name = "transaction")
public class TransactionManager {

    @ManagedProperty(value = "#{property}")
    private Property property;

    @ManagedProperty(value = "#{owner}")
    private User owner;

    @ManagedProperty(value = "#{buyer}")
    private User buyer;

    public TransactionManager() {
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void addTransaction() {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a", "b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            String query = "INSERT INTO TRANSACTIONS (PROPERTY_ID,TRANSACTION_ID,BUYER_NAME,SELLER_NAME) VALUES (????)";
            PreparedStatement ps = con.prepareStatement(query);

            int id = 0;
            CachedRowSet crs = DBSingleton.getCRS();

            crs.setCommand("SELECT COUNT(*) AS NUMROWS FROM TRANSACTIONS");
            crs.execute();

            while (crs.next()) {
                id = crs.getInt("NUMROWS");
            }

            ps.setInt(1, property.getProperty_ID());
            ps.setInt(2, id);
            ps.setString(3, buyer.getUsername());
            ps.setString(4, owner.getUsername());

            boolean success = ps.execute();

            if (success) {
                String propertyQuery = "SELECT * FROM PROPERTY WHERE PROPERTY_ID = ?";
                crs.setCommand(propertyQuery);
                crs.execute();
                if (crs.next()) {
                    crs.updateInt("AVAILABLE", 0);
                    crs.acceptChanges();
                }
                //TODO: Redirect to successful page
            } else {
                                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.sendRedirect("error.xhtml");

            }
        } catch (Exception ex) {
            //TODO: Redirect to error page
        }
    }
}
