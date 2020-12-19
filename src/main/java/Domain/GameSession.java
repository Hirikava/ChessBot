package Domain;

import org.checkerframework.checker.units.qual.C;

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

    private void moveFigure(Figure figure, Cords coordsFrom, Cords coordsTo){
        chessBoard[coordsFrom.getX()][coordsFrom.getY()] = null;
        chessBoard[coordsTo.getX()][coordsTo.getY()] = figure;
    }

    //можем занять клетку, если она пустая или на ней находится фигура противника
    private Boolean takeSquare(Cords coordsTo, PlayerColour colour){
        return chessBoard[coordsTo.getX()][coordsTo.getY()] == null || chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() != colour;
    }

    private Boolean coordsDontChange(Cords coordsFrom, Cords coordsTo){
        return coordsFrom.getX() == coordsTo.getX() && coordsFrom.getY() == coordsTo.getY();
    }

    private Boolean pawnsTurn(Figure figure, Cords coordsFrom, Cords coordsTo) {
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        if (figure.getColour() == PlayerColour.White) {
            if (coordsTo.getX() - coordsFrom.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && chessBoard[coordsTo.getX()][coordsTo.getY()] == null) {
                moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (coordsFrom.getY() == coordsTo.getY() && coordsTo.getX() - coordsFrom.getX() == 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 1){
                moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }

            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.Black) {
                moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
        }
        if (figure.getColour() == PlayerColour.Black) {
            if (coordsFrom.getX() - coordsTo.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && chessBoard[coordsTo.getX()][coordsTo.getX()] == null) {
                moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (coordsFrom.getY() == coordsTo.getY() && coordsFrom.getX() - coordsTo.getX() == 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 6){
                moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.White) {
                moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
        }

        return false;
    }

    private Boolean noFigureInTheWayForX(int FromThisCoord, int ToThisCoord, int UnchangableCoord){
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

    private Boolean noFigureInTheWayForY(int FromThisCoord, int ToThisCoord, int UnchangableCoord){
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

    private Boolean rooksTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        PlayerColour colour = figure.getColour();

        if (coordsFrom.getX() == coordsTo.getX() && coordsFrom.getY() != coordsTo.getY() && takeSquare(coordsTo, colour) && noFigureInTheWayForY(coordsFrom.getY(), coordsTo.getY(), coordsFrom.getX())) {
            moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }

        if (coordsFrom.getX() != coordsTo.getX() && coordsFrom.getY() == coordsTo.getY() && takeSquare(coordsTo, colour) && noFigureInTheWayForX(coordsFrom.getX(), coordsTo.getX(), coordsFrom.getY())){
            moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }
        return false;
    }

    private Boolean findFigureInTheDiagonal(int FromThisCoordX, int ToThisCoordX, int FromThisCoordY){
        for (int i = FromThisCoordX + 1; i < ToThisCoordX; i++){
            FromThisCoordY++;
            if (chessBoard[i][FromThisCoordY] != null) return false;
            FromThisCoordY++;
        }
        return true;
    }

    private Boolean noFigureInTheWayDiagonal(Cords coordsFrom, Cords coordsTo){
        int FromThisCoordX = coordsFrom.getX();
        int FromThisCoordY = coordsFrom.getY();
        int ToThisCoordX = coordsTo.getX();


        if (coordsFrom.getX() < coordsTo.getX() && coordsFrom.getY() < coordsTo.getY()){
            return findFigureInTheDiagonal(FromThisCoordX, ToThisCoordX, FromThisCoordY);
        }
        if (coordsFrom.getX() > coordsTo.getX() && coordsFrom.getY() > coordsTo.getY() ){
            FromThisCoordX = coordsTo.getX();
            ToThisCoordX = coordsFrom.getX();
            FromThisCoordY = coordsTo.getY();
            return findFigureInTheDiagonal(FromThisCoordX, ToThisCoordX, FromThisCoordY);
        }

        if (coordsFrom.getX() > coordsTo.getX() && coordsFrom.getY() < coordsTo.getY()){
            for (int i = FromThisCoordX-1; i > ToThisCoordX; i--){
                FromThisCoordY++;
                if (chessBoard[i][FromThisCoordY] != null) return false;
            }
        }

        if (coordsFrom.getX() < coordsTo.getX() && coordsFrom.getY() > coordsTo.getY()){
            for (int i = FromThisCoordX+1; i < ToThisCoordX; i++){
                FromThisCoordY--;
                if (chessBoard[i][FromThisCoordY] != null) return false;
            }
        }
        return true;
    }


    private Boolean diagonalTurn(Cords coordsFrom, Cords coordsTo){
        return Math.abs(coordsFrom.getX() - coordsTo.getX()) == Math.abs(coordsFrom.getY() - coordsTo.getY());
    }

    private Boolean bishopsTurn(Figure figure, Cords coordsFrom, Cords coordsTo) {
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        if (diagonalTurn(coordsFrom, coordsTo) && takeSquare(coordsTo, colour) && noFigureInTheWayDiagonal(coordsFrom, coordsTo)){
            moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }

        return false;
    }

    private Boolean kingsTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        if ((Math.abs(coordsFrom.getX()-coordsTo.getX())+Math.abs(coordsFrom.getY()-coordsTo.getY()) == 1 ||
                Math.abs(coordsFrom.getX() - coordsTo.getX()) == 1 && Math.abs(coordsFrom.getY() - coordsTo.getY()) == 1) && takeSquare(coordsTo, colour)){
            moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }
        return false;
    }

    private Boolean knightsTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;
        if ((Math.abs(coordsFrom.getX() - coordsTo.getX()) == 1 && Math.abs(coordsFrom.getY()-coordsTo.getY()) == 2 || Math.abs(coordsFrom.getX() - coordsTo.getX()) == 2 && Math.abs(coordsFrom.getY()-coordsTo.getY()) == 1)
                && takeSquare(coordsTo, colour)){
            moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }
        return false;
    }

    private Boolean queensTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        if (coordsDontChange(coordsFrom, coordsTo)) return false;
        return bishopsTurn(figure, coordsFrom, coordsTo) || rooksTurn(figure, coordsFrom, coordsTo);
    }

    private Boolean figuresTurn(Cords coordsFrom, Cords coordsTo) {
        Figure figure = chessBoard[coordsFrom.getX()][coordsFrom.getY()];
        if (figure == null) return false;
        else
            switch (figure.getFiguresName()) {
                case King:
                    return kingsTurn(figure, coordsFrom, coordsTo);
                case Pawn:
                    return pawnsTurn(figure, coordsFrom, coordsTo);
                case Bishop:
                    return bishopsTurn(figure, coordsFrom, coordsTo);
                case Rook:
                    return rooksTurn(figure, coordsFrom, coordsTo);
                case Knight:
                    return knightsTurn(figure, coordsFrom, coordsTo);
                case Queen:
                    return queensTurn(figure, coordsFrom, coordsTo);
            }
        return true;
    }

    private Boolean CheckCords(Cords coords) {
        return coords.getX() < 0 || coords.getX() > 7 || coords.getY() < 0 || coords.getY() > 7; //проверили,что координаты в правильном диапазоне
    }

    public TurnResult MakeTurn(PlayerColour colour, Cords coordsFrom, Cords coordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        if (CheckCords(coordsFrom) || CheckCords(coordsTo))
            return new TurnResult(TurnError.WrongCoords, createGameState());
        if (!figuresTurn(coordsFrom, coordsTo)) return new TurnResult(TurnError.NotAFigure, createGameState());
        return new TurnResult(TurnError.None, createGameState());
    }
}
