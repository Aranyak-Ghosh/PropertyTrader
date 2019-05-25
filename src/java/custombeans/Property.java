package custombeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import util2.Singleton;

/**
 *
 * @author aranyak
 */
@ManagedBean(name = "property")
@SessionScoped
public class Property implements Serializable {

    private int typeID;
    private int available;
    private String area;
    private int year;
    private int numBath;
    private int numBed;
    private int price;
    private String owner;
    private String pictures; // Not so sure about this
    private int property_ID;

    private String typeName;

    private CachedRowSet crs;

    public Property() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStatus() {
        String status = null;
        switch (available) {
        case 0:
            status = "YES";
            break;
        case 1:
            status = "NO";
            break;
        }
        return status;
    }

    public String getTypeName() {
        if (this.typeName == null || this.typeName.contentEquals("")) {
            try {

                crs.setCommand("SELECT * FROM PROPERTYTYPES WHERE TYPEID=?");
                crs.setInt(1, this.typeID);
                crs.execute();
                while (crs.next()) {
                    this.typeName = crs.getString("TYPENAME");
                }
            } catch (Exception ex) {
                Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumBath() {
        return numBath;
    }

    public void setNumBath(int numBath) {
        this.numBath = numBath;
    }

    public int getNumBed() {
        return numBed;
    }

    public void setNumBed(int numBed) {
        this.numBed = numBed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public int getProperty_ID() {
        return property_ID;
    }

    public void setProperty_ID(int property_ID) {
        this.property_ID = property_ID;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void reset() {
        typeID = 0;
        available = 0;
        area = null;
        property_ID = 0;
        pictures = null;
        owner = null;
        price = 0;
        numBed = 0;
        numBath = 0;
        year = 0;
    }
}
