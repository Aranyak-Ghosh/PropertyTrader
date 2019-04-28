/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import Util.DBSingleton;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author b00060789
 */
@ManagedBean(name = "auth")

@SessionScoped
public class AuthenticationProvider {
    
    @ManagedProperty(value="#{user}")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Creates a new instance of AuthenticationProvider
     */
    public AuthenticationProvider() {
     try {
            DBSingleton.init();
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String calc_hash(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] arr = md.digest(pass.getBytes());
            StringBuilder buff = new StringBuilder();
            for (int i = 0; i < arr.length; ++i) {
                buff.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return buff.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void login() throws IOException {
        try {
            CachedRowSet crs=DBSingleton.getCRS();
            crs.setCommand("SELECT * FROM USERS WHERE USERNAME = ?");

            crs.setString(1, this.user.getUsername());
            crs.execute();

            while (crs.next()) {
                if (crs.getString("username").equals(this.user.getUsername())) {
                    String pass = crs.getString("PASSWORD");
                    String pass_hash = calc_hash(this.user.getPassword());
                    if (pass.contentEquals(pass_hash)) {
                        this.user.setLoggedIn(true);
                        this.user.setName(crs.getString("NAME"));
                        FacesContext context = FacesContext.getCurrentInstance();
                        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                        response.sendRedirect("Buyer_Seller_Home.xhtml");
                    } else {
                        FacesContext context = FacesContext.getCurrentInstance();
                        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                        response.sendRedirect("index.xhtml");
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void signUp(){
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a","b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String pass=calc_hash(user.getPassword());
            String query="INSERT INTO USERS(USERNAME, PASSWORD, NAME, LICENSE_NUMBER, PHONEID, UAEID_NUMBER, ADDRESS, EMAIL, BANKCARDINFO, PROPERTIES_BOUGHT, PROPERTIES_SOLD, RATINGS, REVIEWS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            //TODO: Add values to prepared Statement and execute
            
            
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
