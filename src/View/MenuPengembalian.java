/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Koneksi.koneksi;
import Tampilan.MenuUtama;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author User
 */
public class MenuPengembalian extends javax.swing.JFrame {
    private void loadData() {
    try {
        Connection con = koneksi.getKoneksi();
        String sql = "SELECT * FROM peminjaman";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabelPencarian.getModel();
        model.setRowCount(0); // Kosongkan dulu

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("Id_Pinjam"),
                rs.getString("Nis"),
                rs.getString("Id_Buku"),
                rs.getString("Tanggal_Pinjam"),
                rs.getString("Tanggal_Kembali"),
                rs.getInt("Point"),
                rs.getString("Status"),
                rs.getInt("Jumlah_Pinjaman")
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
    }
}
    /**
     * Creates new form MenuPengembalian
     */
    public MenuPengembalian() {
        initComponents();
        loadData();
       tabelPencarian.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        label.setBackground(new java.awt.Color(40, 167, 69)); // Hijau
        label.setForeground(java.awt.Color.WHITE);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        label.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(30, 130, 50)));
        label.setOpaque(true);
        return label;
    }
});

tabelPencarian.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            setBackground(new java.awt.Color(173, 216, 230));
        } else if (row % 2 == 0) {
            setBackground(new java.awt.Color(227, 242, 253)); // Biru muda
        } else {
            setBackground(java.awt.Color.WHITE);
        }
        return this;
    }
});

jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

tabelPencarian.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
tabelPencarian.getColumnModel().getColumn(0).setPreferredWidth(90);   // Id_Pinjam
tabelPencarian.getColumnModel().getColumn(1).setPreferredWidth(90);   // Nis
tabelPencarian.getColumnModel().getColumn(2).setPreferredWidth(80);   // Id_Buku
tabelPencarian.getColumnModel().getColumn(3).setPreferredWidth(120);  // Tanggal_Pinjam
tabelPencarian.getColumnModel().getColumn(4).setPreferredWidth(120);  // Tanggal_Kembali
tabelPencarian.getColumnModel().getColumn(5).setPreferredWidth(60);   // Point
tabelPencarian.getColumnModel().getColumn(6).setPreferredWidth(150);  // Status
tabelPencarian.getColumnModel().getColumn(7).setPreferredWidth(120);  // Jumlah_Pinjaman
btnKembalikan.setBackground(new java.awt.Color(40, 167, 69));
 

btnKembalikan.setBackground(new java.awt.Color(40, 167, 69));
btnKembalikan.setForeground(java.awt.Color.WHITE);
btnKembalikan.setOpaque(true);
btnKembalikan.setBorderPainted(false);

btnCari.setBackground(new java.awt.Color(40, 167, 69));
btnCari.setForeground(java.awt.Color.WHITE);
btnCari.setOpaque(true);
btnCari.setBorderPainted(false);

btnRefresh.setBackground(new java.awt.Color(0, 123, 255));
btnRefresh.setForeground(java.awt.Color.WHITE);
btnRefresh.setOpaque(true);
btnRefresh.setBorderPainted(false);

btnBack.setOpaque(true);
        tabelPencarian.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int baris = tabelPencarian.getSelectedRow();
        if(baris >= 0) {
            txtIdPeminjam.setText(tabelPencarian.getValueAt(baris, 0).toString());
            txtNis.setText(tabelPencarian.getValueAt(baris, 1).toString());
            texIdBuku.setText(tabelPencarian.getValueAt(baris, 2).toString());
            txtTanggalPinjam.setText(tabelPencarian.getValueAt(baris, 3).toString());
            txtKembali.setText(tabelPencarian.getValueAt(baris, 4).toString());
            txtStatus.setText(tabelPencarian.getValueAt(baris, 6).toString());
            txtJumlahPinjam.setText(tabelPencarian.getValueAt(baris, 7).toString());
        }
    }
});
    this.setLayout(new java.awt.BorderLayout());
    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

javax.swing.JPanel pHeader = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
pHeader.add(jLabel2);
pHeader.add(txtDataPengembalianBuku);
pHeader.add(jLabel9);

javax.swing.JPanel pFooter = new javax.swing.JPanel();
pFooter.add(btnKembalikan);
pFooter.add(btnRefresh);
pFooter.add(btnBack);

javax.swing.JPanel pCenter = new javax.swing.JPanel(new java.awt.BorderLayout());
pCenter.add(jPanel1, java.awt.BorderLayout.WEST);
pCenter.add(jPanel2, java.awt.BorderLayout.CENTER);

this.add(pHeader, java.awt.BorderLayout.NORTH);
this.add(pCenter, java.awt.BorderLayout.CENTER);
this.add(pFooter, java.awt.BorderLayout.SOUTH);
    }
void tampilData() {
    try {
        java.sql.Connection con = koneksi.getKoneksi();
        if(con == null) {
            JOptionPane.showMessageDialog(null, "Koneksi Databases Gagal!");
            return;
        }
        String sql = "select * from peminjaman";
        java.sql.Statement st = con.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        DefaultTableModel dtm = (DefaultTableModel) tabelPencarian.getModel();
        dtm.setRowCount(0);
        
        while(rs.next()) {
            dtm.addRow(new Object[]{
                rs.getString("Id_Pinjam"),
                rs.getString("Nis"),
                rs.getString("Id_Buku"),
                rs.getString("Tanggal_Pinjam"),
                rs.getString("Tanggal_Kembali"),
                rs.getString("Point"),
                rs.getString("Status"),
                rs.getString("Jumlah_pinjam"),
            });
        }
    } catch(Exception e) {
        System.out.println(e);
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error:" + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIdPeminjam = new javax.swing.JTextField();
        txtNis = new javax.swing.JTextField();
        texIdBuku = new javax.swing.JTextField();
        txtTanggalPinjam = new javax.swing.JTextField();
        txtKembali = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtJumlahPinjam = new javax.swing.JTextField();
        txtDataPengembalianBuku = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        btnKembalikan = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtPencarian = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPencarian = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Id Peminjam");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Nis");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Id Buku");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Tanggal Pinjam");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Tanggal Kembali");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Status");

        txtIdPeminjam.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        txtNis.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        texIdBuku.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        txtTanggalPinjam.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        txtKembali.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        txtStatus.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Jumlah Pinjaman");

        txtJumlahPinjam.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(42, 42, 42)
                                            .addComponent(txtIdPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(texIdBuku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtTanggalPinjam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtNis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtJumlahPinjam, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                            .addComponent(txtStatus)
                                            .addComponent(txtKembali)))))
                            .addComponent(jLabel1))
                        .addGap(0, 132, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(texIdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggalPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtJumlahPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtDataPengembalianBuku.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtDataPengembalianBuku.setText("Data Pengembalian Buku");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/peopleIconKecil.png"))); // NOI18N

        btnRefresh.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnKembalikan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnKembalikan.setText("Kembalikan");
        btnKembalikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembalikanActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pencarian"));
        jPanel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(471, 394));

        txtPencarian.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        btnCari.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCari.setText("Cari");
        btnCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCariMouseClicked(evt);
            }
        });
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tabelPencarian.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tabelPencarian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id_Pinjam", "Nis", "Id_Buku", "Tanggal_Pinjam", "Tanggal_Kembali", "Point", "Status", "Jumlah_Pinjam"
            }
        ));
        tabelPencarian.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tabelPencarian);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCari)
                .addContainerGap(413, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setText("Transaksi > Pengembalian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDataPengembalianBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(233, 233, 233)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnKembalikan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDataPengembalianBuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKembalikan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        tampilData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new MenuUtama().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnKembalikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembalikanActionPerformed
        // TODO add your handling code here:
    int baris = tabelPencarian.getSelectedRow();
    if(baris < 0) {
        JOptionPane.showMessageDialog(null, "Pilih data dulu!");
        return;
    }
    String id = tabelPencarian.getValueAt(baris, 0).toString();
    try {
        java.sql.Connection con = koneksi.getKoneksi();
        String sql = "update peminjaman set status='Sudah Dikembalikan', Tanggal_Kembali=CURDATE() where Id_Pinjam='"+id+"'";
        java.sql.Statement st = con.createStatement();
        st.executeUpdate(sql);
        JOptionPane.showMessageDialog(null, "Berhasil dikembalikan!");
        tampilData();
    } catch(Exception e) {
        System.out.println(e);
    }
    }//GEN-LAST:event_btnKembalikanActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
    try {
        java.sql.Connection con = koneksi.getKoneksi();
        String cari = txtIdPeminjam.getText();
        String sql = "select * from peminjaman where Id_Pinjam like '%"+cari+"%'";
        java.sql.Statement st = con.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        DefaultTableModel dtm = (DefaultTableModel) tabelPencarian.getModel();
        dtm.setRowCount(0);
        
        while(rs.next()) {
            dtm.addRow(new Object[]{
                rs.getString("Id_Pinjam"),
rs.getString("Nis"),
rs.getString("Id_Buku"),
rs.getString("Tanggal_Pinjam"),
rs.getString("Tanggal_Kembali"),
rs.getString("Point"),
rs.getString("status"),      // ← huruf kecil
rs.getString("Jumlah_Pinjam")  // ← tanpa koma di akhir
            });
        }
    } catch(Exception e) {
        System.out.println(e);
    }

    }//GEN-LAST:event_btnCariActionPerformed

    private void btnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariMouseClicked
        // TODO add your handling code here:
    try {
        java.sql.Connection con = koneksi.getKoneksi();
        String cari = txtPencarian.getText();
        String sql = "select * from peminjaman where Id_Pinjam like '%"+cari+"%'";
        java.sql.Statement st = con.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        DefaultTableModel dtm = (DefaultTableModel) tabelPencarian.getModel();
        dtm.setRowCount(0);
        
        while(rs.next()) {
            dtm.addRow(new Object[]{
                rs.getString("Id_Pinjam"),
                rs.getString("Nis"),
                rs.getString("Id_Buku"),
                rs.getString("Tanggal_Pinjam"),
                rs.getString("Tanggal_Kembali"),
                rs.getString("Point"),
                rs.getString("status"),
                rs.getString("Jumlah_Pinjam")
            });
        }
    } catch(Exception e) {
        System.out.println(e);
    }
    }//GEN-LAST:event_btnCariMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnKembalikan;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTable tabelPencarian;
    private javax.swing.JTextField texIdBuku;
    private javax.swing.JLabel txtDataPengembalianBuku;
    private javax.swing.JTextField txtIdPeminjam;
    private javax.swing.JTextField txtJumlahPinjam;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtNis;
    private javax.swing.JTextField txtPencarian;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTanggalPinjam;
    // End of variables declaration//GEN-END:variables
}
