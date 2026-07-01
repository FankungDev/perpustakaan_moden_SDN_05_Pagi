/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Koneksi.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author imam
 */
public class LaporanPeminjaman extends javax.swing.JPanel {

    /**
     * Creates new form menuAnggota
     */
    public LaporanPeminjaman() {
        initComponents();
        setTabelModel();
        loadData();
        
        btnBatal.setEnabled(false);

        // search
        tfCari.addCaretListener(new javax.swing.event.CaretListener() {
            @Override
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                loadData();
            }
        });
    }
    
    private void setTabelModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("No");
        model.addColumn("ID");
        model.addColumn("ID Anggota");
        model.addColumn("Nama Anggota");
        model.addColumn("ID Buku");
        model.addColumn("Pinjam");
        model.addColumn("Kembali");
        model.addColumn("Dikembalikan");
        model.addColumn("Status");
        model.addColumn("Denda");
        jTable1.setModel(model);
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = Koneksi.koneksi.getKoneksi();
            if (conn == null) {
                System.out.println("Gagal terhubung ke database. Cek konfigurasi koneksi Anda.");
                return;
            }
            
            StringBuilder sql = new StringBuilder(
                "SELECT p.Id_Pinjam, p.Nis, a.Nama, dp.Id_Buku, p.Tanggal_Pinjam, p.Tanggal_Kembali, " +
                "pg.tgl_pengembalian, dp.status_pinjam, pg.point " +
                "FROM peminjaman p " +
                "INNER JOIN data_anggota a ON p.Nis = a.Nis " +
                "INNER JOIN detail_pinjam dp ON p.Id_Pinjam = dp.Id_Pinjam " +
                "LEFT JOIN pengembalian pg ON p.Id_Pinjam = pg.id_peminjaman"
            );

            List<String> conditions = new ArrayList<>();
            java.util.Date startDate = dcMulai.getDate();
            java.util.Date endDate = dcAkhir.getDate();
            String keyword = tfCari.getText().trim();

            if (startDate != null && endDate != null) {
                conditions.add("p.Tanggal_Pinjam BETWEEN ? AND ?");
            } else if (startDate != null) {
                conditions.add("p.Tanggal_Pinjam >= ?");
            } else if (endDate != null) {
                conditions.add("p.Tanggal_Pinjam <= ?");
            }

            if (!keyword.isEmpty()) {
                conditions.add("(p.Id_Pinjam LIKE ? OR p.Nis LIKE ? OR a.Nama LIKE ? OR dp.Id_Buku LIKE ? OR dp.status_pinjam LIKE ?)");
            }

            if (!conditions.isEmpty()) {
                sql.append(" WHERE ").append(String.join(" AND ", conditions));
            }

            sql.append(" ORDER BY p.Tanggal_Pinjam DESC, p.Id_Pinjam DESC");

            st = conn.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (startDate != null && endDate != null) {
                st.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                st.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            } else if (startDate != null) {
                st.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            } else if (endDate != null) {
                st.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            if (!keyword.isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                st.setString(paramIndex++, likeKeyword);
                st.setString(paramIndex++, likeKeyword);
                st.setString(paramIndex++, likeKeyword);
                st.setString(paramIndex++, likeKeyword);
                st.setString(paramIndex++, likeKeyword);
            }

            rs = st.executeQuery();
            int no = 1;
            while (rs.next()) {
                String tglKembaliStr = rs.getString("tgl_pengembalian");
                if (tglKembaliStr == null) {
                    tglKembaliStr = "-";
                }
                String pointVal = rs.getString("point");
                if (pointVal == null) {
                    pointVal = "0";
                }

                model.addRow(new Object[]{
                    no++,
                    rs.getString("Id_Pinjam"),
                    rs.getString("Nis"),
                    rs.getString("Nama"),
                    rs.getString("Id_Buku"),
                    rs.getString("Tanggal_Pinjam"),
                    rs.getString("Tanggal_Kembali"),
                    tglKembaliStr,
                    rs.getString("status_pinjam"),
                    pointVal
                });
            }
        } catch (Exception e) {
            System.out.println("Error pada loadData: " + e.toString());
            e.printStackTrace(); 
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tfCari = new palette.Custom_JTextField();
        btnBatal = new javax.swing.JButton();
        btnTampilkan = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        dcMulai = new com.toedter.calendar.JDateChooser();
        dcAkhir = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setRowHeight(50);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/peopleIconKecil.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Laporan Peminjaman Buku");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Laporan > Peminjaman");

        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnTampilkan.setText("TAMPILKAN");
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        btnPrint.setText("PRINT");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        dcMulai.setDateFormatString("yyyy-MM-dd");

        dcAkhir.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(dcMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dcAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        btnBatal.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dcMulai.setDate(null);
        dcAkhir.setDate(null);
        tfCari.setText("");
        loadData();
        btnBatal.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        loadData();
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
try {

        Connection conn = Koneksi.koneksi.getKoneksi();

        String report = getClass().getResource("/Reports/LaporanPeminjaman.jasper").getPath();

        Map<String, Object> parameter = new HashMap<>();

        if (dcMulai.getDate() != null) {
            parameter.put("tanggalMulai",
                    new java.sql.Date(dcMulai.getDate().getTime()));
        }

        if (dcAkhir.getDate() != null) {
            parameter.put("tanggalAkhir",
                    new java.sql.Date(dcAkhir.getDate().getTime()));
        }

        JasperPrint jp = JasperFillManager.fillReport(
                report,
                parameter,
                conn);

        JasperViewer.viewReport(jp, false);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
                "Gagal mencetak laporan\n" + e.getMessage());
        e.printStackTrace();
    }    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnTampilkan;
    private com.toedter.calendar.JDateChooser dcAkhir;
    private com.toedter.calendar.JDateChooser dcMulai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private palette.Custom_JTextField tfCari;
    // End of variables declaration//GEN-END:variables
}
