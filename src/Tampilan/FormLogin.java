/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Tampilan;


/**
 *
 * @author rafli
 */
public class FormLogin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormLogin.class.getName());

    /**
     * Creates new form FormLogin
     */
    public FormLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        
        // untuk Logo Utama //
        try {
        javax.swing.ImageIcon iconAsli = new javax.swing.ImageIcon(getClass().getResource("/gambar/LoginPagePng.png"));
        
        // GANTI ANGKA DI BAWAH INI sesuai ukuran JLabel kamu di menu Design
        int targetWidth = 333;  
        int targetHeight = 335; 
        
        // Melakukan scaling smooth dengan ukuran pasti
        java.awt.Image imgSelesai = iconAsli.getImage().getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH);
        
        jLabelIcon.setIcon(new javax.swing.ImageIcon(imgSelesai));
        
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        inisialisasiAksiManual();
        
        // untuk Logo Mata
}
    
    private java.util.Map<String, String> checkLogin(String username, String password) {
    java.util.Map<String, String> result = new java.util.HashMap<>();
    
    // Ambil instans koneksi global
    java.sql.Connection conn = Koneksi.koneksi.getKoneksi();
    
    if (conn != null) {
        // Sesuaikan nama tabel 'data_admin' atau 'user' sesuai database kamu
        String sql = "SELECT * FROM data_admin WHERE Username = ? AND Password = ?";
        
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Masukkan data hasil query ke dalam Map sesuai kolom di DB kamu
                    result.put("nama", rs.getString("nama"));
                    result.put("level", rs.getString("level"));
                    return result; // Kembalikan Map jika data ditemukan
                }
            }
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memproses data: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Koneksi database tidak tersedia!", "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    return null; // Mengembalikan null jika login gagal atau error
    }
    
    private void inisialisasiAksiManual() {
        // 1. Aksi ketika tombol LOGIN diklik
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prosesLogin();
            }
        });

        // 2. Aksi fitur intip password pada label mata (lblLihatGmbr)
        lblLihatGmbr.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // Menampilkan teks password asli saat ikon ditekan/ditahan
                txtPassword.setEchoChar((char) 0);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                // Menyembunyikan kembali teks password menjadi bullet bulat saat klik dilepas
                txtPassword.setEchoChar('\u2022');
            }
        });
    }
    
    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validasi inputan tidak boleh kosong
        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Username dan Password wajib diisi!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM data_admin WHERE Username = ? AND Password = ?";

        // 1. Ambil instans koneksi global (Jangan ditaruh di dalam try-with-resources agar tidak auto-close)
        java.sql.Connection conn = Koneksi.koneksi.getKoneksi();
        
        if (conn == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Koneksi database tidak tersedia!", "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Hanya PreparedStatement dan ResultSet saja yang boleh auto-close
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String namaAdmin = rs.getString("nama");
                    String level = rs.getString("level");
                    
                    javax.swing.JOptionPane.showMessageDialog(this, "Selamat Datang " + namaAdmin + " (" + level + ")!", "Login Berhasil", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                    // Membuka Frame MenuUtama / Dashboard
                    MenuUtama utama = new MenuUtama();
                    utama.setVisible(true);
                    
                    // Menutup FormLogin saat ini
                    this.dispose();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Gagal Login", javax.swing.JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText("");
                    txtPassword.requestFocus();
                }
            }
            
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memproses data: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        custom_JPanelRounded1 = new palette.Custom_JPanelRounded();
        custom_JPanelRounded2 = new palette.Custom_JPanelRounded();
        jLabelIcon = new javax.swing.JLabel();
        txtUsername = new palette.Custom_JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblLihatGmbr = new javax.swing.JLabel();
        txtPassword = new palette.Custom_JPasswordField();
        btnLogin = new palette.Custom_JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        custom_JPanelRounded1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        custom_JPanelRounded2.setBackground(new java.awt.Color(224, 234, 255));
        custom_JPanelRounded2.setRoundBottomLeft(0);
        custom_JPanelRounded2.setRoundTopLeft(0);

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LoginPagePng.png"))); // NOI18N

        javax.swing.GroupLayout custom_JPanelRounded2Layout = new javax.swing.GroupLayout(custom_JPanelRounded2);
        custom_JPanelRounded2.setLayout(custom_JPanelRounded2Layout);
        custom_JPanelRounded2Layout.setHorizontalGroup(
            custom_JPanelRounded2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custom_JPanelRounded2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabelIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        custom_JPanelRounded2Layout.setVerticalGroup(
            custom_JPanelRounded2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custom_JPanelRounded2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabelIcon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        custom_JPanelRounded1.add(custom_JPanelRounded2, new org.netbeans.lib.awtextra.AbsoluteConstraints(398, 0, -1, 388));

        txtUsername.setForeground(new java.awt.Color(153, 153, 153));
        txtUsername.addActionListener();
        custom_JPanelRounded1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 105, 350, 41));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Selamat Datang");
        custom_JPanelRounded1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Password");
        custom_JPanelRounded1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 164, -1, -1));

        lblLihatGmbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/eyesIconfit.png"))); // NOI18N
        custom_JPanelRounded1.add(lblLihatGmbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 30, 40));

        txtPassword.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword.addActionListener();
        custom_JPanelRounded1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 192, 350, 41));

        btnLogin.setText("LOGIN");
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        custom_JPanelRounded1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 258, 350, 41));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Username");
        custom_JPanelRounded1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 77, -1, -1));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Halo,");
        custom_JPanelRounded1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(custom_JPanelRounded1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(custom_JPanelRounded1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormLogin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.Custom_JButton btnLogin;
    private palette.Custom_JPanelRounded custom_JPanelRounded1;
    private palette.Custom_JPanelRounded custom_JPanelRounded2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel lblLihatGmbr;
    private palette.Custom_JPasswordField txtPassword;
    private palette.Custom_JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
