package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent {
    private Color color;

    public ChessComponent(Color color) {
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintChess(g);
    }

    private void paintChess(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int spacing = (int) (getWidth() * 0.25);
        g.setColor(color);
        g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
    }
}
