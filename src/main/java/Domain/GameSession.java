package Domain;

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

    public GameState createGameState() {
        return new GameState(Optional.of(Turn), chessBoard.clone());
    }

    private void MoveFigure(Figure figure, Cords coordsFrom, Cords coordsTo){
        chessBoard[coordsFrom.getX()][coordsFrom.getY()] = null;
        chessBoard[coordsTo.getX()][coordsTo.getY()] = figure;
    }


    private Boolean PawnsTurn(Figure figure, Cords coordsFrom, Cords coordsTo) {
        if (coordsFrom.getX() == coordsTo.getY() && coordsFrom.getY() == coordsTo.getY()) return false;
        if (figure.getColour() == PlayerColour.White) {
            if (coordsTo.getX() - coordsFrom.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && chessBoard[coordsTo.getX()][coordsTo.getY()] == null) {
                MoveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (coordsFrom.getY() == coordsTo.getY() && coordsTo.getX() - coordsFrom.getX() == 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 1){
                MoveFigure(figure, coordsFrom, coordsTo);
                return true;
            }

            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.Black) {
                MoveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
        }
        if (figure.getColour() == PlayerColour.Black) {
            if (coordsFrom.getX() - coordsTo.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && chessBoard[coordsTo.getX()][coordsTo.getX()] == null) {
                MoveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (coordsFrom.getY() == coordsTo.getY() && coordsFrom.getX() - coordsTo.getX() == 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 6){
                MoveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.White) {
                MoveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
        }

        return false;
    }

    private Boolean NoFigureInTheWayForX(int FromThisCoord, int ToThisCoord, int UnchangableCoord){
        //changes X
        if (FromThisCoord > ToThisCoord) {
            int x = FromThisCoord;
            FromThisCoord = ToThisCoord;
            ToThisCoord = x;
        }
        for (int i = FromThisCoord+1; i < ToThisCoord; i++) {
            if (chessBoard[i][UnchangableCoord] != null) return false;
        }
        return true;
    }

    private Boolean NoFigureInTheWayForY(int FromThisCoord, int ToThisCoord, int UnchangableCoord){
        //changes Y
        if (FromThisCoord > ToThisCoord) {
            int x = FromThisCoord;
            FromThisCoord = ToThisCoord;
            ToThisCoord = x;
        }
        for (int i = FromThisCoord + 1; i < ToThisCoord; i++) {
            if (chessBoard[UnchangableCoord][i] != null) return false;
        }
        return true;
    }

    private Boolean RooksTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        PlayerColour colour = figure.getColour();
        if (coordsFrom.getX() == coordsTo.getX() && coordsFrom.getY() == coordsTo.getY()) return false;

        if (coordsFrom.getX() == coordsTo.getX() && coordsFrom.getY() != coordsTo.getY() && (chessBoard[coordsTo.getX()][coordsTo.getY()] == null || chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() != colour )
                && NoFigureInTheWayForY(coordsFrom.getY(), coordsTo.getY(), coordsFrom.getX())) {
            MoveFigure(figure, coordsFrom, coordsTo);
            return true;
        }

        if (coordsFrom.getX() != coordsTo.getX() && coordsFrom.getY() == coordsTo.getY() && (chessBoard[coordsTo.getX()][coordsTo.getY()] == null || chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() != colour )
                && NoFigureInTheWayForX(coordsFrom.getX(), coordsTo.getX(), coordsFrom.getY())){
            MoveFigure(figure, coordsFrom, coordsTo);
            return true;
        }
        return false;
    }



    private Boolean FiguresTurn(Cords coordsFrom, Cords coordsTo) {
        Figure figure = chessBoard[coordsFrom.getX()][coordsFrom.getY()];
        if (figure == null) return false;
        else
            switch (figure.getFiguresName()) {
                case King:
                    return true;
                case Pawn:
                    return PawnsTurn(figure, coordsFrom, coordsTo);
                case Bishop:
                    return true;
                case Rook:
                    return RooksTurn(figure, coordsFrom, coordsTo);
                case Knight:
                    return true;
                case Queen:
                    return true;
            }
        return true;
    }

    public Boolean CheckCords(Cords coords) {
        return coords.getX() < 0 || coords.getX() > 7 || coords.getY() < 0 || coords.getY() > 7; //проверили,что координаты в правильном диапазоне
    }

    public TurnResult MakeTurn(PlayerColour colour, Cords coordsFrom, Cords coordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        if (CheckCords(coordsFrom) || CheckCords(coordsTo))
            return new TurnResult(TurnError.WrongCoords, createGameState());
        if (!FiguresTurn(coordsFrom, coordsTo)) return new TurnResult(TurnError.NotAFigure, createGameState());
        return new TurnResult(TurnError.None, createGameState());
    }
}
