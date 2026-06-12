package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class koneksi {
    private static Connection conn;
    
    public static Connection getConnection(){
        if (conn==null) {
            try {
                String url = "";
                String user = "root";
                String pass ="";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                conn = (Connection) DriverManager.getConnection(url,user,pass);
            } catch (Exception e) {
                Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return conn;
    }
}
