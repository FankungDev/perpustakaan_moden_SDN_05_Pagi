package palette;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.io.Serializable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Custom_JTextField extends JTextField implements Serializable {

    // Properti custom radius mandiri tiap sudut
    private int roundTopLeft = 15;
    private int roundTopRight = 15;
    private int roundBottomLeft = 15;
    private int roundBottomRight = 15;

    // Properti warna dan border
    private Color borderColor = new Color(200, 214, 229);
    private Color borderFocusColor = new Color(52, 152, 219); 
    private String placeholder = "Ketik di sini...";
    private Color placeholderColor = new Color(150, 150, 150);

    private boolean isFocused = false;

    public Custom_JTextField() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(new Color(44, 62, 80));
        setCaretColor(new Color(44, 62, 80)); 
        
        // Jarak teks di dalam (padding) agar tidak menempel ke border melengkung
        setBorder(new EmptyBorder(8, 12, 8, 12));

        // Listener efek warna border saat fokus/diklik
        addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                isFocused = true;
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                isFocused = false;
                repaint();
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

    // --- GETTER & SETTER PROPERTI LAINNYA ---
    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; repaint(); }

    public Color getBorderFocusColor() { return borderFocusColor; }
    public void setBorderFocusColor(Color borderFocusColor) { this.borderFocusColor = borderFocusColor; repaint(); }

    public String getPlaceholder() { return placeholder; }
    public void setPlaceholder(String placeholder) { this.placeholder = placeholder; repaint(); }

    public Color getPlaceholderColor() { return placeholderColor; }
    public void setPlaceholderColor(Color placeholderColor) { this.placeholderColor = placeholderColor; repaint(); }

    // Method pembantu untuk membuat bentuk custom path sesuai radius sudut
    private Path2D.Float createCustomShape(int width, int height) {
        Path2D.Float path = new Path2D.Float();
        
        // Kurangi 1 pixel pada width dan height agar outline border tidak terpotong tepi komponen
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

        // 1. Gambar Background Custom Berdasarkan Path
        Path2D.Float shape = createCustomShape(width, height);
        g2.setColor(getBackground());
        g2.fill(shape);

        // Render teks asli bawaan JTextField
        super.paintComponent(g); 

        // 2. Gambar Placeholder jika text field kosong
        if (getText().isEmpty() && placeholder != null) {
            g2.setColor(placeholderColor);
            g2.setFont(getFont().deriveFont(Font.ITALIC)); 
            
            int fontHeight = g2.getFontMetrics().getAscent();
            Insets insets = getInsets();
            int x = insets.left;
            // Hitung vertikal center
            int y = (height - fontHeight) / 2 + fontHeight;
            
            g2.drawString(placeholder, x, y);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        Path2D.Float shape = createCustomShape(width, height);

        // 3. Gambar Outline Border Berdasarkan Status Fokus
        if (isFocused) {
            g2.setColor(borderFocusColor);
            g2.setStroke(new BasicStroke(1.5f)); 
        } else {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.0f));
        }
        
        g2.draw(shape);
        g2.dispose();
    }
}