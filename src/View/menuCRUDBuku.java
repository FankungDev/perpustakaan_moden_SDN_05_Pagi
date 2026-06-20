/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import javax.swing.table.DefaultTableModel;
import Koneksi.koneksi; 
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import Tampilan.MenuUtama;
import java.util.Base64;
import java.io.*;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author rafli
 */
public class menuCRUDBuku extends javax.swing.JPanel {

    
    public menuCRUDBuku() {
        initComponents();
        btnSimpan.setVisible(false); // Sembunyikan Simpan, tampilkan Tambah
        btnTambah.setVisible(true);
        loadKategori();
        loadPenerbit();
    }
    
    public menuCRUDBuku(String idBuku, String judul, String pengarang, String tahun, 
                    String idKategori, String idPenerbit, String jmlHalaman, 
                    String stok, String cover) {
    initComponents();
    
    // 1. Load Data ke ComboBox terlebih dahulu
    loadComboKategori();
    loadComboPenerbit();
    
    // 2. Set data ke text field
    txtIdBuku.setText(idBuku);
    txtIdBuku.setEditable(false); // ID biasanya tidak bisa diubah
    txtJudul.setText(judul);
    txtPengarang.setText(pengarang);
    txtTahunTerbit.setText(tahun);
    txtJumlahHalaman.setText(jmlHalaman);
    txtStok.setText(stok);
    txtImagePath.setText(cover);
    
    // 3. Cocokkan ID pada ComboBox
    selectCombo(cbKategori, idKategori);
    selectCombo(cbPenerbit, idPenerbit);
    
    // 4. Load Gambar dengan jalur yang benar
    if (cover != null && !cover.isEmpty()) {
        String projectPath = System.getProperty("user.dir");
        java.io.File fileGambar = new java.io.File(projectPath, cover);
        
        System.out.println("Mencoba load gambar di: " + fileGambar.getAbsolutePath());

        if (fileGambar.exists()) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                try {
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(fileGambar.getAbsolutePath());
                    // Mendapatkan ukuran label saat ini
                    int w = lbTampilGambar.getWidth();
                    int h = lbTampilGambar.getHeight();
                    
                    // Fallback jika label belum ter-render (masih 0)
                    if (w <= 0) w = 150;
                    if (h <= 0) h = 180;
                    
                    java.awt.Image img = icon.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                    lbTampilGambar.setIcon(new javax.swing.ImageIcon(img));
                } catch (Exception e) {
                    System.out.println("Gagal memuat gambar: " + e.getMessage());
                }
            });
        } else {
            System.out.println("FILE TIDAK DITEMUKAN: " + fileGambar.getAbsolutePath());
        }
    }

    // 5. Pengaturan tombol
    btnTambah.setVisible(false);
    btnSimpan.setVisible(true);
}
    
    private void selectCombo(javax.swing.JComboBox combo, String id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i).toString();
            // Memastikan pencarian ID tepat di depan
            if (item.startsWith(id + " -")) { 
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void loadComboKategori() {
        try {
            cbKategori.removeAllItems(); // Bersihkan isi combo terlebih dahulu
            Connection conn = Koneksi.koneksi.getKoneksi();
            String sql = "SELECT id_kategori, nama_kategori FROM kategori_buku";
            java.sql.Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                // Kita simpan dengan format: ID - Nama
                cbKategori.addItem(rs.getString("id_kategori") + " - " + rs.getString("nama_kategori"));
            }
        } catch (Exception e) {
            System.out.println("Error load Kategori: " + e.getMessage());
        }
    }
    
    private void loadComboPenerbit() {
        try {
            cbPenerbit.removeAllItems(); // Bersihkan isi combo terlebih dahulu
            Connection conn = Koneksi.koneksi.getKoneksi();
            String sql = "SELECT id_penerbit, nama_penerbit FROM penerbit";
            java.sql.Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                // Kita simpan dengan format: ID - Nama
                cbPenerbit.addItem(rs.getString("id_penerbit") + " - " + rs.getString("nama_penerbit"));
            }
        } catch (Exception e) {
            System.out.println("Error load Penerbit: " + e.getMessage());
        }
    }
    
    public void loadKategori() {
        cbKategori.removeAllItems(); // Hapus isi lama
        try {
            String sql = "SELECT * FROM kategori_Buku"; // Sesuaikan nama tabel Anda
            Connection conn = Koneksi.koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                // Format: "ID - Nama"
                cbKategori.addItem(rs.getString("Id_Kategori") + " - " + rs.getString("Nama_Kategori"));
            }
        } catch (Exception e) {
            System.out.println("Gagal load kategori: " + e.getMessage());
        }
    }
    
    private String copyFile(File sourceFile) {
    try {
        // Buat folder 'uploads' jika belum ada
        File directory = new File("uploads");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Tentukan lokasi tujuan
        Path destPath = Paths.get("uploads", sourceFile.getName());
        
        // Copy file ke folder uploads
        Files.copy(sourceFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        
        // Kembalikan path untuk disimpan ke DB
        return "uploads/" + sourceFile.getName();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyalin gambar: " + e.getMessage());
            return null;
        }
    }
    
    
    
    
    public void loadPenerbit() {
        cbPenerbit.removeAllItems(); // Hapus isi lama
        try {
            String sql = "SELECT * FROM penerbit"; // Sesuaikan nama tabel Anda
            Connection conn = Koneksi.koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                // Format: "ID - Nama"
                cbPenerbit.addItem(rs.getString("Id_Penerbit") + " - " + rs.getString("Nama_Penerbit"));
            }
        } catch (Exception e) {
            System.out.println("Gagal load penerbit: " + e.getMessage());
        }
    }
    
  
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        gender = new javax.swing.ButtonGroup();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtIdBuku = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtJudul = new javax.swing.JTextField();
        txtTahunTerbit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPengarang = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtJumlahHalaman = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbPenerbit = new javax.swing.JComboBox<>();
        cbKategori = new javax.swing.JComboBox<>();
        txtImagePath = new javax.swing.JTextField();
        btnBrowseGambar = new javax.swing.JButton();
        lbTampilGambar = new javax.swing.JLabel();

        jPasswordField1.setText("jPasswordField1");

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/peopleIconKecil.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Data Buku Perpustakaan");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Master Data >Buku > Tambah Buku");

        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("ID");

        txtIdBuku.setForeground(new java.awt.Color(153, 153, 153));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Judul");

        txtJudul.setForeground(new java.awt.Color(153, 153, 153));

        txtTahunTerbit.setForeground(new java.awt.Color(153, 153, 153));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Tahun Terbit");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Nama Kategori");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Nama Penerbit");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Pengarang");

        txtPengarang.setForeground(new java.awt.Color(153, 153, 153));

        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Jumlah Halaman");

        txtJumlahHalaman.setForeground(new java.awt.Color(153, 153, 153));

        txtStok.setForeground(new java.awt.Color(153, 153, 153));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Stok");

        cbPenerbit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtImagePath.setForeground(new java.awt.Color(153, 153, 153));

        btnBrowseGambar.setText("...");
        btnBrowseGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseGambarActionPerformed(evt);
            }
        });

        lbTampilGambar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTampilGambar.setText("ID");
        lbTampilGambar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtStok, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtJumlahHalaman, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 434, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIdBuku, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
                                        .addComponent(txtJudul, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPengarang, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTahunTerbit, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jLabel4)
                                    .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(cbPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBrowseGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lbTampilGambar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(95, 95, 95))))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtIdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTahunTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(11, 11, 11)
                        .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbTampilGambar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtJumlahHalaman, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(248, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
            MenuUtama menuUtama = (MenuUtama) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (menuUtama != null) {
            menuUtama.showPanel(new menuBuku());
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    try {
        // 1. Validasi: Pastikan path tidak kosong
        if (txtImagePath.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih gambar terlebih dahulu!");
            return;
        }

        // 2. Salin gambar ke folder 'uploads' dan dapatkan path relatifnya
        File file = new File(txtImagePath.getText());
        String pathUntukDB = copyFile(file); 
        
        if (pathUntukDB == null) {
            return; // Berhenti jika gagal copy
        }

        // 3. Eksekusi Insert
        String sql = "INSERT INTO buku (Id_Buku, Judul_Buku, Pengarang, Tahun_Terbit, Id_Kategori, Id_Penerbit, Jumlah_Halaman, Stok, cover) VALUES (?,?,?,?,?,?,?,?,?)";
        
        Connection conn = Koneksi.koneksi.getKoneksi();
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, txtIdBuku.getText());
        ps.setString(2, txtJudul.getText());
        ps.setString(3, txtPengarang.getText());
        ps.setString(4, txtTahunTerbit.getText());
        
        // Split ID dari ComboBox
        ps.setString(5, cbKategori.getSelectedItem().toString().split(" - ")[0]);
        ps.setString(6, cbPenerbit.getSelectedItem().toString().split(" - ")[0]);
        
        ps.setString(7, txtJumlahHalaman.getText());
        ps.setString(8, txtStok.getText());
        ps.setString(9, pathUntukDB); // Simpan "uploads/namafile.jpg"
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");
        
        // Bersihkan form setelah simpan
        // (opsional: tambahkan fungsi clearForm() di sini)
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal Simpan: " + e.getMessage());
        e.printStackTrace(); // Penting untuk melihat detail error di log
    }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
        String sql;
        boolean updateGambar = !txtImagePath.getText().isEmpty();

        if (!updateGambar) {
            // Update tanpa mengubah cover
            sql = "UPDATE buku SET Judul_Buku=?, Pengarang=?, Tahun_Terbit=?, Id_Kategori=?, Id_Penerbit=?, Jumlah_Halaman=?, Stok=? WHERE Id_Buku=?";
        } else {
            // Update dengan path gambar baru
            sql = "UPDATE buku SET Judul_Buku=?, Pengarang=?, Tahun_Terbit=?, Id_Kategori=?, Id_Penerbit=?, Jumlah_Halaman=?, Stok=?, cover=? WHERE Id_Buku=?";
        }

        Connection conn = Koneksi.koneksi.getKoneksi();
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, txtJudul.getText());
        ps.setString(2, txtPengarang.getText());
        ps.setString(3, txtTahunTerbit.getText());
        ps.setString(4, cbKategori.getSelectedItem().toString().split(" - ")[0]);
        ps.setString(5, cbPenerbit.getSelectedItem().toString().split(" - ")[0]);
        ps.setString(6, txtJumlahHalaman.getText());
        ps.setString(7, txtStok.getText());
        
        if (!updateGambar) {
            ps.setString(8, txtIdBuku.getText());
        } else {
            ps.setString(8, txtImagePath.getText()); // Simpan path baru
            ps.setString(9, txtIdBuku.getText());
        }
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate!");
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Update: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBrowseGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseGambarActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            txtImagePath.setText(file.getAbsolutePath()); // Menyimpan lokasi file

            try {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(file.getAbsolutePath());
                java.awt.Image img = icon.getImage().getScaledInstance(lbTampilGambar.getWidth(), lbTampilGambar.getHeight(), java.awt.Image.SCALE_SMOOTH);
                lbTampilGambar.setIcon(new javax.swing.ImageIcon(img));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnBrowseGambarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBrowseGambar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbKategori;
    private javax.swing.JComboBox<String> cbPenerbit;
    private javax.swing.ButtonGroup gender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbTampilGambar;
    private javax.swing.JTextField txtIdBuku;
    private javax.swing.JTextField txtImagePath;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextField txtJumlahHalaman;
    private javax.swing.JTextField txtPengarang;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtTahunTerbit;
    // End of variables declaration//GEN-END:variables
}
