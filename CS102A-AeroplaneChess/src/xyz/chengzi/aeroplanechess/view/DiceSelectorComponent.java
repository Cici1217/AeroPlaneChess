package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DiceSelectorComponent extends JComponent implements ItemListener {
    private JRadioButton manualDiceRadio;
    private JRadioButton randomDiceRadio;
    private JRadioButton AddButton;
    private JRadioButton SubButton;
    private JRadioButton MulButton;
    private JRadioButton DevButton;
    private JComboBox<Integer> diceComboBox;
    private boolean randomDice = true;
    private int numberOfChoose = 0;

    public DiceSelectorComponent() {
        setLayout(null); // Use absolute layout
        setSize(270, 25);

        diceComboBox = new JComboBox<>();
        for (int i = 1; i <= 6; i++) {
            diceComboBox.addItem(i);
        }
        diceComboBox.setLocation(0, 0);
        diceComboBox.setSize(80, 25);
        diceComboBox.setVisible(false);
        add(diceComboBox);

        manualDiceRadio = new JRadioButton("manual");
        randomDiceRadio = new JRadioButton("auto", true);

        manualDiceRadio.setLocation(90, 0);
        manualDiceRadio.setSize(100, 25);
        manualDiceRadio.setFont(manualDiceRadio.getFont().deriveFont(18.0f));
        randomDiceRadio.setLocation(200, 0);
        randomDiceRadio.setSize(70, 25);
        randomDiceRadio.setFont(randomDiceRadio.getFont().deriveFont(18.0f));
        manualDiceRadio.addItemListener(this);
        randomDiceRadio.addItemListener(this);
        add(manualDiceRadio);
        add(randomDiceRadio);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(manualDiceRadio);
        btnGroup.add(randomDiceRadio);
    }

    public boolean isRandomDice() {
        return randomDice;
    }

    public Object getSelectedDice() {
        return diceComboBox.getSelectedItem();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (randomDiceRadio.isSelected()) {
            numberOfChoose+=1;
            if(numberOfChoose >=2){
                randomDice = true;
                diceComboBox.setVisible(false);
            }else{
                randomDice = true;
                diceComboBox.setVisible(true);
            }

        } else {
            randomDice = false;
            diceComboBox.setVisible(true);
        }
    }
}
