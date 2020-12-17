package xyz.chengzi.aeroplanechess.model;

import java.util.ArrayList;

public class StackPiece extends ChessPiece {
  private ArrayList<Integer> stackPieceNums;
  private ArrayList<ChessPiece> guestPieces;

  public StackPiece(int player, int number) {
    super(player, number);
    stackPieceNums = new ArrayList<>();
    guestPieces = new ArrayList<>();
  }

  public void addPiece(ChessPiece piece) {
    stackPieceNums.add(piece.getNumber());
  }

  public void addPiece(ArrayList<Integer> pieceNums) {
    this.stackPieceNums.addAll(pieceNums);
  }

  public void guestPiece(ChessPiece piece) {
    guestPieces.add(piece);
  }

  public ArrayList<Integer> getStackPieceNums() {
    return stackPieceNums;
  }

  public ArrayList<ChessPiece> getGuestPieces() {
    return guestPieces;
  }

  public ChessPiece getOneGuest(){
    if (guestPieces.size()>0){
      ChessPiece guest = guestPieces.get(0);
      guestPieces.remove(0);
      return guest;
    }
    return null;
  }

  public String toString() {
    String numLine = "";
    for (Integer num : stackPieceNums) {
      numLine += num + " ";
    }
    String guestLine = "";
    for (ChessPiece piece : guestPieces) {
      guestLine += piece + " | ";
    }
    return "player:" + getPlayer() + " num:" + getNumber() + " StackNums:" + numLine +
        " GuestPiece:" + guestLine;
  }
}
