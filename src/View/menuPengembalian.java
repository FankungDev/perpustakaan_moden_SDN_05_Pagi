/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Koneksi.koneksi;
import Tampilan.MenuUtama;

public class menuPengembalian extends javax.swing.JPanel {

public menuPengembalian() {
    initComponents();
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    btnSimpan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSimpanActionPerformed(evt);
        }
    });
    btnKembali.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnKembaliActionPerformed(evt);
        }
    });
}

    public void itemTerpilihPeminjaman(String idPinjam, String tglPinjam, String tglKembali,
        String idAnggota, String namaAnggota, String idBuku, String judulBuku,
        String pengarang, String penerbit) {
        txtPeminjaman.setText(idPinjam);
        txtTanggalPinjam.setText(tglPinjam);
        txtTanggalKembali.setText(tglKembali);
        tIdAnggota.setText(idAnggota);
        txtNamaAnggota.setText(namaAnggota);
        txtBuku.setText(idBuku);
        txtJudul.setText(judulBuku);
        txtPengarang.setText(pengarang);
        txtPenerbit.setText(penerbit);
    }
    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.Frame frame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        Tampilan.DialongPilihPengembalian dialog = new Tampilan.DialongPilihPengembalian(frame, true, this);
        dialog.setVisible(true);
    }

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {
        MenuUtama menuUtama = (MenuUtama) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (menuUtama != null) menuUtama.showPanel(new menuCRUDPengembalian());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnKembali = new palette.Custom_JButton();
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
        jButton1 = new javax.swing.JButton();
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

        btnKembali.setText("KEMBALI");
        add(btnKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

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

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setText("...");
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, 30));
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
    String idPengembalian = (txtId.getText() != null) ? txtId.getText() : "";
    String idPeminjaman   = txtPeminjaman.getText();
    String tglKembali     = txtTanggalKembali.getText();
    java.util.Date tglHariIni = txtTanggal.getDate(); 
    
    if (idPengembalian.equals("") || idPengembalian.equalsIgnoreCase("null")) {
        idPengembalian = "KMB" + System.currentTimeMillis(); 
    }
    
    if (idPeminjaman.isEmpty() || tglHariIni == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Data Peminjaman atau Tanggal Pengembalian belum dipilih!");
        return;
    }

    try {
        Koneksi.koneksi kon = new Koneksi.koneksi();
        java.sql.Connection conn = kon.getKoneksi();
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String tglPengembalianFormat = sdf.format(tglHariIni);

        String sql = "INSERT INTO pengembalian (id_pengembalian, tgl_pengembalian, id_peminjaman, denda) VALUES (?, ?, ?, ?)";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, idPengembalian);
        ps.setString(2, tglPengembalianFormat);
        ps.setString(3, idPeminjaman);
        ps.setInt(4, 0);
        
        int hasil = ps.executeUpdate();
        if (hasil > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Data Pengembalian Sukses Disimpan!");
            txtId.setText(idPengembalian);
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal Simpan Database: " + e.getMessage());
    }

    }//GEN-LAST:event_btnSimpanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.Custom_JButton btnKembali;
    private palette.Custom_JButton btnSimpan;
    private palette.Custom_JPanelRounded custom_JPanelRounded1;
    private javax.swing.JButton jButton1;
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

