package xyz.chengzi.aeroplanechess.model;

public class ChessPiece {
  private final int player;
  private final int number;

  public ChessPiece(int player, int number) {
    this.player = player;
    this.number = number;
  }

  public int getPlayer() {
    return player;
  }

  public int getNumber() {
    return number;
  }

  public String toString() {
    return "player:" + player + " number:" + number;
  }

  public boolean equals(ChessPiece chessPiece) {
    return player == chessPiece.getPlayer() && number == chessPiece.getNumber();
  }
}
