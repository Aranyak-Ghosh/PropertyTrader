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

import util2.Singleton;

@ManagedBean(name = "graphHelper")
@SessionScoped
public class StatsProvider {

    public String getAreaProperties() {
        try {
            String output = "[['AREA','PROPERTIES']";

            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
            crs.setCommand("SELECT DISTINCT AREA FROM PROPERTY");
            crs.execute();

            CachedRowSet crs2 = RowSetProvider.newFactory().createCachedRowSet();
            crs2.setUrl(Singleton.getInstance().getDB());
            crs2.setUsername(Singleton.getInstance().getUser());
            crs2.setPassword(Singleton.getInstance().getPasswd());

            while (crs.next()) {
                String area = crs.getString("AREA");
                crs2.setCommand("SELECT COUNT(*) AS NUMPROPERTY FROM PROPERTY WHERE AREA =? ");
                crs2.setString(1, area);
                crs2.execute();
                int num = 0;
                if (crs2.next()) {
                    num = crs2.getInt("NUMPROPERTY");
                }
                output = output + ",['" + area + "'," + num + "]";

            }
            output = output + "]";

            return output;
        } catch (SQLException e) {
            Logger.getLogger(StatsProvider.class.getSimpleName()).log(Level.SEVERE, null, e);
        }
        return "error.xhtml";
    }
}
