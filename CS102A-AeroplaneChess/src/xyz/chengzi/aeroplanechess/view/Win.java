package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Win extends JComponent implements ItemListener {


    public Win() {
        JFrame jf = new JFrame("win!");
        jf.setSize(200, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String str;

        JPanel panel = new JPanel();

        JLabel label = new JLabel("wins!");
        panel.add(label);

        jf.add(panel);
//        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
