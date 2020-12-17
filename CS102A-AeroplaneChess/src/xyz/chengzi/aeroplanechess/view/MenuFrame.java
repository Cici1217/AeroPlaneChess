package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.AeroplaneChess;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
public class MenuFrame extends JFrame {
    public MenuFrame(GameController controller) throws MalformedURLException {
        URL url;
        setTitle("2019 CS102A Project Demo");
        setSize(772, 825);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        //background
        JLabel BG=new JLabel("");
        BG.setLocation(0, 0);
        String path = System.getProperty("AeroPlaneChess.dir");
        url=new File("D:\\Synapse2002\\AeroPlaneChess\\飞行棋背景图.jpg").toURI().toURL();
        Icon icon=new ImageIcon(url);
        BG.setIcon(icon);
        BG.setOpaque(true);
        BG.setSize(772,825);
        BG.setVisible(true);
        //title
        JLabel titleLabel = new JLabel();
        JLabel titleLabel2 = new JLabel();
        titleLabel.setText("Aeroplane");
        titleLabel2.setText("Chess");
        titleLabel.setFont(new Font(null,Font.BOLD,80));
        titleLabel2.setFont(new Font(null,Font.BOLD,80));
        titleLabel.setLocation(180, 20);
        titleLabel2.setLocation(260, 100);
        titleLabel.setSize(496, 134);
        titleLabel2.setSize(496, 134);
        titleLabel.setOpaque(false);
        titleLabel2.setOpaque(false);
        titleLabel.setVisible(true);
        titleLabel2.setVisible(true);
        //button-newGame
        JButton btn = new JButton("NewGame");
        btn.setFont(new Font("微软雅黑",Font.PLAIN,20));
        btn.setBounds(280,280,200,40);
        btn.setBorderPainted(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AeroplaneChess a=new AeroplaneChess();
                a.main(null);
            }
        });
        add(btn);
        //button-loadGame
        JButton btn2 = new JButton("LoadGame");
        btn2.setFont(new Font("微软雅黑",Font.PLAIN,20));
        btn2.setBounds(280,360,200,40);
        btn2.setBorderPainted(false);
        btn2.addActionListener(e -> {
            String choose = chooseArchive();
            File file = new File(choose);
            ChessBoardLocation locations[][] = new ChessBoardLocation[4][4];
            String loadedList[] = new String[4];
            int turn = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = null;
                int index = 0;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    if (index<4)
                        loadedList[index++] = line;
                    else
                        turn = Integer.parseInt(line);
                }

                System.out.println("turn:"+turn);

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
            controller.loadGame(locations,turn);
        });
        add(btn2);
        //button- rule
        JButton btn3 = new JButton("Rule");
        btn3.setFont(new Font("微软雅黑",Font.PLAIN,20));
        btn3.setBounds(280,440,200,40);
        btn3.setBorderPainted(false);
        add(btn3);
        add(titleLabel);
        add(titleLabel2);
        add(BG);
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
}
