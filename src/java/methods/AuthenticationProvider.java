package methods;

import custombeans.User;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import util2.Singleton;

/**
 *
 * @author aranyak
 */
@ManagedBean(name = "auth")

@SessionScoped
public class AuthenticationProvider {

    @ManagedProperty(value = "#{user}")
    private User user;
    private boolean isLoggedIn;
    private CachedRowSet crs;

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
            new Singleton();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());
            this.isLoggedIn = false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {

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

    public String login() throws IOException {
        try {

            crs.setCommand("SELECT * FROM USERS WHERE USERNAME = ?");

            crs.setString(1, this.user.getUsername());
            crs.execute();

            while (crs.next()) {
                if (crs.getString("USERNAME").equals(this.user.getUsername())) {
                    String pass = crs.getString("HASHPASS");
                    String pass_hash = calc_hash(this.user.getPassword());
                    if (pass.contentEquals(pass_hash)) {
                        this.user.setLoggedIn(true);
                        this.user.setName(crs.getString("FULL_NAME"));
                        isLoggedIn = true;
                        return "home.xhtml";
                    } else {
                        return "authError.xhtml";
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "authError.xhtml";
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String signUp() {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project", "a", "b");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String pass = calc_hash(user.getPassword());
            String query = "INSERT INTO USERS(USERNAME, HASHPASS, FULL_NAME, CONTACTNO, EID, P_ADDRESS, EMAIL, BANKCARD_INFO) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, pass);
            ps.setString(3, user.getName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getId());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getCardNo());

            boolean success = ps.execute();

            return "accountCreated.xhtml";
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "authError.xhtml";
    }

    public void logout() {
        this.isLoggedIn = false;
    }

}
