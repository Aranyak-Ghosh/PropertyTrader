package methods;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import custombeans.Property;
import custombeans.User;

import util2.Singleton;

/**
 *
 * @author aranyak
 */
@ManagedBean(name = "propertyManager")
@SessionScoped
public class PropertyManager {

    @ManagedProperty(value = "#{user}")
    private User user;

    @ManagedProperty(value = "#{property}")
    private Property property;

    private int minprice;
    private int maxprice;
    private String area;
    private int typeID;

    CachedRowSet crs;

    public PropertyManager() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getMinprice() {
        return minprice;
    }

    public void setMinprice(int minprice) {
        this.minprice = minprice;
    }

    public int getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(int maxprice) {
        this.maxprice = maxprice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
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

    public String addProperty(String username) {
        this.property.setOwner(username);
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a", "b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String query = "INSERT INTO PROPERTY(TYPEID, AREA, ORIGINYEAR, BATHROOMS, BEDROOMS, PROPERTY_ID, PRICE, PICTURES, OWNED_BY, AVAILABLE) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            int id = 0;
            PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) AS NUMROWS FROM PROPERTY");
            ResultSet rs = ps2.executeQuery();

            rs.next();
            id = rs.getInt("NUMROWS");

            this.property.setProperty_ID(id);

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

            return "success.xhtml";

        } catch (Exception ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error.xhtml";
    }

    public ArrayList<Property> getPropertybyPrice(int minprice, int maxprice) {
        ArrayList<Property> properties = null;
        try {
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
            crs.setCommand("SELECT * FROM PROPERTY WHERE TYPEID = ? AND AVAILABLE = 1");

            crs.setInt(1, typeID);
            crs.execute();

            properties = generateArraylist(crs);
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

        return properties;
    }

    public ArrayList<Property> getPropertyByOwner(String owner) {
        ArrayList<Property> properties = null;

        try {
            crs.setCommand("SELECT * FROM PROPERTY WHERE OWNED_BY=?");

            crs.setString(1, owner);
            crs.execute();

            properties = generateArraylist(crs);
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

        return properties;
    }

    public ArrayList<Property> searchProperty() {
        ArrayList<Property> properties = null;

        try {
            crs.setCommand(
                    "SELECT * FROM PROPERTY WHERE TYPEID = ? AND AREA LIKE ? AND PRICE>? AND PRICE<? AND AVAILABLE = 1");

            crs.setInt(1, this.typeID);
            crs.setString(2, "%" + this.area + "%");
            crs.setInt(3, this.minprice);
            crs.setInt(4, this.maxprice);
            crs.execute();

            properties = generateArraylist(crs);
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

        return properties;
    }

    public void setCurrentProperty(int propertyID) {
        try {
            crs.setCommand("SELECT * FROM PROPERTY WHERE PROPERTY_ID = ?");

            crs.setInt(1, propertyID);
            crs.execute();

            if (crs.next()) {
                this.property.setTypeID(crs.getInt("TYPEID"));
                this.property.setArea(crs.getString("AREA"));
                this.property.setNumBath(crs.getInt("BATHROOMS"));
                this.property.setNumBed(crs.getInt("BEDROOMS"));
                this.property.setOwner(crs.getString("OWNED_BY"));
                this.property.setPictures(crs.getString("PICTURES"));
                this.property.setPrice(crs.getInt("PRICE"));
                this.property.setYear(crs.getInt("ORIGINYEAR"));
                this.property.setAvailable(crs.getInt("AVAILABLE"));
                this.property.setProperty_ID(crs.getInt("PROPERTY_ID"));
            }

        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }

    }

    public String editProperty() {
        try {

            crs.setCommand(
                    "UPDATE PROPERTY SET TYPID=?, AREA=?,BATHROOMS=?,BEDROOMS=?,OWNED_BY=?,PRICE=?,ORIGINYEAR=? WHERE PROPERTY_ID=?");

            crs.setInt(1, property.getTypeID());
            crs.setString(2, property.getArea());
            crs.setInt(3, property.getNumBath());
            crs.setInt(4, property.getNumBed());
            crs.setString(5, property.getOwner());
            crs.setInt(6, property.getPrice());
            crs.setInt(7, property.getYear());
            crs.setInt(8, property.getProperty_ID());

            crs.execute();

            return "success.xhtml";
        } catch (Exception e) {
            Logger.getLogger(PropertyManager.class.getSimpleName()).log(Level.SEVERE, null, e);
        }
        return "error.xhtml";
    }

    public String deleteProperty() {
        try {
            crs.setCommand("DELETE FROM PROPERTY WHERE PROPERTY_ID = ?");

            crs.setInt(1, this.property.getProperty_ID());
            crs.execute();
            return "success.xhtml";

        } catch (Exception ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error.xhtml";
    }

    private ArrayList<Property> generateArraylist(CachedRowSet crs) {

        ArrayList<Property> properties = new ArrayList<Property>();

        try {
            if (crs.size() > 0) {
                while (crs.next()) {
                    Property temp = new Property();
                    temp.setTypeID(crs.getInt("TYPEID"));
                    temp.setArea(crs.getString("AREA"));
                    temp.setNumBath(crs.getInt("BATHROOMS"));
                    temp.setNumBed(crs.getInt("BEDROOMS"));
                    temp.setOwner(crs.getString("OWNED_BY"));
                    temp.setPrice(crs.getInt("PRICE"));
                    temp.setYear(crs.getInt("ORIGINYEAR"));
                    temp.setAvailable(crs.getInt("AVAILABLE"));
                    temp.setProperty_ID(crs.getInt("PROPERTY_ID"));
                    properties.add(temp);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return properties;
    }
}
