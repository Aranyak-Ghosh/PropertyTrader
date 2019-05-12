package methods;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;

import custombeans.Property;

import custombeans.User;

import util.DBSingleton;

/**
 *
 * @author yaseenfarooqui
 */
@ManagedBean(name = "property")
public class PropertyManager {

    @ManagedProperty(value = "#{user}")
    private User user;

    @ManagedProperty(value = "#{property}")
    private Property property;

    public PropertyManager() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void addProperty() {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a", "b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String query = "INSERT INTO PROPERTY(TYPEID, AREA, ORIGINYEAR, BATHROOMS, BEDROOMS, PROPERTY_ID, PRICE, PICTURES, OWNED_BY, AVAILABLE) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            int id = 0;
            CachedRowSet crs = DBSingleton.getCRS();
            crs.setCommand("SELECT COUNT(*) AS NUMROWS FROM PROPERTY");
            crs.execute();

            if (crs.next()) {
                id = crs.getInt("NUMROWS");
            }

            ps.setInt(1, property.getTypeID());
            ps.setString(2, property.getArea());
            ps.setInt(3, property.getYear());
            ps.setInt(4, property.getNumBath());
            ps.setInt(5, property.getNumBed());
            ps.setInt(6, id);
            ps.setInt(7, property.getPrice());
            ps.setString(8, property.getPictures());
            ps.setString(9, property.getOwner());
            ps.setInt(10, 1);

            boolean success = ps.execute();

            if (success) {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.sendRedirect("index.xhtml");
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.sendRedirect("error.xhtml");
            }

        } catch (Exception ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Property> getPropertybyPrice(int minprice, int maxprice) {
        ArrayList<Property> properties = null;
        try {
            CachedRowSet crs = DBSingleton.getCRS();
            crs.setCommand("SELECT * FROM PROPERTY WHERE PRICE>? AND PRICE<? AND AVAILABLE = 1");
            crs.setInt(1, minprice);
            crs.setInt(2, maxprice);
            crs.execute();

            properties = generateArraylist(crs);

        } catch (Exception ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    public ArrayList<Property> getPropertiesbyArea(String area) {
        ArrayList<Property> properties = null;

        try {
            CachedRowSet crs = DBSingleton.getCRS();

            crs.setCommand("SELECT * FROM PROPERTY WHERE AREA LIKE ? AND AVAILABLE = 1");

            crs.setString(1, "%" + area + "%");
            crs.execute();

            properties = generateArraylist(crs);
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

        return properties;
    }

    public ArrayList<Property> getPropertybyType(int typeID) {
        ArrayList<Property> properties = null;

        try {
            CachedRowSet crs = DBSingleton.getCRS();

            crs.setCommand("SELECT * FROM PROPERTY WHERE TYPEID = ? AND AVAILABLE = 1");

            crs.setInt(1, typeID);
            crs.execute();

            properties = generateArraylist(crs);
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

        return properties;
    }

    private ArrayList<Property> generateArraylist(CachedRowSet crs) {

        ArrayList<Property> properties = new ArrayList<Property>();

        try {
            while (crs.next()) {
                Property temp = new Property();
                temp.setTypeID(crs.getInt("TYPEID"));
                temp.setArea(crs.getString("AREA"));
                temp.setNumBath(crs.getInt("BATHROOMS"));
                temp.setNumBed(crs.getInt("BEDROOMS"));
                temp.setOwner(crs.getString("OWNED_BY"));
                temp.setPictures(crs.getString("PICTURES"));
                temp.setPrice(crs.getInt("PRICE"));
                temp.setYear(crs.getInt("ORIGINYEAR"));
                temp.setAvailable(crs.getInt("AVAILABLE"));
                properties.add(temp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return properties;
    }
}
