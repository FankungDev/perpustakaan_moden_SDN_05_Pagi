/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;
import javax.swing.table.DefaultTableModel;
import Koneksi.koneksi; // Sesuaikan dengan package koneksi Anda
import java.sql.Connection;
import java.sql.PreparedStatement; // INI YANG TADI KURANG
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import Tampilan.MenuUtama;
import View.menuCRUDKategori;
import View.menuCRUDPeminjaman;

/**
 *
 * @author rafli
 */
public class menuPeminjaman extends javax.swing.JPanel {

    /**
     * Creates new form menuAnggota
     */
    public menuPeminjaman() {
        initComponents();
        setTabelModel();
        loadData();
    }
    
    private void loadDetailPeminjaman(String idPinjam) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        model.addColumn("No");
        model.addColumn("ID Pinjam");
        model.addColumn("ID Buku");
        model.addColumn("Judul Buku");
        model.addColumn("Jumlah Buku");
        model.addColumn("Status Peminjaman");

        try {
            Connection conn = Koneksi.koneksi.getKoneksi();
            if (conn == null) return;
            
            // PERBAIKAN: Mengambil dp.status_pinjam dari detail_pinjam
            String sql = "SELECT dp.id_pinjam, dp.id_buku, b.judul_buku, dp.jumlah_pinjam, dp.status_pinjam " +
                         "FROM detail_pinjam dp " +
                         "JOIN buku b ON dp.id_buku = b.id_buku " +
                         "WHERE dp.id_pinjam = ?"; 
            
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
                    rs.getString("status_pinjam") // Sesuai nama kolom baru
                });
            }
            
            tblDetailPeminjaman.setModel(model);
            
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
            // 1. Ambil koneksi
            Connection conn = Koneksi.koneksi.getKoneksi();
            
            // 2. Cek apakah koneksi berhasil atau tidak
            if (conn == null) {
                System.out.println("Gagal terhubung ke database. Cek konfigurasi koneksi Anda.");
                return;
            }
            
            // 3. Eksekusi query dengan JOIN ke data_anggota (kolom Nama) dan detail_pinjam
            String sql = "SELECT p.id_pinjam, p.nis, a.Nama, p.tanggal_pinjam, p.tanggal_kembali, " +
                         "SUM(dp.jumlah_pinjam) AS total_pinjam " +
                         "FROM peminjaman p " +
                         "JOIN data_anggota a ON p.nis = a.Nis " +
                         "JOIN detail_pinjam dp ON p.id_pinjam = dp.id_pinjam " +
                         "GROUP BY p.id_pinjam, p.nis, a.Nama, p.tanggal_pinjam, p.tanggal_kembali"; 
            
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            int no = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_pinjam"),
                    rs.getString("nis"),
                    rs.getString("Nama"), // Disesuaikan dengan nama kolom di database (Nama)
                    rs.getDate("tanggal_pinjam"),
                    rs.getDate("tanggal_kembali"),
                    rs.getInt("total_pinjam")
                });
            }
        } catch (Exception e) {
            System.out.println("Error pada loadData: " + e.toString());
            e.printStackTrace(); 
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        tfCari = new palette.Custom_JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailPeminjaman = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Data Peminjaman Buku");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Master Data > Kategori ");

        btnCari.setText("CARI");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        tfCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCariActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Data Detail Peminjaman Buku");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel13)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        String keyword = tfCari.getText().trim();
        
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblPeminjamanBuku.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.koneksi.getKoneksi();
            // PERBAIKAN: Query pencarian disesuaikan untuk data Transaksi Peminjaman
            String sql = "SELECT p.id_pinjam, p.nis, a.Nama, p.tanggal_pinjam, p.tanggal_kembali, " +
                         "SUM(dp.jumlah_pinjam) AS total_pinjam " +
                         "FROM peminjaman p " +
                         "JOIN data_anggota a ON p.nis = a.Nis " +
                         "JOIN detail_pinjam dp ON p.id_pinjam = dp.id_pinjam " +
                         "WHERE p.id_pinjam LIKE ? OR a.Nama LIKE ? " +
                         "GROUP BY p.id_pinjam, p.nis, a.Nama, p.tanggal_pinjam, p.tanggal_kembali"; 

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%"); 
            st.setString(2, "%" + keyword + "%");

            ResultSet rs = st.executeQuery();

            int no = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_pinjam"),
                    rs.getString("nis"),
                    rs.getString("Nama"),
                    rs.getDate("tanggal_pinjam"),
                    rs.getDate("tanggal_kembali"),
                    rs.getInt("total_pinjam")
                });
            }
            
            if (model.getRowCount() == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Data peminjaman tidak ditemukan!");
                loadData();
            }
            
        } catch (Exception e) {
            System.out.println("Error pada btnCari: " + e.getMessage());
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        MenuUtama menuUtama = (MenuUtama) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (menuUtama != null) {
            menuUtama.showPanel(new menuCRUDPeminjaman());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

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
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDetailPeminjamanMouseClicked

    private void tfCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDetailPeminjaman;
    private javax.swing.JTable tblPeminjamanBuku;
    private palette.Custom_JTextField tfCari;
    // End of variables declaration//GEN-END:variables
}
