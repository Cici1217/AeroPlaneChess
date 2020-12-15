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
    public List<ChessLocation> EatOthersPiece(ChessLocation locationOne, ChessLocation locationTwo) {
        List<ChessLocation> locations=new LinkedList<ChessLocation>();

//        if (locationOne.getPlayer() != locationTwo.getPlayer()) {
//            if (CheckAnyPlayer(locationTwo)){
//                ChessLocation newLocation = new ChessLocation(locationTwo.getColor(),locationTwo.getIndex(),locationOne.getPlayer(),locationOne.getNumber());
//                ChessLocation oldLocation = new ChessLocation(locationOne.getColor(),locationOne.getIndex(),4,4);
//                locations.add(newLocation);
//                locations.add(oldLocation);
//            }else {
//                ChessLocation newLocation = new ChessLocation(locationTwo.getColor(),locationTwo.getIndex(),locationOne.getPlayer(),locationOne.getNumber());
//                ChessLocation oldLocation = new ChessLocation(locationOne.getColor(),locationOne.getIndex(),4,locationOne.getNumber());
//                for (int i=19;i<23;i++) {
//                    ChessLocation loseLocation = new ChessLocation(locationTwo.getColor(),i,locationTwo.getPlayer(),locationTwo.getNumber());//?
//                    if (CheckAnyPlayer(loseLocation)){
//                     loseLocation = new ChessLocation(locationTwo.getColor(),i, locationTwo.getPlayer(), locationTwo.getNumber());
//                        locations.add(loseLocation);
//                    break;
//                    }
//                }
//                locations.add(newLocation);
//                locations.add(oldLocation);
//            }
//        }
        return locations;
    }

    @Override
    public boolean CheckAnyPlayer(ChessPiece piece, ChessBoard board ,ChessBoardLocation location) {
        for(int i =0;i<board.getChessPieces().size();i++){
            if(board.getChessPieces().get(i)== board.getChessPieceAt(location)){
                return true;
            }
        }
        return false;
    }


    //检查这个位置是否有飞机，没有的话返回true，有的话返回false
    @Override
    public ChessLocation BonusLocation(ChessLocation locationOne, ChessLocation locationTwo){
        if (locationOne.getColor()==locationTwo.getColor())
        return new ChessLocation(locationTwo.getColor(),locationTwo.getIndex()+1,locationOne.getPlayer(),locationOne.getNumber());
        else return null;
    }

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
