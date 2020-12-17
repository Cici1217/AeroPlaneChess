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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameController implements InputListener, Listenable<GameStateListener> {
    private final List<GameStateListener> listenerList = new ArrayList<>();
    private final ChessBoardComponent view;
    private final ChessBoard model;
    public int rollTime = 0;
    private int notation;
    private int numberOfDiceOne,numberOfDiceTwo;

    public int getNumberOfDiceTwo() {
        return numberOfDiceTwo;
    }

    public int getNumberOfDiceOne() {
        return numberOfDiceOne;
    }

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


    public void CompeteForChess(){
        if(model.isWhetherToEat()){
            int dice1 = rollDice();
            int num1 = dice1 >> 16;
            int num2 = dice1 & 0x00ff;
            setNumberOfDiceOne(num1);
            setNumberOfDiceTwo(num2);
        }
    }

    public int rollDice() {
        if (rolledNumber == null || implementFofMethods.getNumberOfSecondRoll() != 0 ) {
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
//        if (model.isWhetherToEat()) {
//            model.temp
//        }
        rolledNumber = null;
        return currentPlayer = (currentPlayer + 1) % 4;
    }




    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        System.out.println("clicked " + location.getColor() + "," + location.getIndex());
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        System.out.println("clicked piece "+model.getChessPieceAt(location));
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
            if (piece.getPlayer() == currentPlayer ) {
                model.moveChessPiece(location,x,piece);
//                System.out.println("Controller  :"+model.isFastWay());
                if(model.isFastWay()){
                    ChessBoardLocation chessBoardLocation = model.getLocationLast();
                    if(location.getIndex() == 4 || chessBoardLocation.getIndex() == 4){
                        model.moveChessPiece(chessBoardLocation,12,piece);
                        ChessBoardLocation locationOfBeatenChess = new ChessBoardLocation(Math.abs(piece.getPlayer()-2),15);
                        if(model.getGridAt(locationOfBeatenChess).getPiece() != null){// 如果在shortcut落点发现棋子，并且该棋子不是叠子
                            int player = model.getGridAt(locationOfBeatenChess).getPiece().getPlayer();
                            int number = model.getGridAt(locationOfBeatenChess).getPiece().getNumber();
                            ChessBoardLocation location1 = new ChessBoardLocation(player,number+ getModel().getEndDimension()+ model.getDimension());
                            model.setChessPieceAt(location1,model.getGridAt(locationOfBeatenChess).getPiece());
                            model.removeChessPieceAt(locationOfBeatenChess);
                        }

                    }else{
                        model.moveChessPiece(chessBoardLocation, 4, piece);
                    }
                }

                listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
                if(!implementFofMethods.anotherRoll(numberOfDiceOne, numberOfDiceTwo)){
                    nextPlayer();
                }
                if(implementFofMethods.TooLuckyTooUnlucky(currentPlayer,numberOfDiceOne,numberOfDiceTwo)){
                    for(int i =0;i<4;i++){
                        model.initializeOnePlayer(currentPlayer);
                    }
                    nextPlayer();
                }
                listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
                //-----------------------

                for (int player = 0; player < 4; player++) {
                    for (int index = model.getDimension() + model.getEndDimension();
                         index < model.getDimension() + model.getEndDimension() + 4; index++){
                        if (model.getGridAt(new ChessBoardLocation(player,index)).win){
                            switch (model.getChessPieceAt(new ChessBoardLocation(player,index)).getPlayer()){
                                case 0:
                                    view.setChessAtGrid(new ChessBoardLocation(player,index), Color.yellow);
                                    break;
                                case 1:
                                    view.setChessAtGrid(new ChessBoardLocation(player,index), Color.blue);
                                    break;
                                case 2:
                                    view.setChessAtGrid(new ChessBoardLocation(player,index), Color.green);
                                    break;
                                case 3:
                                    view.setChessAtGrid(new ChessBoardLocation(player,index), Color.red);
                                    break;
                            }
                        }
                    }
                }

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
