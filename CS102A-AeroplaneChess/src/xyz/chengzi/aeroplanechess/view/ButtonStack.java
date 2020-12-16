package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ButtonStack extends JComponent implements ItemListener {
    private JRadioButton Yes;
    private JRadioButton No;
    private JComboBox<Integer> diceComboBox;
private int Stack ;

    public int getStack() {
        return Stack;
    }

    public ButtonStack() {
        JFrame jf = new JFrame("是否叠机？");
        jf.setSize(200, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        // 创建两个单选按钮
        JRadioButton radioBtn01 = new JRadioButton("是");
        JRadioButton radioBtn02 = new JRadioButton("否");

        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);

        // 设置第一个单选按钮选中
        radioBtn01.setSelected(false);
        radioBtn02.setSelected(false);

        panel.add(radioBtn01);
        panel.add(radioBtn02);

        JButton button = new JButton("确定");
        panel.add(button);

        jf.add(panel);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String info ="";
                //通过面板属性名获取到该面板上的所有组件
                System.out.println(info);
                for(Component c:panel.getComponents()){
                    if(c instanceof JRadioButton){
                        if(((JRadioButton) c).isSelected()){
                            info += ((JRadioButton)c).getText();
                        }
                    }
                }
                System.out.println(info);
                if(info.equals("是")){
                    Stack = 0;
                }else{
                    Stack = 1;
                }
                JOptionPane.showMessageDialog(null, "你选择了"+info);
            }
        });

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        new ButtonStack();
    }
}
