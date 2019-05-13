package custombeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import util.Singleton;

/**
 *
 * @author yaseenfarooqui
 */
@ManagedBean(name = "propertyType")
public class PropertyType implements Serializable {

    private int typeID;
    private String typeName;

    private CachedRowSet crs;

    public PropertyType() {
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
        ArrayList<SelectItem> list = new ArrayList<SelectItem>();
        try {
            crs.setCommand("SELECT * FROM PROPERTYTYPES");
            crs.execute();
            while (crs.next()) {
                SelectItem temp = new SelectItem();
                temp.setLabel(crs.getString("TYPENAME"));
                temp.setValue(crs.getInt("TYPEID"));
                list.add(temp);
            }
        } catch (Exception e) {
            Logger.getLogger(PropertyType.class.getSimpleName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

}
