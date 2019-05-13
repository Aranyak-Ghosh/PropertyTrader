package util;

/**
 *
 * @author gbarlas
 */
public class Singleton {
    private static Singleton instance=null;
    private String DB;
    private String user;
    private String passwd;

    public Singleton() throws Exception
    {
        
        System.out.println("Singleton constructor called");
        if(instance==null)
            instance=this;
        else
            throw new Exception("Only one Singleton is allowed");
    }
    public static Singleton getInstance() {
        return instance;
    }

    public static void setInstance(Singleton instance) {
        Singleton.instance = instance;
    }

    public String getDB() {
        return DB;
    }

    public void setDB(String DB) {
        this.DB = DB;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
            
}
