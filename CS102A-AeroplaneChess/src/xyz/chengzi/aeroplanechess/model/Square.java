package xyz.chengzi.aeroplanechess.model;

public class Square {
    private final ChessBoardLocation location;
    public boolean win;
    private ChessPiece piece;

    public Square(ChessBoardLocation location) {
        this.location = location;
    }

    public ChessBoardLocation getLocation() {
        return location;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
}
