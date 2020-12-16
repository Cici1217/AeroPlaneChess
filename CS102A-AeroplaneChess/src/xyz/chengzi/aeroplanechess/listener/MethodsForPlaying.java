package xyz.chengzi.aeroplanechess.listener;

import com.sun.media.jfxmedia.events.PlayerStateEvent;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.view.DiceSelectorComponent;

import java.util.List;


public interface MethodsForPlaying {
    boolean CheckForGoOut(int numberOfDiceOne, int numberOfDiceTwo);

    int[] NumberOfMove(int numberOfDiceOne, int numberOfDiceTwo);

    boolean EatOthersPiece(ChessPiece piece1 , ChessPiece piece2 ,ChessBoard board ,ChessBoardLocation location1 , ChessBoardLocation location2);

    boolean CheckAnyPlayer(ChessPiece piece ,ChessBoard board ,ChessBoardLocation location);

    boolean BonusLocation(ChessBoardLocation location , ChessPiece chessPiece);

    boolean anotherRoll(int numberOfDiceTwo,int numberOfDiceOne);

    //TODO:next:implement methods

//    ChessPiece EatOtherPiecesWhenFlying(ChessPiece chessPiece1 , ChessPiece chessPiece2 , ChessBoardLocation location);

    //There are additional shortcut squares. When a plane lands on one of these of its
    //own colour, it may take the shortcut, and any opposing planes in the path of the
    //shortcut are sent back to their hangars.
    boolean TooLuckyTooUnlucky(int player ,int NumberOfDiceOne , int NumberOfDiceTwo);

    //If the sum of the two dices is no less than 10, whether they are used to enter or
    //move a piece, gives that player another roll. A second sum no less than 10 gives
    //the player a third roll with enter or move. If the player rolls a third sum no less
    //than 10, any pieces moved by the first two steps must return to their hangar and
    //play passes to the next player.


    boolean ChooseToStack(ChessPiece chessPiece1 , ChessPiece chessPiece2 ,ChessBoard board , ChessBoardLocation location2);
    //When a plane lands on another plane in its own color, the player can choose to stack
    //the pieces and move them as one piece until they reach the centre or are landed on by
    //an opponent. When stacked pieces are sent back to their hangar by an opponent
    //landing on them, they are no longer stacked.

//    boolean WhetherToChooseStackTrue();

    void CompeteForEatingPiece(int DiceOne , int DiceTwo);
    //When a plane lands on an opposing plane, players determine which gets sent back to
    //its hangar by rolling one die, with the high roll determining the winner. When one
    //plane attacks a stack of planes, it must battle each one by rolling the die. When a stack
    //attacks another stack, the planes battle each other with a series of successive die rolls
    //until only one player occupies the square. This rule replaces the third basic rule above.


}
