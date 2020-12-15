package xyz.chengzi.aeroplanechess.model;

public class ChessPiece {
    private final int player;
    private final int number;

    public ChessPiece(int player , int number) {
        this.player = player;
        this.number = number;
    }

    public int getPlayer() {
        return player;
    }

    public int getNumber() {
        return number;
    }

}
