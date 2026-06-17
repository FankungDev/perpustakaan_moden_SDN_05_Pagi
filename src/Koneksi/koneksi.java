/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rafli
 */
public class koneksi {
    private static Connection mysqlkoneksi;
    public static Connection getKoneksi() {
        if (mysqlkoneksi == null) {
            try {
                // Jalur/URL menuju database MySQL kamu
                // Ganti 'db_perpustakaan' dengan nama database yang kamu buat di phpMyAdmin
                String url = "jdbc:mysql://localhost:3306/db_perpustakaan"; 
                String user = "root"; // User default MySQL XAMPP
                String pass = "";     // Password default MySQL XAMPP (kosong)
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                mysqlkoneksi = DriverManager.getConnection(url, user, pass);
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Koneksi Database Gagal: " + e.getMessage());
            }
        }
        return mysqlkoneksi;
    }

    
}
