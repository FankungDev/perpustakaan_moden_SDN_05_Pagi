/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Menu.menuKategori;
import javax.swing.table.DefaultTableModel;
import Koneksi.koneksi; // Sesuaikan dengan package koneksi Anda
import Menu.menuPeminjaman;
import Tampilan.MenuUtama;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;

/**
 *
 * @author rafli
 */
public class menuCRUDPeminjaman extends javax.swing.JPanel {
    public String nis, nama_anggota, email_anggota, telp_anggota;
    public String id_buku, judul_buku, pengarang_buku, penerbit_buku, nama_gambar;
    /**
     * Creates new form menuAnggota
     */
    public menuCRUDPeminjaman() {
        initComponents();
        this.btnBatalUpdate.setVisible(false);
        this.btnUpdate.setVisible(false);
        txtIDPinjam.setText(generateIdPinjam());
        txtTglPinjam.setDate(new java.util.Date());
        java.awt.Dimension ukuranTetap = new java.awt.Dimension(289, 38);
    
        txtIdBuku.setPreferredSize(ukuranTetap);
        txtIdBuku.setMaximumSize(ukuranTetap);
        txtIdBuku.setMinimumSize(ukuranTetap);

        txtJudul.setPreferredSize(ukuranTetap);
        txtJudul.setMaximumSize(ukuranTetap);
        txtJudul.setMinimumSize(ukuranTetap);

        txtPengarang.setPreferredSize(ukuranTetap);
        txtPengarang.setMaximumSize(ukuranTetap);
        txtPengarang.setMinimumSize(ukuranTetap);

        txtPenerbit.setPreferredSize(ukuranTetap);
        txtPenerbit.setMaximumSize(ukuranTetap);
        txtPenerbit.setMinimumSize(ukuranTetap);

        // Atur kolom untuk tabel keranjang sementara
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Buku");
        model.addColumn("Judul Buku");
        model.addColumn("Pengarang");
        model.addColumn("Penerbit");
        model.addColumn("Jumlah Pinjam");
        dataTabelPinjam.setModel(model);
        generateIdPinjam();
    }
    
    private String generateIdPinjam() {
    String kodeOtomatis = "PJM001";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
        try {
            conn = Koneksi.koneksi.getKoneksi();
            // Mengambil Id_Pinjam yang paling besar / terakhir dimasukkan
            String sql = "SELECT Id_Pinjam FROM peminjaman ORDER BY Id_Pinjam DESC LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String idTerakhir = rs.getString("Id_Pinjam"); 

                int angka = Integer.parseInt(idTerakhir.substring(3)); 
                angka++;

                kodeOtomatis = String.format("PJM%03d", angka); 
            }
        } catch (Exception e) {
            System.out.println("Error saat membuat ID otomatis: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }

        return kodeOtomatis;
    }

        private void clearFormBuku() {
        txtIdBuku.setText("");
        txtJudul.setText("");
        txtPengarang.setText("");
        txtPenerbit.setText("");
        txtJumlahBuku.setText("");
    }
    
    private void hitungTotalPinjam() {
        int total = 0;
        int jumlahBaris = dataTabelPinjam.getRowCount();

        for (int i = 0; i < jumlahBaris; i++) {
            int qty = Integer.parseInt(dataTabelPinjam.getValueAt(i, 4).toString());
            total += qty;
        }
        lblTotalPinjam.setText(String.valueOf(total));
    }
    
    public void loadDataTabelPinjam() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Buku");
    model.addColumn("Judul Buku");
    model.addColumn("Pengarang");
    model.addColumn("Penerbit");
    model.addColumn("Jumlah Pinjam");
    
    try {
        Connection conn = koneksi.getKoneksi(); 
        
        // Query disesuaikan dengan screenshot: mengambil dari view_peminjaman_detail
        // Asumsi: Filter berdasarkan NIS/ID Anggota. Silakan ganti 'id_anggota' atau 'nis' sesuai nama kolom asli di tabel/view Anda.
        String sql = "SELECT Id_Buku, Judul_Buku, pengarang, penerbit, Jumlah_Pinjam "
                   + "FROM view_peminjaman_detail WHERE nis = ?"; 
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, txtNIS.getText());
        ResultSet rs = ps.executeQuery();
        
        int totalPinjam = 0;
        
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("Id_Buku"),
                rs.getString("Judul_Buku"),
                rs.getString("pengarang"),
                rs.getString("penerbit"),
                rs.getInt("Jumlah_Pinjam")
            });
            
            totalPinjam += rs.getInt("Jumlah_Pinjam");
        }
        dataTabelPinjam.setModel(model);
        
        lblTotalPinjam.setText(String.valueOf(totalPinjam));
        
        } catch (Exception e) {
            System.out.println("Error load data tabel pinjam: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data pinjaman: " + e.getMessage());
        }
    }
    
    public menuCRUDPeminjaman(String id, String nama, String deskripsi) {
        initComponents();
        txtNIS.setText(id);
        tfNamaKategori.setText(nama);
        tfDeskripsi.setText(deskripsi);
        
        txtNIS.setEditable(false);
    }
    
    public void itemTerpilihAnggota() {
        txtNIS.setText(nis);
        tfNamaKategori.setText(nama_anggota);
        tfDeskripsi.setText(email_anggota);
        txtTelpon.setText(telp_anggota);

        txtNIS.setEditable(false);
        tfNamaKategori.setEditable(false);
        tfDeskripsi.setEditable(false);
        txtTelpon.setEditable(false);
    }
    
    public void itemTerpilihBuku() {
        txtIdBuku.setText(id_buku);
        txtJudul.setText(judul_buku);
        txtPengarang.setText(pengarang_buku);
        txtPenerbit.setText(penerbit_buku);
        
        txtIdBuku.setEditable(false);
        txtJudul.setEditable(false);
        txtPengarang.setEditable(false);
        txtPenerbit.setEditable(false);
        
        if (nama_gambar != null && !nama_gambar.isEmpty()) {
            try {
                
                java.nio.file.Path pathLengkap = java.nio.file.Paths.get(System.getProperty("user.dir"), nama_gambar);
                java.io.File fileGambar = pathLengkap.toFile();

                if (fileGambar.exists()) {
                    
                    javax.swing.ImageIcon imageIcon = new javax.swing.ImageIcon(fileGambar.getAbsolutePath());
                    
                    java.awt.Image image = imageIcon.getImage().getScaledInstance(170, 220, java.awt.Image.SCALE_SMOOTH);
                    
                    lblGambar.setIcon(new javax.swing.ImageIcon(image));
                    lblGambar.setText(""); // Hapus teks jLabel
                } else {
                    lblGambar.setIcon(null);
                    lblGambar.setText("File tidak ditemukan");
                }
            } catch (Exception e) {
                lblGambar.setIcon(null);
                lblGambar.setText("Gambar Gagal Dimuat");
            }
        } else {
            lblGambar.setIcon(null);
            lblGambar.setText("No Image");
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

        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNIS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfNamaKategori = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfDeskripsi = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtIDPinjam = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTglPinjam = new com.toedter.calendar.JDateChooser();
        txtTglKembali = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelpon = new javax.swing.JTextField();
        btnCariDataAnggota = new javax.swing.JButton();
        txtIdBuku = new javax.swing.JTextField();
        txtJudul = new javax.swing.JTextField();
        txtPengarang = new javax.swing.JTextField();
        txtPenerbit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnCariDataBuku = new javax.swing.JButton();
        lblGambar = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtJumlahBuku = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        lblTotalPinjam = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTabelPinjam = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnBatalUpdate = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Tambah Peminjaman Buku");

        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Master Data > Peminjaman > Tambah Peminjaman");

        jLabel3.setText("NIS");

        jLabel4.setText("Nama");

        tfNamaKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaKategoriActionPerformed(evt);
            }
        });

        jLabel5.setText("Email");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel11.setText("ID Pinjam");

        jLabel15.setText("Tanggal Pinjam");

        jLabel16.setText("Tanggal Kembali");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(46, 46, 46)
                .addComponent(txtIDPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(224, 224, 224)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTglPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(txtTglKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTglKembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIDPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel15))
                    .addComponent(txtTglPinjam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Telepon");

        btnCariDataAnggota.setText("....");
        btnCariDataAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDataAnggotaActionPerformed(evt);
            }
        });

        txtJudul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJudulActionPerformed(evt);
            }
        });

        jLabel7.setText("ID Buku");

        jLabel8.setText("Judul");

        jLabel9.setText("Pengarang");

        jLabel10.setText("Penerbit");

        btnCariDataBuku.setText("....");
        btnCariDataBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDataBukuActionPerformed(evt);
            }
        });

        lblGambar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("Jumlah");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setText("Total Pinjam");

        lblTotalPinjam.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalPinjam.setText("Total");

        dataTabelPinjam.setModel(new javax.swing.table.DefaultTableModel(
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
        dataTabelPinjam.setRowHeight(50);
        dataTabelPinjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataTabelPinjamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dataTabelPinjam);

        btnUpdate.setText("UBAH");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnBatalUpdate.setText("BATAL");
        btnBatalUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBatalUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfDeskripsi)
                                    .addComponent(tfNamaKategori, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNIS, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCariDataAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel7))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPengarang)
                                    .addComponent(txtJudul, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdBuku, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnCariDataBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(lblGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(txtJumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(lblTotalPinjam))))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNIS, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(btnCariDataAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tfNamaKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(btnCariDataBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtJumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalPinjam))
                    .addComponent(lblGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatalUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        MenuUtama menuUtama = (MenuUtama) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (menuUtama != null) {
            menuUtama.showPanel(new menuPeminjaman());
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(txtIdBuku.getText().isEmpty() || txtJumlahBuku.getText().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih buku dan isi jumlah pinjam terlebih dahulu!");
        return;
    }
        
        DefaultTableModel model = (DefaultTableModel) dataTabelPinjam.getModel();
        
        model.addRow(new Object[]{
            txtIdBuku.getText(),
            txtJudul.getText(),
            txtPengarang.getText(),
            txtPenerbit.getText(),
            txtJumlahBuku.getText() 
        });
        
        hitungTotalPinjam();
        
        txtIdBuku.setText("");
        txtJudul.setText("");
        txtPengarang.setText("");
        txtPenerbit.setText("");
        txtJumlahBuku.setText("");
        lblGambar.setIcon(null);
        lblGambar.setText("No Image");
    }//GEN-LAST:event_btnTambahActionPerformed
    
    
    
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    // 1. VALIDASI FORM
    int jumlahBaris = dataTabelPinjam.getRowCount();
    
    if (jumlahBaris == 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Tabel pinjaman kosong! Tambahkan buku terlebih dahulu.");
        return; 
    }
    if (txtIDPinjam.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "ID Pinjam wajib diisi!");
        return;
    }
    if (txtNIS.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Anggota belum dipilih!");
        return;
    }
    if (txtTglPinjam.getDate() == null || txtTglKembali.getDate() == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Tanggal Pinjam & Kembali harus diisi!");
        return;
    }
    
    Connection conn = null;
    try {
        conn = Koneksi.koneksi.getKoneksi();
        conn.setAutoCommit(false);
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String tglPinjam = sdf.format(txtTglPinjam.getDate());
        String tglKembali = sdf.format(txtTglKembali.getDate());
        
        String sqlMaster = "INSERT INTO peminjaman (Id_Pinjam, Nis, Tanggal_Pinjam, Tanggal_Kembali) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psMaster = conn.prepareStatement(sqlMaster)) {
            psMaster.setString(1, txtIDPinjam.getText().trim());
            psMaster.setString(2, txtNIS.getText().trim());
            psMaster.setString(3, tglPinjam);
            psMaster.setString(4, tglKembali);
            psMaster.executeUpdate();
        }
        
        String sqlDetail = "INSERT INTO detail_pinjam (Id_Pinjam, Id_Buku, Jumlah_Pinjam, Status_Pinjam) VALUES (?, ?, ?, ?)";
        
        String sqlUpdateStok = "UPDATE buku SET Stok = Stok - ? WHERE Id_Buku = ?";
        
        String sqlCekStok = "SELECT Stok, Judul_buku FROM buku WHERE Id_Buku = ?";
        
        try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
             PreparedStatement psUpdateStok = conn.prepareStatement(sqlUpdateStok);
             PreparedStatement psCekStok = conn.prepareStatement(sqlCekStok)) {

            for (int i = 0; i < jumlahBaris; i++) {
                String idBuku = dataTabelPinjam.getValueAt(i, 0).toString();
                String namaBuku = dataTabelPinjam.getValueAt(i, 1).toString(); 
                int qty = Integer.parseInt(dataTabelPinjam.getValueAt(i, 4).toString());
                
                psCekStok.setString(1, idBuku);
                try (ResultSet rsStok = psCekStok.executeQuery()) {
                    if (rsStok.next()) {
                        int stokSekarang = rsStok.getInt("Stok");
                        
                        if (stokSekarang < qty) {
                            javax.swing.JOptionPane.showMessageDialog(this, 
                                "Gagal! Stok buku '" + namaBuku + "' tidak mencukupi.\n" +
                                "Stok tersedia: " + stokSekarang + ", Jumlah diminta: " + qty, 
                                "Stok Kurang", javax.swing.JOptionPane.WARNING_MESSAGE);
                            
                            conn.rollback();
                            return; 
                        }
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(this, "Buku dengan ID " + idBuku + " tidak ditemukan!");
                        conn.rollback();
                        return;
                    }
                }
                
                psDetail.setString(1, txtIDPinjam.getText().trim());
                psDetail.setString(2, idBuku);
                psDetail.setInt(3, qty);
                psDetail.setString(4, "Dipinjam"); 
                psDetail.addBatch();
                
                psUpdateStok.setInt(1, qty); 
                psUpdateStok.setString(2, idBuku);
                psUpdateStok.addBatch();
            }
            
            psDetail.executeBatch(); 
            psUpdateStok.executeBatch(); 
        }
        
        conn.commit();
        javax.swing.JOptionPane.showMessageDialog(this, "Data peminjaman berhasil disimpan & Stok buku berhasil diperbarui!");
        
        clearFormBuku();
        txtNIS.setText("");
        tfNamaKategori.setText("");
        tfDeskripsi.setText("");
        txtTelpon.setText("");
        txtTglPinjam.setDate(null);
        txtTglKembali.setDate(null);
        
        DefaultTableModel model = (DefaultTableModel) dataTabelPinjam.getModel();
        model.setRowCount(0);
        lblTotalPinjam.setText("0");
        
        txtIDPinjam.setText(generateIdPinjam());

    } catch (Exception e) {
        
        if (conn != null) {
            try { conn.rollback(); } catch (Exception ex) { System.out.println(ex.getMessage()); }
        }
        System.out.println("Error simpan data detail: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    } finally {
        if (conn != null) {
            try { conn.setAutoCommit(true); } catch (Exception ex) {}
        }
    }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tfNamaKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaKategoriActionPerformed

    private void txtJudulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJudulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJudulActionPerformed

    private void btnCariDataBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDataBukuActionPerformed
    Tampilan.PopupDataBuku popupBuku = new Tampilan.PopupDataBuku();
    popupBuku.crudPeminjaman = this;
    popupBuku.setVisible(true);
    }//GEN-LAST:event_btnCariDataBukuActionPerformed

    private void dataTabelPinjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataTabelPinjamMouseClicked
    int barisTerpilih = dataTabelPinjam.getSelectedRow();
    
    if (barisTerpilih != -1) {
        
        String idBuku = dataTabelPinjam.getValueAt(barisTerpilih, 0).toString();      
        String judulBuku = dataTabelPinjam.getValueAt(barisTerpilih, 1).toString();  
        String pengarang = dataTabelPinjam.getValueAt(barisTerpilih, 2).toString();   
        String penerbit = dataTabelPinjam.getValueAt(barisTerpilih, 3).toString();    
        String jumlahPinjam = dataTabelPinjam.getValueAt(barisTerpilih, 4).toString(); 

        txtIdBuku.setText(idBuku);
        txtJudul.setText(judulBuku);
        txtPengarang.setText(pengarang);
        txtPenerbit.setText(penerbit);
        txtJumlahBuku.setText(jumlahPinjam);
        
        this.btnBatalUpdate.setVisible(true);
        this.btnUpdate.setVisible(true);
    }
    }//GEN-LAST:event_dataTabelPinjamMouseClicked

    private void btnCariDataAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDataAnggotaActionPerformed
        Tampilan.PopupDataAnggota popup = new Tampilan.PopupDataAnggota();
        popup.agt = this; 
        popup.setVisible(true);   
    }//GEN-LAST:event_btnCariDataAnggotaActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    int barisTerpilih = dataTabelPinjam.getSelectedRow();
    
    // Validasi: Pastikan ada baris yang terpilih
    if (barisTerpilih == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Silakan klik salah satu baris buku di tabel terlebih dahulu!", 
                "Peringatan", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (txtIdBuku.getText().trim().isEmpty() || txtJumlahBuku.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Data buku dan Jumlah buku tidak boleh kosong!", 
                "Peringatan", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        int jumlahBaru = Integer.parseInt(txtJumlahBuku.getText().trim());
        
        if (jumlahBaru <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Jumlah pinjam harus lebih dari 0!", 
                    "Peringatan", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        dataTabelPinjam.setValueAt(txtIdBuku.getText(), barisTerpilih, 0);   
        dataTabelPinjam.setValueAt(txtJudul.getText(), barisTerpilih, 1);       
        dataTabelPinjam.setValueAt(txtPengarang.getText(), barisTerpilih, 2);   
        dataTabelPinjam.setValueAt(txtPenerbit.getText(), barisTerpilih, 3);  
        dataTabelPinjam.setValueAt(jumlahBaru, barisTerpilih, 4);
        
        hitungTotalPinjam();
        
        clearFormBuku();
        
        this.btnBatalUpdate.setVisible(false);
        this.btnUpdate.setVisible(false);
        
        javax.swing.JOptionPane.showMessageDialog(this, "Data buku di keranjang berhasil diperbarui!");
        
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Masukkan jumlah harus berupa angka yang valid!", 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }   // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnBatalUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalUpdateActionPerformed
     this.btnBatalUpdate.setVisible(false);
     this.btnUpdate.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btnBatalUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBatalUpdate;
    private javax.swing.JButton btnCariDataAnggota;
    private javax.swing.JButton btnCariDataBuku;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTable dataTabelPinjam;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGambar;
    private javax.swing.JLabel lblTotalPinjam;
    private javax.swing.JTextField tfDeskripsi;
    private javax.swing.JTextField tfNamaKategori;
    private javax.swing.JTextField txtIDPinjam;
    private javax.swing.JTextField txtIdBuku;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextField txtJumlahBuku;
    private javax.swing.JTextField txtNIS;
    private javax.swing.JTextField txtPenerbit;
    private javax.swing.JTextField txtPengarang;
    private javax.swing.JTextField txtTelpon;
    private com.toedter.calendar.JDateChooser txtTglKembali;
    private com.toedter.calendar.JDateChooser txtTglPinjam;
    // End of variables declaration//GEN-END:variables
}
