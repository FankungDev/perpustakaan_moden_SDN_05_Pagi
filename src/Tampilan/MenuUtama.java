/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tampilan;
import Menu.LaporanAnggota;
import Menu.LaporanBuku;
import Menu.LaporanPeminjaman;
import Menu.LaporanPetugas;
import Menu.menuDashboard;
import Menu.menuAnggota;
import Menu.menuKategori;
import Menu.menuPetugas;
import Menu.menuPenerbit;
import View.menuCRUDPengembalian;
import Menu.menuBuku;
import Menu.menuPeminjaman;
import Menu.menuPengembalian;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.Timer;

/**
 *
 * @author rafli
 */
public class MenuUtama extends javax.swing.JFrame {
    private Timer timer;

    /**
     * Creates new form MenuUtama
     */
    public MenuUtama(String namaAdmin) {
        initComponents();
        setDate();
        lbProfileName.setText(namaAdmin);
        setLocationRelativeTo(null);
        
        pKanan.setLayout(new java.awt.BorderLayout());
        pDasar.setLayout(new java.awt.BorderLayout());
        pUtama.setLayout(new java.awt.BorderLayout());
        
        pDasar.removeAll(); // Hapus dulu sisa-sisa layout absolute bawaan NetBeans
        pDasar.add(jScrollPane1, java.awt.BorderLayout.CENTER); 

        // Pastikan pUtama nempel di dalam JScrollPane
        jScrollPane1.setViewportView(pUtama);

        // Tampilkan dashboard pas pertama kali buka
        showPanel(new menuDashboard());
        
        pUtama.setLayout(new java.awt.BorderLayout());
        BpnlDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlPengembalian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlPenerbit.setCursor(new  java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlKategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlPeminjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlLprPinjam.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlLprAnggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlLprBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BpnlLprPetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        
        BpnlPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new menuPeminjaman());
            }
            });
        
        BpnlLprPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new LaporanPetugas());
            }
            });
        
        BpnlLprBuku.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new LaporanBuku());
            }
            });
        
        BpnlLprAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new LaporanAnggota());
            }
            });
        
        BpnlLprPinjam.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new LaporanPeminjaman());
            }
            });
        
        BpnlAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            BpnlAnggotaMouseClicked(evt);
                    }
                    });
        
        BpnlPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new menuPetugas());
            }
            });
        
        BpnlBuku.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPanel(new menuBuku());
            }
            });
        
        BpnlPenerbit.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPanel(new menuPenerbit());
            }
        });
        
        BpnlKategori.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPanel(new menuKategori());
            }
        });
        
        BpnlPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPanel(new menuPengembalian());
            }
});
    }
    
    public void showPanel(javax.swing.JPanel panel){
        pUtama.removeAll();
        pUtama.add(panel, java.awt.BorderLayout.CENTER);
        pUtama.repaint();
        pUtama.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void setDate(){
        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Calendar calender =  Calendar.getInstance();
                Date now = new Date();
                SimpleDateFormat formatHari = new SimpleDateFormat("EEEE", new Locale("in","ID"));
                SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String hari = formatHari.format(calender.getTime());
                String waktuTanggal = formatTanggal.format(now);
                lbDate.setText(hari+", "+waktuTanggal);
            }
        });
        timer.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pKiri = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        IconBuku = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BpnlDashboard = new javax.swing.JPanel();
        IconDashboard = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BpnlAnggota = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        BpnlBuku = new javax.swing.JPanel();
        IconDashboard2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BpnlKategori = new javax.swing.JPanel();
        IconDashboard3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        BpnlPenerbit = new javax.swing.JPanel();
        IconDashboard4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        BpnlPetugas = new javax.swing.JPanel();
        IconDashboard5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        BpnlPeminjaman = new javax.swing.JPanel();
        IconDashboard6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        BpnlPengembalian = new javax.swing.JPanel();
        IconDashboard7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        BpnlLprPinjam = new javax.swing.JPanel();
        IconDashboard8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        BpnlLprAnggota = new javax.swing.JPanel();
        IconDashboard9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        BpnlLprBuku = new javax.swing.JPanel();
        IconDashboard10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        BpnlLprPetugas = new javax.swing.JPanel();
        IconDashboard11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        pKanan = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbProfileName = new javax.swing.JLabel();
        lbDate = new javax.swing.JLabel();
        btnProfile = new javax.swing.JButton();
        pDasar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pUtama = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pKiri.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        IconBuku.setBackground(new java.awt.Color(255, 255, 255));
        IconBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/IconBuku.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(IconBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(IconBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Perpustakaan");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("SDN 05 Pagi Bidaracina");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("MASTER DATA");

        BpnlDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BpnlDashboardMouseClicked(evt);
            }
        });

        IconDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconDashboard.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Dashboard");

        javax.swing.GroupLayout BpnlDashboardLayout = new javax.swing.GroupLayout(BpnlDashboard);
        BpnlDashboard.setLayout(BpnlDashboardLayout);
        BpnlDashboardLayout.setHorizontalGroup(
            BpnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlDashboardLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard)
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlDashboardLayout.setVerticalGroup(
            BpnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlDashboardLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BpnlDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(17, 17, 17))
        );

        BpnlAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BpnlAnggotaMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Anggota");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/peopleIconKecil.png"))); // NOI18N

        javax.swing.GroupLayout BpnlAnggotaLayout = new javax.swing.GroupLayout(BpnlAnggota);
        BpnlAnggota.setLayout(BpnlAnggotaLayout);
        BpnlAnggotaLayout.setHorizontalGroup(
            BpnlAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlAnggotaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel17)
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlAnggotaLayout.setVerticalGroup(
            BpnlAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlAnggotaLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel17)
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BpnlAnggotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(17, 17, 17))
        );

        IconDashboard2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/BookIconMini.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Buku");

        javax.swing.GroupLayout BpnlBukuLayout = new javax.swing.GroupLayout(BpnlBuku);
        BpnlBuku.setLayout(BpnlBukuLayout);
        BpnlBukuLayout.setHorizontalGroup(
            BpnlBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlBukuLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(IconDashboard2)
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlBukuLayout.setVerticalGroup(
            BpnlBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlBukuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(BpnlBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(IconDashboard2))
                .addGap(15, 15, 15))
        );

        IconDashboard3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/KategoriMini.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Kategori");

        javax.swing.GroupLayout BpnlKategoriLayout = new javax.swing.GroupLayout(BpnlKategori);
        BpnlKategori.setLayout(BpnlKategoriLayout);
        BpnlKategoriLayout.setHorizontalGroup(
            BpnlKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlKategoriLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(IconDashboard3)
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlKategoriLayout.setVerticalGroup(
            BpnlKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlKategoriLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BpnlKategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IconDashboard3)
                .addGap(8, 8, 8))
        );

        IconDashboard4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPenaMini.png"))); // NOI18N
        IconDashboard4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconDashboard4MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Penerbit");

        javax.swing.GroupLayout BpnlPenerbitLayout = new javax.swing.GroupLayout(BpnlPenerbit);
        BpnlPenerbit.setLayout(BpnlPenerbitLayout);
        BpnlPenerbitLayout.setHorizontalGroup(
            BpnlPenerbitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPenerbitLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard4)
                .addGap(28, 28, 28)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlPenerbitLayout.setVerticalGroup(
            BpnlPenerbitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPenerbitLayout.createSequentialGroup()
                .addGroup(BpnlPenerbitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlPenerbitLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard4))
                    .addGroup(BpnlPenerbitLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addGap(10, 10, 10))
        );

        IconDashboard5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPetugasMini.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Petugas");

        javax.swing.GroupLayout BpnlPetugasLayout = new javax.swing.GroupLayout(BpnlPetugas);
        BpnlPetugas.setLayout(BpnlPetugasLayout);
        BpnlPetugasLayout.setHorizontalGroup(
            BpnlPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPetugasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard5)
                .addGap(28, 28, 28)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlPetugasLayout.setVerticalGroup(
            BpnlPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPetugasLayout.createSequentialGroup()
                .addGroup(BpnlPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlPetugasLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard5))
                    .addGroup(BpnlPetugasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)))
                .addGap(10, 10, 10))
        );

        IconDashboard6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Peminjaman");

        javax.swing.GroupLayout BpnlPeminjamanLayout = new javax.swing.GroupLayout(BpnlPeminjaman);
        BpnlPeminjaman.setLayout(BpnlPeminjamanLayout);
        BpnlPeminjamanLayout.setHorizontalGroup(
            BpnlPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPeminjamanLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard6)
                .addGap(28, 28, 28)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlPeminjamanLayout.setVerticalGroup(
            BpnlPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPeminjamanLayout.createSequentialGroup()
                .addGroup(BpnlPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlPeminjamanLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard6))
                    .addGroup(BpnlPeminjamanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)))
                .addGap(10, 10, 10))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("TRANSAKSI");

        IconDashboard7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPengembalianMini.png"))); // NOI18N
        IconDashboard7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconDashboard7MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Pengembalian");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BpnlPengembalianLayout = new javax.swing.GroupLayout(BpnlPengembalian);
        BpnlPengembalian.setLayout(BpnlPengembalianLayout);
        BpnlPengembalianLayout.setHorizontalGroup(
            BpnlPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPengembalianLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard7)
                .addGap(28, 28, 28)
                .addComponent(jLabel12)
                .addGap(60, 60, 60))
        );
        BpnlPengembalianLayout.setVerticalGroup(
            BpnlPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlPengembalianLayout.createSequentialGroup()
                .addGroup(BpnlPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlPengembalianLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard7))
                    .addGroup(BpnlPengembalianLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)))
                .addGap(10, 10, 10))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("LAPORAN");

        BpnlLprPinjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BpnlLprPinjamMouseClicked(evt);
            }
        });

        IconDashboard8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPeminjamanMini.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Peminjaman");

        javax.swing.GroupLayout BpnlLprPinjamLayout = new javax.swing.GroupLayout(BpnlLprPinjam);
        BpnlLprPinjam.setLayout(BpnlLprPinjamLayout);
        BpnlLprPinjamLayout.setHorizontalGroup(
            BpnlLprPinjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprPinjamLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard8)
                .addGap(28, 28, 28)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlLprPinjamLayout.setVerticalGroup(
            BpnlLprPinjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprPinjamLayout.createSequentialGroup()
                .addGroup(BpnlLprPinjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlLprPinjamLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard8))
                    .addGroup(BpnlLprPinjamLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)))
                .addGap(10, 10, 10))
        );

        BpnlLprAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BpnlLprAnggotaMouseClicked(evt);
            }
        });

        IconDashboard9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/peopleIconKecil.png"))); // NOI18N
        IconDashboard9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconDashboard9MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Anggota");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BpnlLprAnggotaLayout = new javax.swing.GroupLayout(BpnlLprAnggota);
        BpnlLprAnggota.setLayout(BpnlLprAnggotaLayout);
        BpnlLprAnggotaLayout.setHorizontalGroup(
            BpnlLprAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprAnggotaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard9)
                .addGap(28, 28, 28)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlLprAnggotaLayout.setVerticalGroup(
            BpnlLprAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprAnggotaLayout.createSequentialGroup()
                .addGroup(BpnlLprAnggotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlLprAnggotaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard9))
                    .addGroup(BpnlLprAnggotaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)))
                .addGap(10, 10, 10))
        );

        BpnlLprBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BpnlLprBukuMouseClicked(evt);
            }
        });

        IconDashboard10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/BookIconMini.png"))); // NOI18N
        IconDashboard10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconDashboard10MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Buku");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BpnlLprBukuLayout = new javax.swing.GroupLayout(BpnlLprBuku);
        BpnlLprBuku.setLayout(BpnlLprBukuLayout);
        BpnlLprBukuLayout.setHorizontalGroup(
            BpnlLprBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprBukuLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard10)
                .addGap(28, 28, 28)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlLprBukuLayout.setVerticalGroup(
            BpnlLprBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprBukuLayout.createSequentialGroup()
                .addGroup(BpnlLprBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlLprBukuLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard10))
                    .addGroup(BpnlLprBukuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)))
                .addGap(10, 10, 10))
        );

        BpnlLprPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BpnlLprPetugasMouseClicked(evt);
            }
        });

        IconDashboard11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/LogoPetugasMini.png"))); // NOI18N
        IconDashboard11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconDashboard11MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("Petugas");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BpnlLprPetugasLayout = new javax.swing.GroupLayout(BpnlLprPetugas);
        BpnlLprPetugas.setLayout(BpnlLprPetugasLayout);
        BpnlLprPetugasLayout.setHorizontalGroup(
            BpnlLprPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprPetugasLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(IconDashboard11)
                .addGap(28, 28, 28)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BpnlLprPetugasLayout.setVerticalGroup(
            BpnlLprPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpnlLprPetugasLayout.createSequentialGroup()
                .addGroup(BpnlLprPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BpnlLprPetugasLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(IconDashboard11))
                    .addGroup(BpnlLprPetugasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pKiriLayout = new javax.swing.GroupLayout(pKiri);
        pKiri.setLayout(pKiriLayout);
        pKiriLayout.setHorizontalGroup(
            pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pKiriLayout.createSequentialGroup()
                .addGroup(pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pKiriLayout.createSequentialGroup()
                        .addGroup(pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pKiriLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)))
                            .addGroup(pKiriLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pKiriLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BpnlDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlAnggota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlBuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlPenerbit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlPetugas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlPeminjaman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlPengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlLprPinjam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pKiriLayout.createSequentialGroup()
                                .addGroup(pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(BpnlLprAnggota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlLprBuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BpnlLprPetugas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pKiriLayout.setVerticalGroup(
            pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pKiriLayout.createSequentialGroup()
                .addGroup(pKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pKiriLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pKiriLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel2)))
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlLprPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlLprAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BpnlLprBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BpnlLprPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        getContentPane().add(pKiri, java.awt.BorderLayout.LINE_START);

        pKanan.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        lbProfileName.setBackground(new java.awt.Color(255, 255, 255));
        lbProfileName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbProfileName.setForeground(new java.awt.Color(255, 255, 255));
        lbProfileName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbProfileName.setText("Profile Name");

        lbDate.setBackground(new java.awt.Color(255, 255, 255));
        lbDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbDate.setForeground(new java.awt.Color(255, 255, 255));
        lbDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDate.setText("Date");

        btnProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/profile.png"))); // NOI18N
        btnProfile.setToolTipText("");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(1192, Short.MAX_VALUE)
                .addComponent(lbDate)
                .addGap(88, 88, 88)
                .addComponent(lbProfileName)
                .addGap(40, 40, 40)
                .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbProfileName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pKanan.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pDasar.setBackground(new java.awt.Color(204, 204, 204));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pUtama.setBackground(new java.awt.Color(255, 255, 255));
        pUtama.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(pUtama);

        javax.swing.GroupLayout pDasarLayout = new javax.swing.GroupLayout(pDasar);
        pDasar.setLayout(pDasarLayout);
        pDasarLayout.setHorizontalGroup(
            pDasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDasarLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pDasarLayout.setVerticalGroup(
            pDasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );

        pKanan.add(pDasar, java.awt.BorderLayout.CENTER);

        getContentPane().add(pKanan, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BpnlDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BpnlDashboardMouseClicked
       menuDashboard dashboard = new menuDashboard();
       showPanel(dashboard);
    }//GEN-LAST:event_BpnlDashboardMouseClicked

    private void BpnlAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BpnlAnggotaMouseClicked
        menuAnggota anggota = new menuAnggota();
        showPanel(anggota);
    }//GEN-LAST:event_BpnlAnggotaMouseClicked

    private void IconDashboard9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconDashboard9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_IconDashboard9MouseClicked

    private void BpnlLprPinjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BpnlLprPinjamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BpnlLprPinjamMouseClicked

    private void BpnlLprAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BpnlLprAnggotaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BpnlLprAnggotaMouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseClicked

    private void IconDashboard10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconDashboard10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_IconDashboard10MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel16MouseClicked

    private void BpnlLprBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BpnlLprBukuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BpnlLprBukuMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
   // Panggil method showPanel yang sudah kamu buat, lalu masukkan class menuPengembalian
    showPanel(new Menu.menuPengembalian());
    }//GEN-LAST:event_jLabel12MouseClicked

    private void IconDashboard4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconDashboard4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_IconDashboard4MouseClicked

    private void IconDashboard7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconDashboard7MouseClicked
      // Panggil method showPanel yang sudah kamu buat, lalu masukkan class menuPengembalian
    showPanel(new Menu.menuPengembalian());
    }//GEN-LAST:event_IconDashboard7MouseClicked

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        MenuProfile menu = new MenuProfile(this, true, this);
        Point p = btnProfile.getLocationOnScreen();
        menu.setLocation(p.x - 340, p.y + 40);
        menu.setVisible(true);
    }//GEN-LAST:event_btnProfileActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        pUtama.removeAll();
        pUtama.add(new menuDashboard());
        pUtama.repaint();
        pUtama.revalidate();
    }//GEN-LAST:event_formWindowOpened

    private void IconDashboard11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconDashboard11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_IconDashboard11MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel18MouseClicked

    private void BpnlLprPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BpnlLprPetugasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BpnlLprPetugasMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new menuDashboard().setVisible(true);
        }
    });

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
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String namaAdmin = "nama";
                new MenuUtama(namaAdmin).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BpnlAnggota;
    private javax.swing.JPanel BpnlBuku;
    private javax.swing.JPanel BpnlDashboard;
    private javax.swing.JPanel BpnlKategori;
    private javax.swing.JPanel BpnlLprAnggota;
    private javax.swing.JPanel BpnlLprBuku;
    private javax.swing.JPanel BpnlLprPetugas;
    private javax.swing.JPanel BpnlLprPinjam;
    private javax.swing.JPanel BpnlPeminjaman;
    private javax.swing.JPanel BpnlPenerbit;
    private javax.swing.JPanel BpnlPengembalian;
    private javax.swing.JPanel BpnlPetugas;
    private javax.swing.JLabel IconBuku;
    private javax.swing.JLabel IconDashboard;
    private javax.swing.JLabel IconDashboard10;
    private javax.swing.JLabel IconDashboard11;
    private javax.swing.JLabel IconDashboard2;
    private javax.swing.JLabel IconDashboard3;
    private javax.swing.JLabel IconDashboard4;
    private javax.swing.JLabel IconDashboard5;
    private javax.swing.JLabel IconDashboard6;
    private javax.swing.JLabel IconDashboard7;
    private javax.swing.JLabel IconDashboard8;
    private javax.swing.JLabel IconDashboard9;
    private javax.swing.JButton btnProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDate;
    private javax.swing.JLabel lbProfileName;
    private javax.swing.JPanel pDasar;
    private javax.swing.JPanel pKanan;
    private javax.swing.JPanel pKiri;
    private javax.swing.JPanel pUtama;
    // End of variables declaration//GEN-END:variables
}
