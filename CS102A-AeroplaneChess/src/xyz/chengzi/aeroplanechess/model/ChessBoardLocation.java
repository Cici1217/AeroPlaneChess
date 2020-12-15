package xyz.chengzi.aeroplanechess.model;

import java.util.Objects;

public class ChessBoardLocation {
    private final int color;
    private final int index;

    public ChessBoardLocation(int color, int index) {
        this.color = color;
        this.index = index;
    }

    public int getColor() {
        return color;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoardLocation location = (ChessBoardLocation) o;
        return color == location.color && index == location.index;
    }

    @Override
    public String toString() {
        return "(" + color + "," + index + ")";
    }


    @Override
    public int hashCode() {
        return Objects.hash(color, index);
    }
}
