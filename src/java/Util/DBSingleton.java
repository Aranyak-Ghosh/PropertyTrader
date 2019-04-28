
package Util;

import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yaseenfarooqui
 */
public class DBSingleton {

    private static final String DB_URL = "jdbc:derby://localhost:1527/Project";
    private static final String DB_USERNAME = "a";
    private static final String DB_PASSWORD = "b";
    private static CachedRowSet crs = null;
    private static boolean initialized = false;

    private DBSingleton() {
    }

    public static void init() throws SQLException {
        if (!initialized) {
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(DBSingleton.DB_URL);
            crs.setUsername(DBSingleton.DB_USERNAME);
            crs.setPassword(DBSingleton.DB_PASSWORD);
            initialized = true;
        }
    }
    
    public static CachedRowSet getCRS() throws Exception{
        if(!initialized)
            throw new Exception("CRS instance not initialized");
        else
            return crs;
    }
    
}
