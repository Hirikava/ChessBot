package Domain;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.Optional;

public class GameSession {

    private PlayerColour Turn;
    Figure[][] chessBoard;

    public GameSession() {
        Turn = PlayerColour.White;

        //Init black pieces
        chessBoard = new Figure[8][8];
        chessBoard[7][0] = new Figure(Pieces.Rook, PlayerColour.Black);
        chessBoard[7][1] = new Figure(Pieces.Knight, PlayerColour.Black);
        chessBoard[7][2] = new Figure(Pieces.Bishop, PlayerColour.Black);
        chessBoard[7][3] = new Figure(Pieces.Queen, PlayerColour.Black);
        chessBoard[7][4] = new Figure(Pieces.King, PlayerColour.Black);
        chessBoard[7][5] = new Figure(Pieces.Bishop, PlayerColour.Black);
        chessBoard[7][6] = new Figure(Pieces.Knight, PlayerColour.Black);
        chessBoard[7][7] = new Figure(Pieces.Rook, PlayerColour.Black);

        for (int j = 0; j < 8; j++)
            chessBoard[6][j] = new Figure(Pieces.Pawn, PlayerColour.Black);

        //Init white pieces
        chessBoard[0][0] = new Figure(Pieces.Rook, PlayerColour.White);
        chessBoard[0][1] = new Figure(Pieces.Knight, PlayerColour.White);
        chessBoard[0][2] = new Figure(Pieces.Bishop, PlayerColour.White);
        chessBoard[0][3] = new Figure(Pieces.Queen, PlayerColour.White);
        chessBoard[0][4] = new Figure(Pieces.King, PlayerColour.White);
        chessBoard[0][5] = new Figure(Pieces.Bishop, PlayerColour.White);
        chessBoard[0][6] = new Figure(Pieces.Knight, PlayerColour.White);
        chessBoard[0][7] = new Figure(Pieces.Rook, PlayerColour.White);

        for (int j = 0; j < 8; j++)
            chessBoard[1][j] = new Figure(Pieces.Pawn, PlayerColour.White);
    }

    private Figure[][] copyChessBoard() {
        Figure[][] copyBoard = new Figure[8][8];

        for (int i = 0; i < chessBoard.length; i++)
            System.arraycopy(chessBoard[i], 0, copyBoard[i], 0, chessBoard[i].length);
        return copyBoard;
    }

    public GameState createGameState() {
        return new GameState(Optional.empty(), chessBoard.clone());
    }

    private Boolean chooseFiguresTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        switch (figure.getFiguresName()) {
            case King:
                return kingsTurn(board, figure, coordsFrom, coordsTo);
            case Pawn:
                return pawnsTurn(board, figure, coordsFrom, coordsTo);
            case Bishop:
                return bishopsTurn(board, figure, coordsFrom, coordsTo);
            case Rook:
                return rooksTurn(board, figure, coordsFrom, coordsTo);
            case Knight:
                return knightsTurn(board, figure, coordsFrom, coordsTo);
            case Queen:
                return queensTurn(board, figure, coordsFrom, coordsTo);
        }
        return false;
    }


    private void moveFigure(Figure[][] board, Cords coordsFrom, Cords coordsTo) {
        board[coordsTo.getX()][coordsTo.getY()] = new Figure(board[coordsFrom.getX()][coordsFrom.getY()]);
        board[coordsFrom.getX()][coordsFrom.getY()] = null;
    }

    private Cords getCordsOfKing(Figure[][] board, PlayerColour playerColour) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] != null && board[i][j].getColour() == playerColour && board[i][j].getFiguresName() == Pieces.King)
                    return new Cords(i, j);
        throw new InvalidStateException("No king on board");
    }

    private ArrayList<Cords> getAllFiguresOfChosenColor(Figure[][] board, PlayerColour colour) {
        ArrayList<Cords> list = new ArrayList<>();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] != null && board[i][j].getColour() == colour)
                    list.add(new Cords(i, j));
        return list;
    }

    //можем занять клетку, если она пустая или на ней находится фигура противника
    private Boolean takeSquare(Figure[][] board, Cords coordsTo, PlayerColour colour) {
        return board[coordsTo.getX()][coordsTo.getY()] == null || board[coordsTo.getX()][coordsTo.getY()].getColour() != colour;
    }

    private Boolean coordsDontChange(Cords coordsFrom, Cords coordsTo) {
        return coordsFrom.getX() == coordsTo.getX() && coordsFrom.getY() == coordsTo.getY();
    }

    private Boolean pawnsTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        if (figure.getColour() == PlayerColour.White) {
            //делаем один ход вперед, если поле пустое
            if (coordsTo.getX() - coordsFrom.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && board[coordsTo.getX()][coordsTo.getY()] == null) {
                if (coordsTo.getX() == 7) {
                    return true;
                }
                return true;
            }
            //если Х = 1, то мы можем сделать ход на две клетки вперёд и если клетка пуста
            if (coordsFrom.getY() == coordsTo.getY() && coordsTo.getX() - coordsFrom.getX() == 2 && board[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 1) {
                return true;
            }

            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && board[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.Black) {
                if (coordsTo.getX() == 7) {
                    return true;
                }
                return true;
            }
        }
        if (figure.getColour() == PlayerColour.Black) {
            if (coordsFrom.getX() - coordsTo.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && board[coordsTo.getX()][coordsTo.getX()] == null) {
                if (coordsTo.getX() == 0) {
                    return true;
                }
                return true;
            }
            if (coordsFrom.getY() == coordsTo.getY() && coordsFrom.getX() - coordsTo.getX() == 2 && board[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 6) {
                return true;
            }
            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && board[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.White) {
                if (coordsTo.getX() == 0) {
                    return true;
                }
                return true;
            }
        }

        return false;
    }

    private Boolean noFigureInTheWayForX(Figure[][] board, int FromThisCoord, int ToThisCoord, int UnchangableCoord) {
        //changes X
        if (FromThisCoord > ToThisCoord) {
            int x = FromThisCoord;
            FromThisCoord = ToThisCoord;
            ToThisCoord = x;
        }
        for (int i = FromThisCoord + 1; i < ToThisCoord; i++) {
            if (board[i][UnchangableCoord] != null) return false;
        }
        return true;
    }

    private Boolean noFigureInTheWayForY(Figure[][] board, int FromThisCoord, int ToThisCoord, int UnchangableCoord) {
        //changes Y
        if (FromThisCoord > ToThisCoord) {
            int x = FromThisCoord;
            FromThisCoord = ToThisCoord;
            ToThisCoord = x;
        }
        for (int i = FromThisCoord + 1; i < ToThisCoord; i++) {
            if (board[UnchangableCoord][i] != null) return false;
        }
        return true;
    }

    private Boolean rooksTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        PlayerColour colour = figure.getColour();
        if (coordsFrom.getX() == coordsTo.getX() && coordsFrom.getY() != coordsTo.getY() && takeSquare(board, coordsTo, colour) && noFigureInTheWayForY(board, coordsFrom.getY(), coordsTo.getY(), coordsFrom.getX())) {
            return true;
        }

        return coordsFrom.getX() != coordsTo.getX() && coordsFrom.getY() == coordsTo.getY() && takeSquare(board, coordsTo, colour) && noFigureInTheWayForX(board, coordsFrom.getX(), coordsTo.getX(), coordsFrom.getY());
    }

    private Boolean findFigureInTheDiagonal(Figure[][] board, Cords cordsFrom, Cords cordsTo) {
        int dx = cordsFrom.getX() < cordsTo.getX() ? 1 : -1;
        int dy = cordsFrom.getY() < cordsTo.getY() ? 1 : -1;

        int cordX = cordsFrom.getX() + dx;
        int cordY = cordsFrom.getY() + dy;
        while(cordX != cordsTo.getX() && cordY != cordsTo.getY()) {
            if (board[cordX][cordY] != null) return false;
            cordX += dx;
            cordY += dy;
        }
        return true;
    }

    private Boolean diagonalTurn(Cords coordsFrom, Cords coordsTo) {
        return Math.abs(coordsFrom.getX() - coordsTo.getX()) == Math.abs(coordsFrom.getY() - coordsTo.getY());
    }

    private Boolean bishopsTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        return diagonalTurn(coordsFrom, coordsTo) && takeSquare(board, coordsTo, colour) && findFigureInTheDiagonal(board, coordsFrom, coordsTo);
    }

    private Boolean kingsTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        if ((Math.abs(coordsFrom.getX() - coordsTo.getX()) + Math.abs(coordsFrom.getY() - coordsTo.getY()) == 1 ||
                Math.abs(coordsFrom.getX() - coordsTo.getX()) == 1 && Math.abs(coordsFrom.getY() - coordsTo.getY()) == 1) && takeSquare(board, coordsTo, colour)) {
            return true;
        }
        return false;
    }

    private Boolean knightsTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;
        return (Math.abs(coordsFrom.getX() - coordsTo.getX()) == 1 && Math.abs(coordsFrom.getY() - coordsTo.getY()) == 2 || Math.abs(coordsFrom.getX() - coordsTo.getX()) == 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) == 1)
                && takeSquare(board, coordsTo, colour);
    }

    private Boolean queensTurn(Figure[][] board, Figure figure, Cords coordsFrom, Cords coordsTo) {
        if (coordsDontChange(coordsFrom, coordsTo)) return false;
        return bishopsTurn(board, figure, coordsFrom, coordsTo) || rooksTurn(board, figure, coordsFrom, coordsTo);
    }


    private Boolean isCheck(Cords coordsFrom, Cords coordsTo) {
        Figure[][] copyBoard = copyChessBoard();
        moveFigure(copyBoard, coordsFrom, coordsTo);
        if (Turn == PlayerColour.White) {
            Cords cordsOfKing = getCordsOfKing(copyBoard, PlayerColour.White);
            for (Cords enemyFigure : getAllFiguresOfChosenColor(copyBoard, PlayerColour.Black)) {
                if (chooseFiguresTurn(copyBoard, copyBoard[enemyFigure.getX()][enemyFigure.getY()], enemyFigure, cordsOfKing)) {
                    return true;
                }
            }
        } else {
            Cords cordsOfKing = getCordsOfKing(copyBoard, PlayerColour.Black);
            for (Cords enemyFigure : getAllFiguresOfChosenColor(copyBoard, PlayerColour.White)) {
                if (chooseFiguresTurn(copyBoard, copyBoard[enemyFigure.getX()][enemyFigure.getY()], enemyFigure, cordsOfKing)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean figuresTurn(Cords coordsFrom, Cords coordsTo) {
        Figure figure = chessBoard[coordsFrom.getX()][coordsFrom.getY()];
        return figure != null && chooseFiguresTurn(chessBoard, figure, coordsFrom, coordsTo) && !isCheck(coordsFrom, coordsTo);
    }

    private Boolean CheckCords(Cords coords) {
        return coords.getX() < 0 || coords.getX() > 7 || coords.getY() < 0 || coords.getY() > 7; //проверили,что координаты в правильном диапазоне
    }

    private void passTurn() {
        if (Turn == PlayerColour.Black)
            Turn = PlayerColour.White;
        else
            Turn = PlayerColour.Black;
    }

    public synchronized TurnResult MakeTurn(PlayerColour colour, Cords coordsFrom, Cords coordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        if (CheckCords(coordsFrom) || CheckCords(coordsTo))
            return new TurnResult(TurnError.WrongCoords, createGameState());
        if (!figuresTurn(coordsFrom, coordsTo))
            return new TurnResult(TurnError.WrongTurn, createGameState());
        moveFigure(chessBoard, coordsFrom, coordsTo);
        passTurn();
        return new TurnResult(TurnError.None, createGameState());
    }
}
