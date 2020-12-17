package xyz.chengzi.aeroplanechess;

import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.view.*;
import xyz.chengzi.aeroplanechess.view.MenuFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;

import java.net.MalformedURLException;

public class Test {
    public static void main(String[] args) throws MalformedURLException {
        boolean a =true;
        boolean b =false;
        System.out.println(a&b);
        System.out.println(a|b);
        Music music = new Music();
        music.playMusic();
        music.loopMusic();
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(550, 13, 6);
        ChessBoard chessBoard = new ChessBoard(13, 6);
        GameController controller = new GameController(chessBoardComponent, chessBoard);
        GameFrame mainFrame = new GameFrame(controller);
        MenuFrame menu = new MenuFrame( controller);
        menu.setVisible(true);
    }
}

