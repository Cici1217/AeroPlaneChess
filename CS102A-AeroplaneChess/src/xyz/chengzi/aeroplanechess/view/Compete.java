package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.util.RandomUtil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Compete extends JComponent implements ItemListener {
    public boolean b ;
    public Compete() {

        JFrame jf = new JFrame("Compete");
        jf.setSize(200, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String str;

        JPanel panel = new JPanel();
        int x1 = RandomUtil.nextInt(1,6);
        int x2 = RandomUtil.nextInt(1,6);
        String s1 = Integer.toString(x1);
        String s2 = Integer.toString(x2);
        String str1 = "Attacker piece rolls : " + s1;
        String str2 = "Defender piece rolls : " + s2;
        JLabel label = new JLabel(str1);
        JLabel label2 = new JLabel(str2);

        panel.add(label);
        panel.add(label2);
        if(x1>=x2){
            JLabel label3 = new JLabel("Attacker wins the Compete");
            panel.add(label3);
            b = true;
        }else{
            JLabel label3 = new JLabel("Defender wins the Compete");
            panel.add(label3);
            b = false;
        }
        jf.add(panel);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
