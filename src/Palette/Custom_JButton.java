package palette;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Custom_JButton extends JButton implements Serializable {

    // Properti custom radius mandiri tiap sudut
    private int roundTopLeft = 15;
    private int roundTopRight = 15;
    private int roundBottomLeft = 15;
    private int roundBottomRight = 15;

    // Properti border
    private Color borderColor = new Color(52, 152, 219);
    private int borderThickness = 0; 

    private boolean mouseOver = false;
    private boolean mousePressed = false;

    public Custom_JButton() {
        // --- KUNCI UTAMA AGAR ROUNDED BERFUNGSI SEMPURNA ---
        setOpaque(false);
        setContentAreaFilled(false); // Wajib false agar background bawaan Java tidak menimpa rounded kita
        setFocusPainted(false);                  
        setBorderPainted(false);                 
        
        // Mengatur padding dalam agar teks tidak menabrak lengkungan sudut
        setBorder(new EmptyBorder(8, 15, 8, 15));
        
        setBackground(new Color(52, 152, 219)); 
        setForeground(Color.WHITE);              
        setCursor(new Cursor(Cursor.HAND_CURSOR)); 

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                mousePressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    mousePressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    mousePressed = false;
                    repaint();
                }
            }
        });
    }

    // --- GETTER & SETTER RADIUS INDEPENDEN ---
    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int roundTopLeft) { this.roundTopLeft = roundTopLeft; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int roundTopRight) { this.roundTopRight = roundTopRight; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int roundBottomLeft) { this.roundBottomLeft = roundBottomLeft; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int roundBottomRight) { this.roundBottomRight = roundBottomRight; repaint(); }

    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; repaint(); }

    public int getBorderThickness() { return borderThickness; }
    public void setBorderThickness(int borderThickness) { this.borderThickness = borderThickness; repaint(); }

    private Path2D.Float createCustomShape(int width, int height) {
        Path2D.Float path = new Path2D.Float();
        
        // Offset 1 pixel agar garis luar border pas di dalam bingkai komponen
        float w = width - 1.0f;
        float h = height - 1.0f;

        path.moveTo(roundTopLeft, 0);
        path.lineTo(w - roundTopRight, 0);
        path.quadTo(w, 0, w, roundTopRight);
        path.lineTo(w, h - roundBottomRight);
        path.quadTo(w, h, w - roundBottomRight, h);
        path.lineTo(roundBottomLeft, h);
        path.quadTo(0, h, 0, h - roundBottomLeft);
        path.lineTo(0, roundTopLeft);
        path.quadTo(0, 0, roundTopLeft, 0);
        path.closePath();
        
        return path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        Color baseColor = getBackground();
        if (mousePressed) {
            baseColor = baseColor.darker();
        } else if (mouseOver) {
            baseColor = brightColor(baseColor);
        }

        // Gambar bentuk rounded custom
        Path2D.Float shape = createCustomShape(width, height);
        g2.setColor(baseColor);
        g2.fill(shape);

        g2.dispose();
        
        // Render teks dan ikon bawaan setelah background rounded kita siap
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (borderThickness > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            Path2D.Float shape = createCustomShape(width, height);

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.draw(shape);
            
            g2.dispose();
        }
    }

    private Color brightColor(Color color) {
        int r = Math.min(255, (int) (color.getRed() * 1.1));
        int g = Math.min(255, (int) (color.getGreen() * 1.1));
        int b = Math.min(255, (int) (color.getBlue() * 1.1));
        return new Color(r, g, b, color.getAlpha());
    }
}