package xyz.chengzi.aeroplanechess.model;

import com.sun.corba.se.spi.orbutil.fsm.FSM;
import jdk.nashorn.internal.ir.OptimisticLexicalContext;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.ImplementFofMethods;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.Compete;
import xyz.chengzi.aeroplanechess.view.GameFrame;
import xyz.chengzi.aeroplanechess.view.Win;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Listenable<ChessBoardListener> {
    private final List<ChessBoardListener> listenerList = new ArrayList<>();
    private final Square[][] grid;
    private final int dimension, endDimension;
    public int[][] NumberTotal = new int[4][4];
    //    private List<ChessPiece> chessPieces = new ArrayList<>();
    private boolean IsThereAPlane;
    private boolean WhetherToEat;
    public int x = 0;

    public boolean isWhetherToEat() {
        return WhetherToEat;
    }

    public int WinOf0 = 0;
    public int WinOf1 = 0;
    public int WinOf2 = 0;
    public int WinOf3 = 0;
    private ChessBoardLocation LocationLast;
    private boolean FastWay;

//    public List<ChessPiece> getChessPieces() {
//        return chessPieces;
//    }

    ImplementFofMethods implementFofMethods = new ImplementFofMethods();

    public void WriteArr() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                NumberTotal[i][j] = 0;
                //i:color ; j:number of chess
            }
        }
    }

    public ChessBoard(int dimension, int endDimension) {
        this.grid = new Square[4][dimension + endDimension + 5];
        this.dimension = dimension;
        this.endDimension = endDimension;
        initGrid();
    }

    public void placeLoadedPieces(ChessBoardLocation[][] locations) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension + 5; j++) {
                grid[i][j].setPiece(null);
            }
        }
        for (int player = 0; player < 4; player++) {
            for (int num = 0; num < 4; num++) {
                if (locations[player][num].getColor() >= 0) {
                    grid[locations[player][num].getColor()][locations[player][num].getIndex()]
                            .setPiece(new StackPiece(player, num));
                }
            }
        }
        StackPiece guests[][] = new StackPiece[4][4];
        for (int player = 0; player < 4; player++) {
            for (int num = 0; num < 4; num++) {
                if (locations[player][num].getColor() == -2) {
                    guests[player][num] = new StackPiece(player, num);
                }
            }
        }

        for (int player = 0; player < 4; player++) {
            for (int num = 0; num < 4; num++) {
                if (locations[player][num].getColor() == -1) {
                    int target = locations[player][num].getIndex();
                    if (guests[player][target] != null) {
                        guests[player][target].addPiece(new ChessPiece(player, num));
                    } else {
                        ((StackPiece) grid[locations[player][target].getColor()][locations[player][target]
                                .getIndex()].getPiece()).addPiece(new ChessPiece(player, num));
                    }
                }
            }
        }
        for (int player = 0; player < 4; player++) {
            for (int num = 0; num < 4; num++) {
                if (locations[player][num].getColor() == -2) {
                    int target = locations[player][num].getIndex();
                    ((StackPiece) grid[locations[player][target].getColor()][locations[player][target]
                            .getIndex()].getPiece()).guestPiece(guests[player][num]);
                }
            }
        }
        listenerList.forEach(listener -> listener.onChessBoardReload(this));

    }

    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension + 5; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitialPieces() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension; j++) {
                grid[i][j].setPiece(null);
            }
            grid[i][23].setPiece(null);
        }
        // FIXME: Demo implementation
        grid[0][dimension + endDimension].setPiece(new ChessPiece(0, 0));
        grid[0][dimension + endDimension + 1].setPiece(new ChessPiece(0, 1));
        grid[0][dimension + endDimension + 2].setPiece(new ChessPiece(0, 2));
        grid[0][dimension + endDimension + 3].setPiece(new ChessPiece(0, 3));
        grid[1][dimension + endDimension].setPiece(new ChessPiece(1, 0));
        grid[1][dimension + endDimension + 1].setPiece(new ChessPiece(1, 1));
        grid[1][dimension + endDimension + 2].setPiece(new ChessPiece(1, 2));
        grid[1][dimension + endDimension + 3].setPiece(new ChessPiece(1, 3));
        grid[2][dimension + endDimension].setPiece(new ChessPiece(2, 0));
        grid[2][dimension + endDimension + 1].setPiece(new ChessPiece(2, 1));
        grid[2][dimension + endDimension + 2].setPiece(new ChessPiece(2, 2));
        grid[2][dimension + endDimension + 3].setPiece(new ChessPiece(2, 3));
        grid[3][dimension + endDimension].setPiece(new ChessPiece(3, 0));
        grid[3][dimension + endDimension + 1].setPiece(new ChessPiece(3, 1));
        grid[3][dimension + endDimension + 2].setPiece(new ChessPiece(3, 2));
        grid[3][dimension + endDimension + 3].setPiece(new ChessPiece(3, 3));
//        int n;
//        for (int i = 0; i <= 3; i++) {
//            n = 0;
//            for (int j = dimension + endDimension; j <= dimension + endDimension + 3; j++) {
//                ChessPiece chessPiece = new ChessPiece(grid[i][j].getLocation().getColor(), n);
//                n = n + 1;
//                chessPieces.add(chessPiece);
//            }
//        }
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public void initializeOnePlayer(int player) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= 23; j++) {
                ChessBoardLocation chessBoardLocation = new ChessBoardLocation(i, j);
                if (this.getGridAt(chessBoardLocation).getPiece() != null) {
                    if (this.getGridAt(chessBoardLocation).getPiece().getPlayer() == player) {
                        removeChessPieceAt(chessBoardLocation);
                    }
                }
            }
        }
        grid[player][dimension + endDimension].setPiece(new ChessPiece(player, 0));
        grid[player][dimension + endDimension + 1].setPiece(new ChessPiece(player, 1));
        grid[player][dimension + endDimension + 2].setPiece(new ChessPiece(player, 2));
        grid[player][dimension + endDimension + 3].setPiece(new ChessPiece(player, 3));
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getColor()][location.getIndex()];
    }

    public int getDimension() {
        return dimension;
    }

    public int getAllDimension() {
        return dimension + endDimension + 5;
    }

    public int getEndDimension() {
        return endDimension;
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void addEatenPieces(ArrayList<ChessPiece> eatenPieces, StackPiece piece) {
        for (Integer num : piece.getStackPieceNums()) {
            ChessPiece tempPiece = new ChessPiece(piece.getPlayer(), num);
            eatenPieces.add(tempPiece);
        }
    }

    public void moveChessPiece(ChessBoardLocation src, int steps, ChessPiece piece) {
        ChessBoardLocation dest = src;
        ChessBoardLocation destold = dest;

        // FIXME: This just naively move the chess forward without checking anything
        if (steps == 0) {
            return;
        }
        if (dest.getIndex() <= 18) {
            for (int i = 0; i < steps; i++) {
                dest = nextLocation(dest, piece);
            }
        } else if (dest.getIndex() >= 19 && dest.getIndex() <= 22) {
            dest = new ChessBoardLocation(dest.getColor(), 23);
        } else {
            int newcolor;
            if (dest.getColor() == 0) {
                newcolor = 3;
            } else {
                newcolor = dest.getColor() - 1;
            }
            dest = new ChessBoardLocation(newcolor, 3);

            if (dest.getIndex() < 12) {
                for (int i = 0; i < steps - 1; i++) {
                    dest = nextLocation(dest, piece);
                }
            } else {

                if (NumberTotal[piece.getPlayer()][piece.getNumber()] != 0) {
                    NumberTotal[piece.getPlayer()][piece.getNumber()] = 2;
                }
                dest = new ChessBoardLocation(dest.getColor(), dest.getIndex() + steps - 1);
            }
        }

        IsThereAPlane = implementFofMethods.CheckAnyPlayer(piece, this, dest);

    /*
    对dest处的棋子进行判断，如果是叠子（或者暂时重叠)，就把所有需要回到老家的棋子找出来。
     */
        ChessPiece EatenPiece = this.getGridAt(dest).getPiece();
        ArrayList<ChessPiece> EatenPieces = new ArrayList<>();
        if (EatenPiece instanceof StackPiece) {
            addEatenPieces(EatenPieces, (StackPiece) EatenPiece);
            for (ChessPiece guest : ((StackPiece) EatenPiece).getGuestPieces()) {
                if (guest instanceof StackPiece) {
                    addEatenPieces(EatenPieces, (StackPiece) guest);
                } else {
                    EatenPieces.add(guest);
                }
            }
            EatenPieces.add(new ChessPiece(EatenPiece.getPlayer(), EatenPiece.getNumber()));
        } else {
            EatenPieces.add(EatenPiece);
        }
        Compete compete = null;
        WhetherToEat = implementFofMethods.EatOthersPiece(piece, EatenPiece, this, destold, dest);
        if (WhetherToEat) {
            for (ChessPiece piece1 : EatenPieces) {
                int number;
                int color;
                compete = new Compete();
//                System.out.println(compete.b);
                if (compete.b) {
                    //attacker win
                    number = piece1.getNumber();
                    color = piece1.getPlayer();
                    if (EatenPiece instanceof StackPiece) {
                        ArrayList<Integer> nums = ((StackPiece) EatenPiece).getStackPieceNums();
                        for (int idx = 0; idx < nums.size(); idx++) {
                            if (nums.get(idx) == number) {
                                nums.remove(idx);
                                break;
                            }
                        }
//                        if (number == EatenPiece.getNumber()) {
//                            int leftPieceNum = ((StackPiece) EatenPiece).getStackPieceNums().get(0);
//                            ArrayList<Integer> leftStacks = ((StackPiece) EatenPiece).getStackPieceNums();
//                            leftStacks.remove(0);
//                            StackPiece newStack = new StackPiece(EatenPiece.getPlayer(), leftPieceNum);
//                            newStack.addPiece(leftStacks);
//                            EatenPiece = newStack;
//                        }
                    }

                    System.out.println("first status");
                    System.out.println(number);
                    System.out.println(color);
                    setChessPieceAt(grid[color][number + dimension + endDimension].getLocation(), piece1);

                } else {
                    //definer win
                    number = piece.getNumber();
                    color = piece.getPlayer();
                    System.out.println("second status");
                    System.out.println(number);
                    System.out.println(color);
                    setChessPieceAt(grid[color][number + dimension + endDimension].getLocation(), piece);
                }

//            ChessPiece piece1 = new ChessPiece(color, number);


            }
        }

        boolean temp = true;
        if (19 <= src.getIndex() && src.getIndex() <= 22) {
            temp = false;
        }
        if (12 <= dest.getIndex() && dest.getIndex() <= 18) {
            temp = false;
        }
        FastWay = implementFofMethods.BonusLocation(dest, piece);
        FastWay = FastWay & temp;

        LocationLast = dest;
        ChessPiece srcPiece = piece;
        ChessPiece newPiece = srcPiece;
        if (compete != null && !compete.b) {
            newPiece = EatenPiece;
        }
        if (srcPiece instanceof StackPiece &&
                ((StackPiece) srcPiece).getGuestPieces().size() != 0) {//src的棋子是复数个棋子重合（非叠子）
            newPiece = ((StackPiece) srcPiece).getOneGuest();
        } else {
            removeChessPieceAt(src);
        }

        if (IsThereAPlane && !WhetherToEat) {
            implementFofMethods.ChooseToStack(newPiece, this.getGridAt(dest).getPiece(), this, dest);
        } else {
            setChessPieceAt(dest, newPiece);
        }

        if (dest.getIndex() == 18) {
            ChessPiece chessPiece = this.getGridAt(dest).getPiece();
            if (chessPiece instanceof StackPiece) {
                for (Integer piece1 : ((StackPiece) chessPiece).getStackPieceNums()) {
                    if (srcPiece.getPlayer() == 0) {
                        WinOf0++;
                    } else if (srcPiece.getPlayer() == 1) {
                        WinOf1++;
                    } else if (srcPiece.getPlayer() == 2) {
                        WinOf2++;
                    } else {
                        WinOf3++;
                    }
                    ChessPiece chessPiece1 = new ChessPiece(srcPiece.getPlayer(), piece1);
                    setChessPieceAt(
                            grid[srcPiece.getPlayer()][piece1 + dimension + endDimension].getLocation(),
                            chessPiece1);
                    this.getGridAt(new ChessBoardLocation(srcPiece.getPlayer(),
                            piece1 + dimension + endDimension)).win = true;
                }
                if (srcPiece.getPlayer() == 0) {
                    WinOf0++;
                } else if (srcPiece.getPlayer() == 1) {
                    WinOf1++;
                } else if (srcPiece.getPlayer() == 2) {
                    WinOf2++;
                } else {
                    WinOf3++;
                }
                ChessPiece chessPiece2 = new ChessPiece(srcPiece.getPlayer(), chessPiece.getNumber());
                setChessPieceAt(
                        grid[srcPiece.getPlayer()][chessPiece.getNumber() + dimension + endDimension]
                                .getLocation(), chessPiece2);

                this.getGridAt(new ChessBoardLocation(srcPiece.getPlayer(),
                        chessPiece.getNumber() + dimension + endDimension)).win = true;
            } else {
                if (srcPiece.getPlayer() == 0) {
                    WinOf0++;
                } else if (srcPiece.getPlayer() == 1) {
                    WinOf1++;
                } else if (srcPiece.getPlayer() == 2) {
                    WinOf2++;
                } else {
                    WinOf3++;
                }
            }
            removeChessPieceAt(dest);

        }

        if (WinOf0 == 4 || WinOf2 == 4 || WinOf1 == 4 || WinOf3 == 4) {
            Win win = new Win();
            JLabel j = new JLabel("0");
            win.add(j);
            j.setVisible(true);
            win.setLocation(396, 585);
        }
    }

    public ChessBoardLocation getLocationLast() {
        return LocationLast;
    }

    public boolean isFastWay() {
        return FastWay;
    }


    public ChessBoardLocation nextLocation(ChessBoardLocation location, ChessPiece piece) {
        // FIXME: This move the chess to next jump location instead of nearby next location
        int OldColor = location.getColor();
        int OldIndex = location.getIndex();
        int NewColor, NewIndex;

        if (NumberTotal[piece.getPlayer()][piece.getNumber()] == 0) {
            if (OldIndex >= 19 && OldIndex <= 22) {
                NewColor = OldColor;
                NewIndex = 23;
            } else if (OldIndex == 23) {
                if (OldColor == 0) {
                    NewColor = 1;
                } else if (OldColor == 1) {
                    NewColor = 2;
                } else if (OldColor == 2) {
                    NewColor = 3;
                } else {
                    NewColor = 0;
                }
                NewIndex = 0;
            } else if (OldIndex == 12) {
                System.out.println("player:" + piece.getPlayer());
                if (piece.getPlayer() == OldColor) {
                    NewColor = OldColor;
                    NewIndex = OldIndex + 1;
                } else {
                    if (OldColor == 0) {
                        NewColor = 1;
                    } else if (OldColor == 1) {
                        NewColor = 2;
                    } else if (OldColor == 2) {
                        NewColor = 3;
                    } else {
                        NewColor = 0;
                    }
                    NewIndex = 9;
                }
            } else if (OldIndex >= 0 && OldIndex <= 11) {
                //0 to 11(on circle,except 12)
                if (0 <= OldIndex && OldIndex <= 2) {
                    NewIndex = OldIndex + 10;
                } else {
                    NewIndex = OldIndex - 3;
                    //3<= OldIndex <= 11
                }
                if (OldColor == 0) {
                    NewColor = 1;
                } else if (OldColor == 1) {
                    NewColor = 2;
                } else if (OldColor == 2) {
                    NewColor = 3;
                } else {
                    NewColor = 0;
                }
            } else {
                //13-18
                if (OldIndex == 18) {
                    NumberTotal[piece.getPlayer()][piece.getNumber()] =
                            NumberTotal[piece.getPlayer()][piece.getNumber()] + 1;
                    NewColor = OldColor;
                    NewIndex = OldIndex - 1;
                } else {
                    NewColor = OldColor;
                    NewIndex = OldIndex + 1;
                }
            }
        } else {
            NewColor = OldColor;
            if (OldIndex == 12) {
                NumberTotal[piece.getPlayer()][piece.getNumber()] =
                        NumberTotal[piece.getPlayer()][piece.getNumber()] + 1;
                NewIndex = OldIndex + 1;
            } else if (OldIndex == 18) {
                NumberTotal[piece.getPlayer()][piece.getNumber()] =
                        NumberTotal[piece.getPlayer()][piece.getNumber()] + 1;
                NewIndex = OldIndex - 1;
            } else {
                int num = NumberTotal[piece.getPlayer()][piece.getNumber()] % 2;
                if (num == 0) {
                    NewIndex = OldIndex + 1;
                } else {
                    NewIndex = OldIndex - 1;
                }
            }
        }

        System.out.println("NewColor:" + NewColor + " NewIndex:" + NewIndex);

        return new ChessBoardLocation(NewColor, NewIndex);
    }


    @Override
    public void registerListener(ChessBoardListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(ChessBoardListener listener) {
        listenerList.remove(listener);
    }
}
