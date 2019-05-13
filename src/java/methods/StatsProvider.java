package methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import custombeans.Property;
import custombeans.Reviews;
import custombeans.User;
import custombeans.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.DBSingleton;

@ManagedBean(name = "graphHelper")
public class StatsProvider {

    public String getAreaProperties() {
        try {
            String output = "[['AREA','PROPERTIES'],";

            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl("jdbc:derby://localhost:1527/Project");
            crs.setUsername("a");
            crs.setPassword("b");
            crs.setCommand("SELECT AREA FROM PROPERTY");
            crs.execute();

            CachedRowSet crs2 = RowSetProvider.newFactory().createCachedRowSet();
            crs2.setUrl("jdbc:derby://localhost:1527/Project");
            crs2.setUsername("a");
            crs2.setPassword("b");

            while (crs.next()) {
                String area = crs.getString("AREA");
                crs2.setCommand("SELECT COUNT(*) AS NUMPROPERTY FROM PROPERTY WHERE AREA = " + area);
                crs2.execute();
                int num = 0;
                if (crs2.next()) {
                    num = crs2.getInt("NUMPROPERTY");
                }
                output = output + "['" + area + "''," + num + "],";

            }
            output = output + "]";

            return output;
        } catch (SQLException e) {
            Logger.getLogger(StatsProvider.class.getSimpleName()).log(Level.SEVERE, null, e);
        }
        return "error.xhtml";
    }
}
