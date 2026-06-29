/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Menu.menuBuku;
import Koneksi.koneksi;
import Tampilan.MenuUtama;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    txtIdBuku.setEditable(false); 
    txtJudul.setText(judul);
    txtPengarang.setText(pengarang);
    txtTahunTerbit.setText(tahun);
    txtJumlahHalaman.setText(jmlHalaman);
    txtStok.setText(stok);
    txtImagePath.setText(cover);
    
    // 3. Cocokkan ID pada ComboBox
    selectCombo(cbKategori, idKategori);
    selectCombo(cbPenerbit, idPenerbit);
    
    // --- TEMPATKAN KODINGAN DI SINI ---
    if (cover != null && !cover.isEmpty()) {
        lbTampilGambar.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                tampilkanGambar(System.getProperty("user.dir") + java.io.File.separator + cover);
                lbTampilGambar.removeComponentListener(this); 
            }
        });
    }
    // ----------------------------------

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
    
    private String copyFile(File sourceFile, String idBuku) {
        try {
            String rootPath = System.getProperty("user.dir");
            Path uploadDir = Paths.get(rootPath, "uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Dapatkan ekstensi file asli (misal: .jpg atau .png)
            String fileName = sourceFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));

            // Buat nama file baru: ID_Buku + Ekstensi (Contoh: B001.jpg)
            String newFileName = idBuku + extension;
            Path targetPath = uploadDir.resolve(newFileName);

            // Salin file dengan nama baru
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return "uploads/" + newFileName;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyalin gambar: " + e.getMessage());
            return null;
        }
    }
    
    private void tampilkanGambar(String path) {
        try {
            java.io.File f = new java.io.File(path);
            if (f.exists()) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(path);
                int w = lbTampilGambar.getWidth();
                int h = lbTampilGambar.getHeight();

                // Fallback jika komponen masih bernilai 0
                if (w <= 0) w = 150;
                if (h <= 0) h = 180;

                java.awt.Image img = icon.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                lbTampilGambar.setIcon(new javax.swing.ImageIcon(img));
                lbTampilGambar.setText(""); // Menghilangkan teks "ID" saat gambar tampil
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat gambar: " + e.getMessage());
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/BookIconMini.png"))); // NOI18N

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
            if (txtImagePath.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih gambar terlebih dahulu!");
                return;
            }

            // Panggil copyFile dengan mengirimkan ID Buku untuk rename
            File file = new File(txtImagePath.getText());
            String pathUntukDB = copyFile(file, txtIdBuku.getText()); 

            if (pathUntukDB == null) return; 

            String sql = "INSERT INTO buku (Id_Buku, Judul_Buku, Pengarang, Tahun_Terbit, Id_Kategori, Id_Penerbit, Jumlah_Halaman, Stok, cover) VALUES (?,?,?,?,?,?,?,?,?)";

            Connection conn = Koneksi.koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, txtIdBuku.getText());
            ps.setString(2, txtJudul.getText());
            ps.setString(3, txtPengarang.getText());
            ps.setString(4, txtTahunTerbit.getText());
            ps.setString(5, cbKategori.getSelectedItem().toString().split(" - ")[0]);
            ps.setString(6, cbPenerbit.getSelectedItem().toString().split(" - ")[0]);
            ps.setString(7, txtJumlahHalaman.getText());
            ps.setString(8, txtStok.getText());
            ps.setString(9, pathUntukDB);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");

            // Opsional: Kembali ke tabel
            btnBatalActionPerformed(evt);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Simpan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    try {
            // PERBAIKAN LOGIKA:
            // Cek apakah user memilih gambar baru melalui browse (path-nya bukan 'uploads/')
            boolean updateGambar = !txtImagePath.getText().isEmpty() && !txtImagePath.getText().contains("uploads/");

            String pathBaruUntukDB = null;
            String pathLama = null;
            Connection conn = Koneksi.koneksi.getKoneksi();

            // 1. Ambil path lama dari DB (untuk jaga-jaga kalau ternyata updateGambar = true)
            String sqlGetLama = "SELECT cover FROM buku WHERE Id_Buku = ?";
            PreparedStatement psGet = conn.prepareStatement(sqlGetLama);
            psGet.setString(1, txtIdBuku.getText());
            ResultSet rs = psGet.executeQuery();
            if (rs.next()) {
                pathLama = rs.getString("cover");
            }

            // 2. Jika user MEMILIH gambar baru (bukan sekadar data lama di textbox)
            if (updateGambar) {
                File file = new File(txtImagePath.getText());
                pathBaruUntukDB = copyFile(file, txtIdBuku.getText());
                if (pathBaruUntukDB == null) return;
            }

            // 3. Siapkan query
            String sql = updateGambar ? 
                "UPDATE buku SET Judul_Buku=?, Pengarang=?, Tahun_Terbit=?, Id_Kategori=?, Id_Penerbit=?, Jumlah_Halaman=?, Stok=?, cover=? WHERE Id_Buku=?" :
                "UPDATE buku SET Judul_Buku=?, Pengarang=?, Tahun_Terbit=?, Id_Kategori=?, Id_Penerbit=?, Jumlah_Halaman=?, Stok=? WHERE Id_Buku=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtJudul.getText());
            ps.setString(2, txtPengarang.getText());
            ps.setString(3, txtTahunTerbit.getText());
            ps.setString(4, cbKategori.getSelectedItem().toString().split(" - ")[0]);
            ps.setString(5, cbPenerbit.getSelectedItem().toString().split(" - ")[0]);
            ps.setString(6, txtJumlahHalaman.getText());
            ps.setString(7, txtStok.getText());

            if (updateGambar) {
                ps.setString(8, pathBaruUntukDB);
                ps.setString(9, txtIdBuku.getText());
            } else {
                ps.setString(8, txtIdBuku.getText());
            }

            ps.executeUpdate();

            // 4. HAPUS FILE LAMA HANYA JIKA ADA GAMBAR BARU
            if (updateGambar && pathLama != null && !pathLama.isEmpty()) {
                // Cek jika path lama berbeda dengan path baru (antisipasi rename/sama)
                if (!pathLama.equals(pathBaruUntukDB)) {
                    Path fileLama = Paths.get(System.getProperty("user.dir"), pathLama);
                    Files.deleteIfExists(fileLama);
                }
            }

            JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate!");
            btnBatalActionPerformed(evt);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Update: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBrowseGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseGambarActionPerformed
    JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            txtImagePath.setText(file.getAbsolutePath());
            tampilkanGambar(file.getAbsolutePath()); // Panggil method helper di atas
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
