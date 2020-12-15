package xyz.chengzi.aeroplanechess.listener;

import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import java.util.LinkedList;
import java.util.List;

public class ImplementFofMethods implements MethodsForPlaying {

    @Override
    public boolean CheckForGoOut(int numberOfDiceOne, int numberOfDiceTwo) {
        if (numberOfDiceOne == 6 || numberOfDiceTwo == 6) {
            return true;
        }
        return false;
    }


    @Override
    public int[] NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo) {
        int add , sub = 0, mul = 0, div = 0;
        add = numberOfDiceOne + numberOfDiceTwo;
        if(numberOfDiceOne != numberOfDiceTwo){
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
    public boolean EatOthersPiece(ChessPiece piece1 , ChessPiece piece2 ,ChessBoard board ,ChessBoardLocation location1,ChessBoardLocation location2) {
        if(CheckAnyPlayer(piece1,board,location2)){
            if(board.getGridAt(location2).getPiece() == piece2){
                if(piece1.getPlayer() != piece2.getPlayer()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean CheckAnyPlayer(ChessPiece piece ,ChessBoard board ,ChessBoardLocation location) {
            if(board.getGridAt(location).getPiece()==null){
                return false;
            }
            if(board.getGridAt(location).getPiece() == piece){
                return false;
            }
        return true;
    }


    @Override
    public boolean BonusLocation(ChessBoardLocation location , ChessPiece chessPiece){
        if (location.getColor() == chessPiece.getPlayer()){
            return true;
        }
        return false;
    }
    //有一点错误
    //尚未完成

    @Override
    public List<ChessLocation> EatOtherPiecesWhenFlying(ChessLocation locationOne, ChessLocation locationTwo,ChessLocation locationThree) {
        List<ChessLocation> locations=new LinkedList<ChessLocation>();
//        if (locationTwo.getIndex() == 4) {
//            ChessLocation newLocation = new ChessLocation(locationTwo.getColor(),locationTwo.getIndex()+3,locationOne.getPlayer(),locationOne.getNumber());
//            ChessLocation oldLocation = new ChessLocation(locationOne.getColor(),locationOne.getIndex(),4,locationThree.getNumber());
//            locations.add(newLocation);
//            locations.add(oldLocation);
//            if (CheckAnyPlayer(locationThree)){
//                ChessLocation crashLocation = new ChessLocation(locationThree.getColor(),locationThree.getIndex(),4,locationThree.getNumber());
//            }
//    }
        return locations;
}

    @Override
    public void TooLuckyTooUnlucky() {

    }

    @Override
    public void ChooseToStack(ChessLocation locationOne, ChessLocation locationTwo) {

    }

    @Override
    public void CompeteForEatingPiece(int DiceOne, int DiceTwo) {

    }
}
