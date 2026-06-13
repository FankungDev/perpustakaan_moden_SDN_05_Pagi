/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rafli
 */
public class koneksi {
    
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                // Parameter koneksi
                String url = "jdbc:mysql://localhost:3306/perpustakaan_sd05_bidaracina";
                String user = "root"; // Sesuaikan dengan user database Anda
                String password = ""; // Sesuaikan dengan password database Anda
                
                // Load driver & buat koneksi
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                
                System.out.println("Koneksi berhasil!");
            } catch (SQLException e) {
                System.out.println("Koneksi gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }
    
    // Main method untuk tes koneksi
    public static void main(String[] args) {
        getKoneksi();
    }
}