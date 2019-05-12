package custombeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.sql.rowset.CachedRowSet;
import util.DBSingleton;

/**
 *
 * @author yaseenfarooqui
 */
@ManagedBean(name = "propertyType")
public class PropertyType implements Serializable {

    private int typeID;
    private String typeName;

    public PropertyType() {
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
            CachedRowSet crs = DBSingleton.getCRS();
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
