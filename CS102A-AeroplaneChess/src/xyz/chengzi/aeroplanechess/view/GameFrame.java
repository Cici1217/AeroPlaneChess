package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.chengzi.aeroplanechess.model.StackPiece;

public class GameFrame extends JFrame implements GameStateListener {
    private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};
    private final JLabel statusLabel = new JLabel();
    public int num1;
    public int num2;


    public GameFrame(GameController controller) {

        JButton save = new JButton("Save");
        save.setLocation(600, 150);
        save.setFont(save.getFont().deriveFont(18.0f));
        save.setSize(90, 30);
        save.addActionListener((e -> {
            ChessBoard board = controller.getModel();
            ChessBoardLocation lists[][] = new ChessBoardLocation[4][4];

            int turn = controller.getCurrentPlayer();
            for (int player = 0; player < 4; player++) {
                for (int index = 0; index < 24; index++) {
                    ChessPiece piece;
                    if ((piece = board.getChessPieceAt(new ChessBoardLocation(player, index))) != null) {
                        if (piece instanceof StackPiece) {
                            StackPiece stackPiece = (StackPiece) piece;
                            for (Integer num : stackPiece.getStackPieceNums()) {
                                lists[piece.getPlayer()][num] = new ChessBoardLocation(-1, piece.getNumber());
                            }
                            for (ChessPiece guest : stackPiece.getGuestPieces()) {
                                if (guest instanceof StackPiece) {
                                    for (Integer num : ((StackPiece) guest).getStackPieceNums()) {
                                        lists[piece.getPlayer()][num] = new ChessBoardLocation(-1, guest.getNumber());
                                    }
                                }
                                lists[piece.getPlayer()][guest.getNumber()] =
                                        new ChessBoardLocation(-2, piece.getNumber());
                            }
                        }
                        int color = piece.getPlayer();
                        int number = piece.getNumber();
                        lists[color][number] = new ChessBoardLocation(player, index);
                    }
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
            String filename = df.format(new Date());// new Date()为获取当前系统时间
            File dir = new File("Archive");
            if (!dir.exists()) {
                dir.mkdir();
            }
            filename = "Archive/" + filename + ".txt";
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                String str = "";
                for (int i = 0; i < lists.length; i++) {
                    String line = "";
                    for (int j = 0; j < lists[i].length; j++) {
                        line += lists[i][j].toString() + " ";
                    }
                    str += line + "\n";
                }
                str += turn;
                bw.write(str);
                bw.close();
            } catch (IOException err) {
                err.printStackTrace();
            }
        }));
        add(save);

        JButton load = new JButton("load");
        load.setLocation(600, 300);
        load.setFont(load.getFont().deriveFont(18.0f));
        load.setSize(90, 30);

        JButton restart = new JButton("Restart");
        restart.setLocation(600, 400);
        restart.setFont(restart.getFont().deriveFont(18.0f));
        restart.setSize(120, 30);
        add(restart);
        restart.addActionListener(e ->
        {
            controller.initializeGame();
        });

        load.addActionListener(e -> {
            String choose = chooseArchive();
            File file = new File(choose);
            ChessBoardLocation locations[][] = new ChessBoardLocation[4][4];
            String loadedList[] = new String[4];
            int turn = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                int index = 0;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    if (index < 4) {
                        loadedList[index++] = line;
                    } else {
                        turn = Integer.parseInt(line);
                    }
                }
                br.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            for (int player = 0; player < 4; player++) {
                String[] splitedStr = loadedList[player].split(" ");
                for (int num = 0; num < 4; num++) {
                    String leftNum = splitedStr[num].split(",")[0].substring(1);
                    String rightNum = splitedStr[num].split(",")[1]
                            .substring(0, splitedStr[num].split(",")[1].length() - 1);
                    locations[player][num] =
                            new ChessBoardLocation(Integer.parseInt(leftNum), Integer.parseInt(rightNum));
                }
            }
            controller.loadGame(locations, turn);
        });
        add(load);

        controller.registerListener(this);
        setTitle("2020 CS102A Project Demo");
        setSize(772, 825);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        statusLabel.setLocation(0, 585);
        statusLabel.setFont(statusLabel.getFont().deriveFont(18.0f));
        statusLabel.setSize(400, 20);
        add(statusLabel);

        DiceSelectorComponent diceSelectorComponent = new DiceSelectorComponent();
        DiceSelectorComponent diceSelectorComponent1 = new DiceSelectorComponent();
        NotationSelectorComponent notationSelectorComponent = new NotationSelectorComponent();
        diceSelectorComponent.setLocation(396, 585);
        diceSelectorComponent1.setLocation(396, 615);
        notationSelectorComponent.setLocation(396 - 220, 645);
        add(diceSelectorComponent);
        add(diceSelectorComponent1);
        add(notationSelectorComponent);

        JButton button = new JButton("roll");

        button.addActionListener((e) -> {
            if (diceSelectorComponent.isRandomDice()) {
                int dice1 = controller.rollDice();
                num1 = dice1 >> 16;
                num2 = dice1 & 0x00ff;
                controller.setNumberOfDiceOne(num1);
                controller.setNumberOfDiceTwo(num2);
                if (dice1 != -1) {
                    statusLabel.setText(String.format("[%s] Rolled a (%d)(%d) ",
                            PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2));

                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
//                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
                num1 = (Integer) diceSelectorComponent1.getSelectedDice();
                num2 = (Integer) diceSelectorComponent.getSelectedDice();
                statusLabel.setText(String.format("[%s] Selected a (%d)(%d) ",
                        PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2));
                controller.setNumberOfDiceOne(num1);
                controller.setNumberOfDiceTwo(num2);
                controller.manualDice(num1, num2);

            }
        });

        JButton button1 = new JButton("choose");
        button1.addActionListener((e) -> {
            if (notationSelectorComponent.WhichNotationToChoose() == 0) {
                controller.setNotation(0);
                statusLabel.setText(String.format("[%s] Choose + , sum is %d",
                        PLAYER_NAMES[controller.getCurrentPlayer()], controller.getRolledNumber()));
            } else if (notationSelectorComponent.WhichNotationToChoose() == 1) {
                controller.setNotation(1);
                if (controller.getRolledNumber() == 0) {
                    JOptionPane.showMessageDialog(this, "You can't choose this!");
                } else {
                    statusLabel.setText(String.format("[%s] Choose - , sum is %d",
                            PLAYER_NAMES[controller.getCurrentPlayer()], controller.getRolledNumber()));
                }
            } else if (notationSelectorComponent.WhichNotationToChoose() == 2) {
                controller.setNotation(2);
                if (controller.getRolledNumber() == 0) {
                    JOptionPane.showMessageDialog(this, "You can't choose this!");
                } else {
                    statusLabel.setText(String.format("[%s] Choose x , sum is %d",
                            PLAYER_NAMES[controller.getCurrentPlayer()], controller.getRolledNumber()));
                }
            } else {
                controller.setNotation(3);
                if (controller.getRolledNumber() == 0) {
                    JOptionPane.showMessageDialog(this, "You can't choose this!");
                } else {
                    statusLabel.setText(String.format("[%s] Choose / , sum is %d",
                            PLAYER_NAMES[controller.getCurrentPlayer()], controller.getRolledNumber()));
                }

            }
        });

        button1.setLocation(668, 620);
        button1.setFont(button1.getFont().deriveFont(18.0f));
        button1.setSize(90, 30);
        add(button1);

        button.setLocation(668, 585);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        add(button);


    }


    private String chooseArchive() {
        JFileChooser fileChooser = new JFileChooser("Archive\\");

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int returnVal = fileChooser.showOpenDialog(fileChooser);
        String filePath = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的
        }
        return filePath;
    }


    @Override
    public void onPlayerStartRound(int player) {
        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
