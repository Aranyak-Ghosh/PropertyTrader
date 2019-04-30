package Beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;

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
    
    
}