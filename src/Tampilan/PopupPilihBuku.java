/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tampilan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rafli
 */
public class PopupPilihBuku extends javax.swing.JFrame {
    private View.menuCRUDPengembalian formPengembalian;

    /**
     * Creates new form PopupPilihBuku
     */
    public PopupPilihBuku(View.menuCRUDPengembalian formPengembalian) {
        initComponents();
        setTabelDetailModel();
        this.formPengembalian = formPengembalian; // Inisialisasi properti
        setTabelModel();
        this.setLocationRelativeTo(null);
        loadData();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    public PopupPilihBuku() {
        initComponents();
        setTabelModel();
        loadData();
        
    }
    
    private javax.swing.ImageIcon getCoverBuku(String idBuku) {
        javax.swing.ImageIcon imageIcon = null;
        try {
            Connection conn = Koneksi.koneksi.getKoneksi();
            // Sesuaikan 'cover' dan 'buku' dengan nama kolom gambar dan tabel Anda yang sebenarnya
            String sql = "SELECT cover FROM buku WHERE id_buku = ?"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idBuku);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("cover");
                if (imgBytes != null) {
                    // Membuat ImageIcon dari byte array
                    javax.swing.ImageIcon tempIcon = new javax.swing.ImageIcon(imgBytes);
                    
                    // Skala gambar agar pas dengan ukuran lblGambar di menuCRUDPengembalian (W: 170, H: 190)
                    java.awt.Image img = tempIcon.getImage();
                    java.awt.Image scaledImg = img.getScaledInstance(170, 190, java.awt.Image.SCALE_SMOOTH);
                    imageIcon = new javax.swing.ImageIcon(scaledImg);
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat mengambil cover buku: " + e.getMessage());
        }
        return imageIcon;
    }
    
     private void setTabelDetailModel() {
        DefaultTableModel model = new DefaultTableModel();
       model.addColumn("No");
        model.addColumn("ID Pinjam");
        model.addColumn("ID Buku");
        model.addColumn("Judul Buku");
        model.addColumn("Jumlah Buku");
        model.addColumn("Status Peminjaman");

        tblDetailPeminjaman.setModel(model);
    }
    
   private void loadDetailPeminjaman(String idPinjam) {

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No");
    model.addColumn("ID Pinjam");
    model.addColumn("ID Buku");
    model.addColumn("Judul Buku");
    model.addColumn("Jumlah Buku");
    model.addColumn("Status Peminjaman");

    try {
        Connection conn = Koneksi.koneksi.getKoneksi();
        if (conn == null) return;

        String sql = "SELECT dp.id_pinjam, dp.id_buku, b.judul_buku, dp.jumlah_pinjam, dp.status_pinjam " +
                     "FROM detail_pinjam dp " +
                     "JOIN buku b ON dp.id_buku = b.id_buku " +
                     "WHERE dp.id_pinjam = ? AND dp.status_pinjam = 'Dipinjam'";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idPinjam);

        ResultSet rs = ps.executeQuery();

        int no = 1;

        while (rs.next()) {
            model.addRow(new Object[]{
                no++,
                rs.getString("id_pinjam"),
                rs.getString("id_buku"),
                rs.getString("judul_buku"),
                rs.getInt("jumlah_pinjam"),
                rs.getString("status_pinjam")
            });
        }

        tblDetailPeminjaman.setModel(model);

        // Otomatis menyesuaikan lebar kolom

    } catch (Exception e) {
        System.out.println("Error pada loadDetailPeminjaman: " + e.getMessage());
    }
}
    
    private void setTabelModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("ID Pinjam");
        model.addColumn("NIS");
        model.addColumn("Nama Anggota");
        model.addColumn("Tanggal Pinjam");
        model.addColumn("Tanggal Kembali");
        model.addColumn("Total Pinjam");
        tblPeminjamanBuku.setModel(model);
    }
     
    private void loadData() {
    DefaultTableModel model = (DefaultTableModel) tblPeminjamanBuku.getModel();
    model.setRowCount(0);

    try {
        Connection conn = Koneksi.koneksi.getKoneksi();
        if (conn == null) {
            System.out.println("Gagal terhubung ke database.");
            return;
        }

        String sql = "SELECT p.Id_Pinjam, p.Nis, a.Nama, p.Tanggal_Pinjam, p.Tanggal_Kembali, " +
                     "SUM(dp.Jumlah_Pinjam) AS total_pinjam " +
                     "FROM peminjaman p " +
                     "JOIN data_anggota a ON p.Nis = a.Nis " +
                     "JOIN detail_pinjam dp ON p.Id_Pinjam = dp.Id_Pinjam " +
                     "GROUP BY p.Id_Pinjam, p.Nis, a.Nama, p.Tanggal_Pinjam, p.Tanggal_Kembali";

        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        int no = 1;
        while (rs.next()) {
            model.addRow(new Object[]{
                no++,
                rs.getString("Id_Pinjam"),
                rs.getString("Nis"),
                rs.getString("Nama"),
                rs.getDate("Tanggal_Pinjam"),
                rs.getDate("Tanggal_Kembali"),
                rs.getInt("total_pinjam")
            });
        }

        // Otomatis menyesuaikan lebar kolom

    } catch (Exception e) {
        System.out.println("Error pada loadData: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    private void cariData() {
    DefaultTableModel model = (DefaultTableModel) tblPeminjamanBuku.getModel();
    model.setRowCount(0);

    String keyword = tfCari.getText().trim();

    try {
        Connection conn = Koneksi.koneksi.getKoneksi();

        String sql =
                "SELECT p.Id_Pinjam, p.Nis, a.Nama, p.Tanggal_Pinjam, p.Tanggal_Kembali, " +
                "SUM(dp.Jumlah_Pinjam) AS total_pinjam " +
                "FROM peminjaman p " +
                "JOIN data_anggota a ON p.Nis = a.Nis " +
                "JOIN detail_pinjam dp ON p.Id_Pinjam = dp.Id_Pinjam " +
                "WHERE p.Id_Pinjam LIKE ? " +
                "OR p.Nis LIKE ? " +
                "OR a.Nama LIKE ? " +
                "GROUP BY p.Id_Pinjam, p.Nis, a.Nama, p.Tanggal_Pinjam, p.Tanggal_Kembali";

        PreparedStatement ps = conn.prepareStatement(sql);

        String cari = "%" + keyword + "%";

        ps.setString(1, cari);
        ps.setString(2, cari);
        ps.setString(3, cari);

        ResultSet rs = ps.executeQuery();

        int no = 1;

        while (rs.next()) {
            model.addRow(new Object[]{
                no++,
                rs.getString("Id_Pinjam"),
                rs.getString("Nis"),
                rs.getString("Nama"),
                rs.getDate("Tanggal_Pinjam"),
                rs.getDate("Tanggal_Kembali"),
                rs.getInt("total_pinjam")
            });
        }

    } catch (Exception e) {
        System.out.println("Error pencarian: " + e.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeminjamanBuku = new javax.swing.JTable();
        tfCari = new palette.Custom_JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailPeminjaman = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblPeminjamanBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPeminjamanBuku.setRowHeight(50);
        tblPeminjamanBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPeminjamanBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeminjamanBuku);

        tfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCariKeyPressed(evt);
            }
        });

        tblDetailPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDetailPeminjaman.setRowHeight(50);
        tblDetailPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetailPeminjamanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetailPeminjaman);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Data Peminjaman Buku");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(231, 468, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfCari, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(4, 4, 4)
                .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPeminjamanBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPeminjamanBukuMouseClicked
         // 1. Ambil baris ke berapa yang diklik oleh user
        int baris = tblPeminjamanBuku.getSelectedRow();
        
        // 2. Cek apakah ada baris yang valid
        if (baris != -1) {
            // 3. Ambil nilai ID Pinjam dari kolom indeks ke-1 (Kolom ke-2)
            // Sesuaikan indeks ini dengan posisi kolom id_pinjam di tblPeminjamanBuku Anda (0 = No, 1 = ID Pinjam)
            String idPinjam = tblPeminjamanBuku.getValueAt(baris, 1).toString();
            
            // 4. Panggil method detail dengan melempar ID Pinjam tersebut
            loadDetailPeminjaman(idPinjam);
        }      
    }//GEN-LAST:event_tblPeminjamanBukuMouseClicked

    private void tblDetailPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailPeminjamanMouseClicked
        int barisDetail = tblDetailPeminjaman.getSelectedRow();
    int barisMaster = tblPeminjamanBuku.getSelectedRow();
        
    // Validasi memastikan kedua tabel sudah dipilih barisnya
    if (barisDetail != -1 && barisMaster != -1 && formPengembalian != null) {
        
        // 1. Ambil data dari tblPeminjamanBuku (Master)
        String idPinjam = tblPeminjamanBuku.getValueAt(barisMaster, 1).toString();
        String idAnggota = tblPeminjamanBuku.getValueAt(barisMaster, 2).toString();
        String namaAnggota = tblPeminjamanBuku.getValueAt(barisMaster, 3).toString();
        String tglPinjam = tblPeminjamanBuku.getValueAt(barisMaster, 4).toString();
        String tglKembali = tblPeminjamanBuku.getValueAt(barisMaster, 5).toString();

        // 2. Ambil data dasar dari tblDetailPeminjaman (Detail)
        String idBuku = tblDetailPeminjaman.getValueAt(barisDetail, 2).toString();
        String judulBuku = tblDetailPeminjaman.getValueAt(barisDetail, 3).toString();

        String penerbit = "";
        String pengarang = "";
        String pathCover = ""; // Menggunakan String untuk menampung alamat file gambar

        // 3. Query tambahan untuk mencari Penerbit & Pengarang & Alamat Path Cover dari tabel buku
        try {
            Connection conn = Koneksi.koneksi.getKoneksi();
            String sql = "SELECT p.nama_penerbit, b.pengarang, b.cover " +
                         "FROM buku b " +
                         "INNER JOIN penerbit p ON b.id_penerbit = p.id_penerbit " +
                         "WHERE b.id_buku = ?"; 

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idBuku);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                penerbit = rs.getString("nama_penerbit"); 
                pengarang = rs.getString("pengarang"); 
                pathCover = rs.getString("cover"); // Mengambil alamat/path gambar bertipe teks
            }
        } catch (Exception e) {
            System.out.println("Error saat mengambil data buku: " + e.getMessage());
            e.printStackTrace();
        }

        // 4. Kirim seluruh data gabungan master + detail + string alamat cover ke formPengembalian
        formPengembalian.setDetailBukuDariPopup(
            idPinjam, idBuku, judulBuku, penerbit, pengarang, 
            tglPinjam, tglKembali, idAnggota, namaAnggota, 
            pathCover // Mengirimkan alamat file gambar (String)
        );

        // Tutup JDialog / JFrame Popup
        this.dispose();
    }
    
    }//GEN-LAST:event_tblDetailPeminjamanMouseClicked

    private void tfCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyPressed
        cariData();
    }//GEN-LAST:event_tfCariKeyPressed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PopupPilihBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopupPilihBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopupPilihBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopupPilihBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PopupPilihBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDetailPeminjaman;
    private javax.swing.JTable tblPeminjamanBuku;
    private palette.Custom_JTextField tfCari;
    // End of variables declaration//GEN-END:variables
}
