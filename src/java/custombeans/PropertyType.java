package custombeans;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

import util2.Singleton;

/**
 *
 * @author aranyak
 */
@ManagedBean(name = "propertyType")
@SessionScoped
public class PropertyType implements Serializable {

    private int typeID;
    private String typeName;

    private ArrayList<SelectItem> list;

    public PropertyType() {
        list = new ArrayList<SelectItem>();
        SelectItem bunglow = new SelectItem();
        SelectItem apartment = new SelectItem();
        SelectItem plot = new SelectItem();
        bunglow.setLabel("Bunglow");
        bunglow.setValue(1);
        apartment.setLabel("Apartment");
        apartment.setValue(2);
        plot.setLabel("Plot");
        plot.setValue(3);
        list.add(bunglow);
        list.add(apartment);
        list.add(plot);
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ArrayList<SelectItem> getList() {
        return list;
    }

}
