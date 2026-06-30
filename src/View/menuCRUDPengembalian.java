/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Koneksi.koneksi;
import Tampilan.MenuUtama;
import View.menuCRUDPengembalian;
import Menu.menuPengembalian;
import Tampilan.PopupPilihBuku;
import java.text.SimpleDateFormat;
import java.util.Date;

public class menuCRUDPengembalian extends javax.swing.JPanel {

public menuCRUDPengembalian() {
    initComponents();
    txtTanggal.setEnabled(false);
    initTanggalKalkulasiListener();
}

private void initTanggalKalkulasiListener() {

    txtTanggal.getDateEditor().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        @Override
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if ("date".equals(evt.getPropertyName())) {
                // Saring dan validasi tanggal terlebih dahulu sebelum menghitung denda
                if (validasiTanggalPengembalian()) {
                    System.out.println("Tanggal valid, menghitung denda...");
                    hitungDendaOtomatis();
                }
            }
        }
    });
}

private boolean validasiTanggalPengembalian() {
    String tglPinjamStr = txtTanggalPinjam.getText();
    java.util.Date tglPengembalianReal = txtTanggal.getDate();

    if (tglPinjamStr == null || tglPinjamStr.trim().isEmpty() || tglPengembalianReal == null) {
        return false;
    }

    try {
        // Samakan format pembacaan teks tanggal pinjam
        String formatPattern = "yyyy-MM-dd";
        if (tglPinjamStr.contains("-") && tglPinjamStr.indexOf("-") == 2) {
            formatPattern = "dd-MM-yyyy";
        } else if (tglPinjamStr.contains("/")) {
            formatPattern = tglPinjamStr.indexOf("/") == 2 ? "dd/MM/yyyy" : "yyyy/MM/dd";
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(formatPattern);
        java.util.Date tglPinjamMurni = sdf.parse(tglPinjamStr.trim());

        // Normalisasi waktu ke 00:00:00 agar perbandingan hari akurat
        java.util.Calendar calPinjam = java.util.Calendar.getInstance();
        calPinjam.setTime(tglPinjamMurni);
        calPinjam.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calPinjam.set(java.util.Calendar.MINUTE, 0);
        calPinjam.set(java.util.Calendar.SECOND, 0);
        calPinjam.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Calendar calKembaliReal = java.util.Calendar.getInstance();
        calKembaliReal.setTime(tglPengembalianReal);
        calKembaliReal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calKembaliReal.set(java.util.Calendar.MINUTE, 0);
        calKembaliReal.set(java.util.Calendar.SECOND, 0);
        calKembaliReal.set(java.util.Calendar.MILLISECOND, 0);

        // JIKA TANGGAL KEMBALI SEBELUM TANGGAL PINJAM
        if (calKembaliReal.before(calPinjam)) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Terjadi Kesalahan!\nTanggal pengembalian tidak boleh sebelum tanggal pinjam (" + tglPinjamStr + ").", 
                "Validasi Gagal", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            
            // Reset JDateChooser menjadi kosong kembali tanpa memicu infinite loop
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    txtTanggal.setDate(null);
                    txtPoint.setText("0");
                }
            });
            return false;
        }

        return true;
    } catch (Exception e) {
        System.out.println("Error pada validasi tanggal pinjam: " + e.getMessage());
        return false;
    }
}

/**
 */
private void hitungDendaOtomatis() {

    System.out.println("========== HITUNG DENDA ==========");

    String tglKembaliSrt = txtTanggalKembali.getText();
    java.util.Date tglPengembalianReal = txtTanggal.getDate();

    System.out.println("Tanggal Kembali (Text) : " + tglKembaliSrt);
    System.out.println("Tanggal Dipilih        : " + tglPengembalianReal);

    // Jika salah satu kosong
    if (tglKembaliSrt == null || tglKembaliSrt.trim().isEmpty() || tglPengembalianReal == null) {
        System.out.println("Tanggal belum lengkap.");
        txtPoint.setText("0");
        return;
    }

    try {

        // Deteksi format tanggal
        String formatPattern = "yyyy-MM-dd";

        if (tglKembaliSrt.contains("-") && tglKembaliSrt.indexOf("-") == 2) {
            formatPattern = "dd-MM-yyyy";
        } else if (tglKembaliSrt.contains("/")) {
            if (tglKembaliSrt.indexOf("/") == 2) {
                formatPattern = "dd/MM/yyyy";
            } else {
                formatPattern = "yyyy/MM/dd";
            }
        }

        System.out.println("Format yang digunakan : " + formatPattern);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(formatPattern);
        sdf.setLenient(false);

        java.util.Date tglKembaliSeharusnya = sdf.parse(tglKembaliSrt.trim());

        System.out.println("Tanggal Kembali Parse : " + tglKembaliSeharusnya);

        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        cal1.setTime(tglKembaliSeharusnya);
        cal1.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal1.set(java.util.Calendar.MINUTE, 0);
        cal1.set(java.util.Calendar.SECOND, 0);
        cal1.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal2.setTime(tglPengembalianReal);
        cal2.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal2.set(java.util.Calendar.MINUTE, 0);
        cal2.set(java.util.Calendar.SECOND, 0);
        cal2.set(java.util.Calendar.MILLISECOND, 0);

        long selisihMilidetik = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        long selisihHari = selisihMilidetik / (1000 * 60 * 60 * 24);

        System.out.println("Selisih Hari : " + selisihHari);

        long denda = 0;

        if (selisihHari > 0) {
            denda = selisihHari * 1000;
        }

        System.out.println("Denda : " + denda);

        txtPoint.setText(String.valueOf(denda));

        System.out.println("txtPoint berhasil diisi.");

    } catch (Exception e) {

        System.out.println("=== ERROR HITUNG DENDA ===");
        e.printStackTrace();

        txtPoint.setText("0");
    }
}

public void setDetailBukuDariPopup(
    String idPinjam, String idBuku, String judul, String penerbit, String pengarang, 
    String tglPinjam, String tglKembali, String idAnggota, String namaAnggota, 
    String alamatCover // Diubah ke String untuk menerima alamat file gambar
) {
    // Mengisi data peminjaman & buku 
    txtPeminjaman.setText(idPinjam);
    txtBuku.setText(idBuku);
    txtJudul.setText(judul);
    txtPenerbit.setText(penerbit);   
    txtPengarang.setText(pengarang); 
    
    // Mengisi data anggota & tanggal yang baru diminta 
    txtTanggalPinjam.setText(tglPinjam);
    txtTanggalKembali.setText(tglKembali);
    tIdAnggota.setText(idAnggota);
    txtNamaAnggota.setText(namaAnggota);
    
    // Memuat gambar ke lblGambar berdasarkan alamat file gambar yang dikirim
    loadGambarDariAlamatPath(alamatCover);

    txtTanggal.setEnabled(true); // Buka kunci txtTanggal karena data peminjaman sudah terisi
    hitungDendaOtomatis(); // Hitung denda jika txtTanggal kebetulan sudah terisi data
}

private void loadGambarDariAlamatPath(String alamatFile) {
    if (alamatFile == null || alamatFile.trim().isEmpty() || alamatFile.equalsIgnoreCase("null")) {
        lblGambar.setIcon(null);
        lblGambar.setText("No Image");
        return;
    }

    try {
        java.io.File fileGambar = new java.io.File(alamatFile);
        
        if (fileGambar.exists()) {
            // Ambil ukuran lblGambar secara dinamis (Default layout Anda: W:170, H:190)
            int width = lblGambar.getWidth() > 0 ? lblGambar.getWidth() : 170;
            int height = lblGambar.getHeight() > 0 ? lblGambar.getHeight() : 190;
            
            // Konversi file path menjadi ImageIcon
            javax.swing.ImageIcon tempIcon = new javax.swing.ImageIcon(fileGambar.getAbsolutePath());
            java.awt.Image img = tempIcon.getImage();
            
            // Lakukan smooth scaling pas dengan ukuran JLabel komponen
            java.awt.Image scaledImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            
            lblGambar.setIcon(new javax.swing.ImageIcon(scaledImg));
            lblGambar.setText(""); // Hapus teks jika gambar berhasil ditampilkan
        } else {
            lblGambar.setIcon(null);
            lblGambar.setText("Image Not Found");
            System.out.println("File gambar tidak ditemukan di lokasi: " + alamatFile);
        }
    } catch (Exception e) {
        lblGambar.setIcon(null);
        lblGambar.setText("Error Load Image");
        System.out.println("Gagal load gambar: " + e.getMessage());
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnBack = new palette.Custom_JButton();
        btnSimpan = new palette.Custom_JButton();
        txtBuku = new palette.Custom_JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTanggalPinjam = new palette.Custom_JTextField();
        txtTanggalKembali = new palette.Custom_JTextField();
        tIdAnggota = new palette.Custom_JTextField();
        txtNamaAnggota = new palette.Custom_JTextField();
        txtPeminjaman = new palette.Custom_JTextField();
        pilihBuku = new javax.swing.JButton();
        txtJudul = new palette.Custom_JTextField();
        txtPenerbit = new palette.Custom_JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPengarang = new palette.Custom_JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        custom_JPanelRounded1 = new palette.Custom_JPanelRounded();
        jLabel3 = new javax.swing.JLabel();
        txtId = new palette.Custom_JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        lblGambar = new javax.swing.JLabel();
        txtPoint = new palette.Custom_JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setText("Menu Pengembalian Buku Perpustakaan");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setText("Transaksi > Pengembalian");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 460, -1));

        btnBack.setText("KEMBALI");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));
        add(txtBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 250, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Peminjaman");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Tanggal Pinjam");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Tanggal Kembali");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Id Anggota");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Nama Anggota");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));
        add(txtTanggalPinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 255, -1));
        add(txtTanggalKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 255, -1));
        add(tIdAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 255, -1));
        add(txtNamaAnggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 255, -1));
        add(txtPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 204, -1));

        pilihBuku.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pilihBuku.setText("...");
        pilihBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihBukuActionPerformed(evt);
            }
        });
        add(pilihBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, 30));
        add(txtJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 230, 250, -1));

        txtPenerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPenerbitActionPerformed(evt);
            }
        });
        add(txtPenerbit, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 250, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Buku");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Judul");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, -1, -1));
        add(txtPengarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 240, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Penerbit");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Pengarang");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("ID");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Tanggal");

        txtTanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTanggalKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout custom_JPanelRounded1Layout = new javax.swing.GroupLayout(custom_JPanelRounded1);
        custom_JPanelRounded1.setLayout(custom_JPanelRounded1Layout);
        custom_JPanelRounded1Layout.setHorizontalGroup(
            custom_JPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custom_JPanelRounded1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        custom_JPanelRounded1Layout.setVerticalGroup(
            custom_JPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, custom_JPanelRounded1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(custom_JPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(custom_JPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(19, 19, 19))
        );

        add(custom_JPanelRounded1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1080, 70));

        lblGambar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(lblGambar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, 170, 190));
        add(txtPoint, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 430, 120, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("Point");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 430, -1, 30));

        jLabel15.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel15.setText("Info Point");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 470, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtPenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPenerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPenerbitActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    String idPengembalian = (txtId.getText() != null) ? txtId.getText().trim() : "";
    String idPeminjaman   = txtPeminjaman.getText().trim();
    java.util.Date tglHariIni = txtTanggal.getDate(); 

    // Ambil juga ID Buku yang akan dikembalikan dari form
    String idBuku         = txtBuku.getText().trim(); 

    // 1. Ambil nilai denda keterlambatan dari txtPoint ke variabel pointFinal
    int pointFinal = 0;
    try {
        pointFinal = Integer.parseInt(txtPoint.getText().trim());
    } catch (Exception e) {
        pointFinal = 0;
    }

    // GENERATE ID JIKA KOSONG
    if (idPengembalian.equals("") || idPengembalian.equalsIgnoreCase("null")) {
        idPengembalian = "KMB" + System.currentTimeMillis(); 
    }

    // VALIDASI INPUT UTAMA
    if (idPeminjaman.isEmpty() || idBuku.isEmpty() || tglHariIni == null) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Data Peminjaman, Buku, atau Tanggal Pengembalian belum dipilih!", 
            "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    java.sql.Connection con = null;
    java.sql.PreparedStatement psInsert = null;
    java.sql.PreparedStatement psUpdateStatus = null;

    try {
        // Ambil Koneksi Database
        con = Koneksi.koneksi.getKoneksi();

        // Matikan AutoCommit untuk menjalankan Transaction (menjaga integritas data)
        con.setAutoCommit(false);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String tglPengembalianFormat = sdf.format(tglHariIni);

        // 2. QUERY INSERT ke tabel pengembalian (Disesuaikan dengan kolom huruf kecil sesuai phpMyAdmin)
        String sqlInsert = "INSERT INTO pengembalian (id_pengembalian, tgl_pengembalian, id_peminjaman, point) VALUES (?, ?, ?, ?)";
        psInsert = con.prepareStatement(sqlInsert);

        psInsert.setString(1, idPengembalian);
        psInsert.setString(2, tglPengembalianFormat);
        psInsert.setString(3, idPeminjaman);
        psInsert.setInt(4, pointFinal); 

        int hasilInsert = psInsert.executeUpdate();

        // 3. QUERY UPDATE status pada tabel detail_pinjam
        // DISESUAIKAN: Nama tabel menjadi 'detail_pinjam', nama kolom menjadi 'status_pinjam' (huruf kecil)
        String sqlUpdateStatus = "UPDATE detail_pinjam SET status_pinjam = 'Sudah dikembalikan' WHERE Id_Pinjam = ? AND Id_Buku = ?";
        psUpdateStatus = con.prepareStatement(sqlUpdateStatus);
        psUpdateStatus.setString(1, idPeminjaman);
        psUpdateStatus.setString(2, idBuku);

        int hasilUpdate = psUpdateStatus.executeUpdate();

        // JIKA KEDUANYA BERHASIL
        if (hasilInsert > 0 && hasilUpdate > 0) {
            con.commit(); // Simpan permanen perubahan ke database
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Data Pengembalian Sukses Disimpan!\nStatus buku pada detail peminjaman telah diperbarui.", 
                "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            txtId.setText(idPengembalian);

            // Opsional: bersihkan form atau muat ulang tabel jika diperlukan
        } else {
            con.rollback(); // Batalkan jika salah satu query gagal
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memproses data secara lengkap.");
        }

    } catch (Exception e) {
        try {
            if (con != null) {
                con.rollback(); // Rollback jika ada error runtime sql
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("Gagal Rollback: " + ex.getMessage());
        }

        javax.swing.JOptionPane.showMessageDialog(this, "Gagal Simpan Database: " + e.getMessage(), 
            "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        // Pastikan AutoCommit dikembalikan ke true dan resources ditutup
        try { if (con != null) con.setAutoCommit(true); } catch (Exception e) {}
        try { if (psInsert != null) psInsert.close(); } catch (Exception e) {}
        try { if (psUpdateStatus != null) psUpdateStatus.close(); } catch (Exception e) {}
        
        MenuUtama menuUtama = (MenuUtama) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (menuUtama != null) menuUtama.showPanel(new menuPengembalian()); 
    }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void pilihBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihBukuActionPerformed
        Tampilan.PopupPilihBuku popup = new Tampilan.PopupPilihBuku(this);
        popup.setVisible(true);
    }//GEN-LAST:event_pilihBukuActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
            MenuUtama menuUtama = (MenuUtama) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (menuUtama != null) menuUtama.showPanel(new menuPengembalian());        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtTanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.Custom_JButton btnBack;
    private palette.Custom_JButton btnSimpan;
    private palette.Custom_JPanelRounded custom_JPanelRounded1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblGambar;
    private javax.swing.JButton pilihBuku;
    private palette.Custom_JTextField tIdAnggota;
    private palette.Custom_JTextField txtBuku;
    private palette.Custom_JTextField txtId;
    private palette.Custom_JTextField txtJudul;
    private palette.Custom_JTextField txtNamaAnggota;
    private palette.Custom_JTextField txtPeminjaman;
    private palette.Custom_JTextField txtPenerbit;
    private palette.Custom_JTextField txtPengarang;
    private palette.Custom_JTextField txtPoint;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private palette.Custom_JTextField txtTanggalKembali;
    private palette.Custom_JTextField txtTanggalPinjam;
    // End of variables declaration//GEN-END:variables
}

