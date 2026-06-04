package palette; // Sesuaikan dengan nama package proyekmu

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.io.Serializable;
import javax.swing.JPanel;

public class Custom_JPanelRounded extends JPanel implements Serializable {

    // Properti radius tetap mandiri
    private int roundTopLeft = 30;
    private int roundTopRight = 30;
    private int roundBottomLeft = 30;
    private int roundBottomRight = 30;
    
    // Properti border
    private Color borderColor = new Color(220, 221, 225);     
    private int borderThickness = 0;                         

    public Custom_JPanelRounded() {
        setOpaque(false); // Wajib false agar area luar lengkungan transparan
        // Menggunakan method bawaan JPanel agar disinkronisasikan dengan properti NetBeans
        setBackground(new Color(245, 246, 250)); 
    }

    // --- GETTER & SETTER RADIUS ---
    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int roundTopLeft) { this.roundTopLeft = roundTopLeft; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int roundTopRight) { this.roundTopRight = roundTopRight; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int roundBottomLeft) { this.roundBottomLeft = roundBottomLeft; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int roundBottomRight) { this.roundBottomRight = roundBottomRight; repaint(); }

    // --- SINKRONISASI WARNA DENGAN METHOD BAWAAN JPANEL ---
    // Kita override method set/get agar NetBeans Properties langsung mengubah warna internal JPanel
    public Color getBackgroundColor() { 
        return getBackground(); 
    }
    public void setBackgroundColor(Color backgroundColor) { 
        setBackground(backgroundColor); 
        repaint(); 
    }

    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; repaint(); }

    public int getBorderThickness() { return borderThickness; }
    public void setBorderThickness(int borderThickness) { this.borderThickness = borderThickness; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Anti-aliasing agar hasil lengkungan halus
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();

        // Membuat path khusus untuk radius mandiri tiap sudut
        Path2D.Float path = new Path2D.Float();
        path.moveTo(roundTopLeft, 0);
        path.lineTo(width - roundTopRight, 0);
        path.quadTo(width, 0, width, roundTopRight);
        path.lineTo(width, height - roundBottomRight);
        path.quadTo(width, height, width - roundBottomRight, height);
        path.lineTo(roundBottomLeft, height);
        path.quadTo(0, height, 0, height - roundBottomLeft);
        path.lineTo(0, roundTopLeft);
        path.quadTo(0, 0, roundTopLeft, 0);
        path.closePath();

        // 1. Ambil warna langsung dari getBackground() yang sudah sinkron dengan NetBeans
        g2.setColor(getBackground());
        g2.fill(path);

        // 2. Gambar Border jika borderThickness lebih dari 0
        if (borderThickness > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.draw(path);
        }

        g2.dispose();
    }
}