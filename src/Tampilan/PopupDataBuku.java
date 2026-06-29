/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tampilan;
import Koneksi.koneksi;
import View.menuCRUDPeminjaman;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rafli
 */
public class PopupDataBuku extends javax.swing.JFrame {
    private Connection Conn = koneksi.getKoneksi();
    private DefaultTableModel tabmode;
    public menuCRUDPeminjaman crudPeminjaman = null;

    /**
     * Creates new form PopupAnggota
     */
    public PopupDataBuku() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Mengatur tinggi baris tabel agar muat menampilkan gambar cover mini
        tableDataBuku.setRowHeight(60); 
        datatable();
        
    }
    
    private ImageIcon dapatkanIconCover(String pathGambar) {
        if (pathGambar != null && !pathGambar.isEmpty()) {
            try {
                Path pathLengkap = Paths.get(System.getProperty("user.dir"), pathGambar);
                File fileImg = pathLengkap.toFile();
                if (fileImg.exists()) {
                    ImageIcon iconAsli = new ImageIcon(fileImg.getAbsolutePath());
                    // Resize ke ukuran mini di tabel: lebar 50, tinggi 60
                    Image imgScaled = iconAsli.getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH);
                    return new ImageIcon(imgScaled);
                }
            } catch (Exception e) {
                System.out.println("Gagal load icon tabel: " + e.getMessage());
            }
        }
        return null; // Jika kosong/tidak ditemukan
    }
    
    protected void datatable() {
        Object[] Baris = {"ID BUKU", "JUDUL", "PENGARANG", "PENERBIT", "COVER"};
        
        // Override DefaultTableModel agar kolom ke-5 (indeks 4) dikenali sebagai Image/Icon
        tabmode = new DefaultTableModel(null, Baris) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) {
                    return ImageIcon.class;
                }
                return Object.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDataBuku.setModel(tabmode);
        
        // Atur agar renderer JTable bisa menggambar object ImageIcon ke tengah cell
        tableDataBuku.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    JLabel label = new JLabel((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    if (isSelected) {
                        label.setBackground(table.getSelectionBackground());
                        label.setOpaque(true);
                    }
                    return label;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        
        String sql = "SELECT b.id_buku, b.judul_buku, b.pengarang, b.cover, p.nama_penerbit " +
                     "FROM buku b " +
                     "INNER JOIN penerbit p ON b.id_penerbit = p.id_penerbit"; 
        try {
            Statement stat = Conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String a = hasil.getString("id_buku"); 
                String b = hasil.getString("judul_buku");
                String c = hasil.getString("pengarang");
                String d = hasil.getString("nama_penerbit"); 
                String e = hasil.getString("cover"); 
                
                // Ambil object ImageIcon mini
                ImageIcon iconCover = dapatkanIconCover(e);
                
                // Simpan object data ke baris tabel (termasuk iconCover)
                Object[] data = {a, b, c, d, iconCover};
                tabmode.addRow(data);
                
                // Menyimpan string path aslinya tersembunyi di properti client JTable untuk diambil saat klik
                tableDataBuku.putClientProperty("path_" + (tabmode.getRowCount() - 1), e);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data awal buku: " + e.getMessage());
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
        tableDataBuku = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCariBuku = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableDataBuku.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDataBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDataBuku);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Data Buku");

        txtCariBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariBukuKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtCariBuku))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCariBuku, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariBukuKeyPressed
    Object[] Baris = {"ID BUKU", "JUDUL", "PENGARANG", "PENERBIT", "COVER"};
        
        tabmode = new DefaultTableModel(null, Baris) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) return ImageIcon.class;
                return Object.class;
            }
        };
        
        String sql = "SELECT b.id_buku, b.judul_buku, b.pengarang, b.cover, p.nama_penerbit " +
                     "FROM buku b " +
                     "INNER JOIN penerbit p ON b.id_penerbit = p.id_penerbit " +
                     "WHERE b.judul_buku LIKE '%" + txtCariBuku.getText() + "%' " +
                     "OR b.id_buku LIKE '%" + txtCariBuku.getText() + "%'";
        try {
            Statement stat = Conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("id_buku");
                String b = hasil.getString("judul_buku");
                String c = hasil.getString("pengarang");
                String d = hasil.getString("nama_penerbit");
                String e = hasil.getString("cover"); 

                ImageIcon iconCover = dapatkanIconCover(e);

                Object[] data = {a, b, c, d, iconCover};
                tabmode.addRow(data);
                tableDataBuku.putClientProperty("path_" + (tabmode.getRowCount() - 1), e);
            }
            tableDataBuku.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat pencarian buku: " + e.getMessage());
        }
    }//GEN-LAST:event_txtCariBukuKeyPressed

    private void tableDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataBukuMouseClicked
    int bar = tableDataBuku.getSelectedRow();
        if (crudPeminjaman != null && bar != -1) {
            crudPeminjaman.id_buku = tableDataBuku.getValueAt(bar, 0).toString();
            crudPeminjaman.judul_buku = tableDataBuku.getValueAt(bar, 1).toString();
            crudPeminjaman.pengarang_buku = tableDataBuku.getValueAt(bar, 2).toString();
            crudPeminjaman.penerbit_buku = tableDataBuku.getValueAt(bar, 3).toString();
            
            // Ambil string path asli dari client property tersembunyi
            Object pathObj = tableDataBuku.getClientProperty("path_" + bar);
            crudPeminjaman.nama_gambar = (pathObj != null) ? pathObj.toString() : "";
            
            crudPeminjaman.itemTerpilihBuku();
            this.dispose(); 
        }
    }//GEN-LAST:event_tableDataBukuMouseClicked

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
            java.util.logging.Logger.getLogger(PopupDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopupDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopupDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopupDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PopupDataBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableDataBuku;
    private javax.swing.JTextField txtCariBuku;
    // End of variables declaration//GEN-END:variables
}
