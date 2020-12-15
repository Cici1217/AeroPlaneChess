package xyz.chengzi.aeroplanechess.controller;

import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.listener.ImplementFofMethods;
import xyz.chengzi.aeroplanechess.listener.InputListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.ChessComponent;
import xyz.chengzi.aeroplanechess.view.NotationSelectorComponent;
import xyz.chengzi.aeroplanechess.view.SquareComponent;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.List;

public class GameController implements InputListener, Listenable<GameStateListener> {
    private final List<GameStateListener> listenerList = new ArrayList<>();
    private final ChessBoardComponent view;
    private final ChessBoard model;
    public int rollTime = 0;
    private int notation;
    private int numberOfDiceOne,numberOfDiceTwo;


    public void setNumberOfDiceOne(int numberOfDiceOne) {
        this.numberOfDiceOne = numberOfDiceOne;
    }

    public void setNumberOfDiceTwo(int numberOfDiceTwo) {
        this.numberOfDiceTwo = numberOfDiceTwo;
    }

    public void setNotation(int notation) {
        this.notation = notation;
    }

    public int getNotation() {
        return notation;
    }

    private Integer rolledNumber;
    ImplementFofMethods implementFofMethods = new ImplementFofMethods();

    private int currentPlayer;

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard) {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        view.registerListener(this);
        model.registerListener(view);
    }

    public void loadGame(ChessBoardLocation[][] locations, int turn){
        model.placeLoadedPieces(locations);
        rolledNumber = null;
        currentPlayer = turn;
        listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
    }

    public Integer getRolledNumber() {
        int num1 = rolledNumber >> 16;
        int num2 = rolledNumber & 0x00ff;

//        System.out.println(notation);
        return implementFofMethods.NumberOfMove(num1, num2)[notation];
    }

    public ChessBoardComponent getView() {
        return view;
    }

    public ChessBoard getModel() {
        return model;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void initializeGame() {
        model.placeInitialPieces();
        rolledNumber = null;
        currentPlayer = 0;
        listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
    }

    public int rollDice() {
        if (rolledNumber == null) {
            rolledNumber = RandomUtil.nextInt(1, 6);
            rolledNumber <<= 16;
            int number2 = RandomUtil.nextInt(1, 6);
            rolledNumber |= number2;
            return rolledNumber;
        } else {
            return -1;
        }
    }

    public void manualDice(int dice1, int dice2) {
        rolledNumber = dice1;
        rolledNumber <<= 16;
        rolledNumber |= dice2;
    }

    public int nextPlayer() {
        rolledNumber = null;
        return currentPlayer = (currentPlayer + 1) % 4;
    }


    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        System.out.println("clicked " + location.getColor() + "," + location.getIndex());
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        if (rolledNumber != null) {
            ChessPiece piece = model.getChessPieceAt(location);
            int x = getRolledNumber();
            if(19<=location.getIndex() && location.getIndex()<=22){
                System.out.println(numberOfDiceOne);
                System.out.println(numberOfDiceTwo);
                if(!implementFofMethods.CheckForGoOut(numberOfDiceOne, numberOfDiceTwo)){
                    x = 0;
                }
            }
            if (piece.getPlayer() == currentPlayer) {
                model.moveChessPiece(location, x, piece);
                listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                nextPlayer();
                listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
            }
        }
    }

    @Override
    public void registerListener(GameStateListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameStateListener listener) {
        listenerList.remove(listener);
    }
}
