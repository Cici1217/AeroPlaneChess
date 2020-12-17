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

        int spacing = (int) (getWidth() * 0.175);


        String filepath = "";
        if (color.equals(Color.YELLOW.darker())){
            filepath = "resources/yellow.png";
        }else if (color.equals(Color.BLUE.darker())){
            filepath = "resources/blue.png";
        }else if (color.equals(Color.GREEN.darker())){
            filepath = "resources/green.png";
        }else if (color.equals(Color.RED.darker())){
            filepath = "resources/red.png";
        }else {
            g.setColor(color);
            g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
            return;
        }

        Image image = Toolkit.getDefaultToolkit().getImage(filepath);
        // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
        g.drawImage(image, spacing, spacing, getWidth()-2*spacing, getHeight() - 2 * spacing, this);
    }
}
