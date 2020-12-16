package xyz.chengzi.aeroplanechess.listener;

import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.view.ButtonStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.LinkedList;
import java.util.List;

public class ImplementFofMethods implements MethodsForPlaying {

    public int Stack ;
    private int NumberOfSecondRoll = 0;

    public int getNumberOfSecondRoll() {
        return NumberOfSecondRoll;
    }

    @Override
    public boolean CheckForGoOut(int numberOfDiceOne, int numberOfDiceTwo) {
        if (numberOfDiceOne == 6 || numberOfDiceTwo == 6) {
            return true;
        }
        return false;
    }


    @Override
    public int[] NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo) {
        int add, sub = 0, mul = 0, div = 0;
        add = numberOfDiceOne + numberOfDiceTwo;
        if (numberOfDiceOne != numberOfDiceTwo) {
            sub = Math.abs(numberOfDiceOne - numberOfDiceTwo);
        }
        if (numberOfDiceOne * numberOfDiceTwo <= 12)
            mul = numberOfDiceOne * numberOfDiceTwo;
        if (numberOfDiceOne >= numberOfDiceTwo && numberOfDiceOne % numberOfDiceTwo == 0)
            div = numberOfDiceOne / numberOfDiceTwo;
        if (numberOfDiceTwo >= numberOfDiceOne && numberOfDiceTwo % numberOfDiceOne == 0)
            div = numberOfDiceTwo / numberOfDiceOne;
        int[] num = {add, sub, mul, div};
        return num;
    }

    @Override
    public boolean EatOthersPiece(ChessPiece piece1, ChessPiece piece2, ChessBoard board, ChessBoardLocation location1, ChessBoardLocation location2) {
        if (CheckAnyPlayer(piece1, board, location2)) {
            if (board.getGridAt(location2).getPiece() == piece2) {
                if (piece1.getPlayer() != piece2.getPlayer()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean CheckAnyPlayer(ChessPiece piece, ChessBoard board, ChessBoardLocation location) {
        if (board.getGridAt(location).getPiece() == null) {
            return false;
        }
        if (board.getGridAt(location).getPiece() == piece) {
            return false;
        }
        return true;
    }


    @Override
    public boolean BonusLocation(ChessBoardLocation location, ChessPiece chessPiece) {
        if (location.getColor() == chessPiece.getPlayer()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean anotherRoll(int numberOfDiceTwo, int numberOfDiceOne) {
        if (numberOfDiceOne + numberOfDiceTwo >= 10) {
            NumberOfSecondRoll++;
            return true;
        }
        NumberOfSecondRoll = 0;
        return false;
    }

    @Override
    public boolean TooLuckyTooUnlucky(int player, int NumberOfDiceOne, int NumberOfDiceTwo) {
        if (NumberOfSecondRoll >= 3) {
            if (anotherRoll(NumberOfDiceTwo, NumberOfDiceOne)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int ChooseToStack(ChessPiece chessPiece1, ChessPiece chessPiece2, ChessBoard board, ChessBoardLocation location2) {
        if (CheckAnyPlayer(chessPiece1, board, location2)) {
            if (chessPiece1.getPlayer() == chessPiece2.getPlayer()) {
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

                return Stack;
            }
        }
        return -1;
    }

//    public boolean WhetherToChooseStackTrue() {
//        if(x == 0){
//            System.out.println("Whe : true");
//            return true;
//        }else{
//            System.out.println("Whe : false");
//            return false;
//        }
//
//    }

    @Override
    public void CompeteForEatingPiece(int DiceOne, int DiceTwo) {

    }
}
