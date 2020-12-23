package Domain;

import org.checkerframework.checker.units.qual.C;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.ArrayList;
import java.util.Optional;

public class GameSession {
    private PlayerColour Turn;
    Figure[][] chessBoard;
    Cords CoordsOfWhiteKing;
    Cords CoordsOfBlackKing;
    ArrayList<Cords> CoordsOfWhiteFigures= new ArrayList<Cords>();
    ArrayList<Cords>CoordsOfBlackFigures = new ArrayList<Cords>();

    public GameSession() {
        Turn = PlayerColour.White;

        CoordsOfWhiteKing = new Cords(0,4);
        CoordsOfBlackKing = new Cords(7, 4);


       for (int i = 0; i < 3; i++){
           for (int j  = 0; j < 8; j++){
               CoordsOfWhiteFigures.add(new Cords(i, j));
           }
       }

        for (int i = 6; i < 8; i++){
            for (int j  = 0; j < 8; j++){
                CoordsOfWhiteFigures.add(new Cords(i, j));
            }
        }

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
        return new GameState(Optional.empty(), chessBoard.clone());
    }

    private Boolean chooseFiguresTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
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
                return queensTurn(figure,coordsFrom, coordsTo);
        }
        return false;
    }

    private void moveFigure(Figure figure, Cords coordsFrom, Cords coordsTo){
        if (chessBoard[coordsTo.getX()][coordsTo.getY()] == null) {
            if (figure.getColour() == PlayerColour.White) changeCoordsInListOfCoords(CoordsOfWhiteFigures, coordsFrom, coordsTo);
            else changeCoordsInListOfCoords(CoordsOfBlackFigures, coordsFrom, coordsTo);
        }

        if (chessBoard[coordsTo.getX()][coordsTo.getY()] != null) {
            if (figure.getColour() == PlayerColour.White) deleteCoordsInListOfCoords(CoordsOfBlackFigures, coordsTo);
            else deleteCoordsInListOfCoords(CoordsOfWhiteFigures, coordsTo);
        }

        chessBoard[coordsFrom.getX()][coordsFrom.getY()] = null;
        chessBoard[coordsTo.getX()][coordsTo.getY()] = figure;
    }


    private void changeCoordsInListOfCoords(ArrayList<Cords> list, Cords coordsFrom, Cords coordsTo){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getX() == coordsFrom.getX() && list.get(i).getY() == coordsFrom.getY()) {
                list.set(i, coordsTo);
            }
        }

    }

    private void deleteCoordsInListOfCoords(ArrayList<Cords> list, Cords coords) {
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getX() == coords.getX() && list.get(i).getY() == coords.getY()) {
                list.remove(i);
            }
        }
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
            //делаем один ход вперед, если поле пустое
            if (coordsTo.getX() - coordsFrom.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && chessBoard[coordsTo.getX()][coordsTo.getY()] == null) {
                if (coordsTo.getX() == 7){
                    //moveFigure(new Figure(Pieces.Queen, PlayerColour.White), coordsFrom, coordsTo);
                    return true;
                }
                //moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            //если Х = 1, то мы можем сделать ход на две клетки вперёд и если клетка пуста
            if (coordsFrom.getY() == coordsTo.getY() && coordsTo.getX() - coordsFrom.getX() == 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 1){
                //moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }

            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.Black) {
                if (coordsTo.getX() == 7) {
                    //moveFigure(new Figure(Pieces.Queen, PlayerColour.White), coordsFrom, coordsTo);
                    return true;
                }
                //moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
        }
        if (figure.getColour() == PlayerColour.Black) {
            if (coordsFrom.getX() - coordsTo.getX() == 1 && coordsFrom.getY() == coordsTo.getY() && chessBoard[coordsTo.getX()][coordsTo.getX()] == null) {
                if (coordsTo.getX() == 0) {
                   // moveFigure(new Figure(Pieces.Queen, PlayerColour.Black), coordsFrom, coordsTo);
                    return true;
                }
                //moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (coordsFrom.getY() == coordsTo.getY() && coordsFrom.getX() - coordsTo.getX() == 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] == null && coordsFrom.getX() == 6){
                //moveFigure(figure, coordsFrom, coordsTo);
                return true;
            }
            if (Math.abs(coordsTo.getX() - coordsFrom.getX()) < 2 && Math.abs(coordsFrom.getY() - coordsTo.getY()) < 2 && chessBoard[coordsTo.getX()][coordsTo.getY()] != null && chessBoard[coordsTo.getX()][coordsTo.getY()].getColour() == PlayerColour.White) {
                if (coordsTo.getX() == 0) {
                    //moveFigure(new Figure(Pieces.Queen, PlayerColour.Black), coordsFrom, coordsTo);
                    return true;
                }
                //moveFigure(figure, coordsFrom, coordsTo);
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
            //moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }

        if (coordsFrom.getX() != coordsTo.getX() && coordsFrom.getY() == coordsTo.getY() && takeSquare(coordsTo, colour) && noFigureInTheWayForX(coordsFrom.getX(), coordsTo.getX(), coordsFrom.getY())){
            //moveFigure(figure, coordsFrom, coordsTo);
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
            //moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }

        return false;
    }

    private Boolean kingsTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;

        if ((Math.abs(coordsFrom.getX()-coordsTo.getX())+Math.abs(coordsFrom.getY()-coordsTo.getY()) == 1 ||
                Math.abs(coordsFrom.getX() - coordsTo.getX()) == 1 && Math.abs(coordsFrom.getY() - coordsTo.getY()) == 1) && takeSquare(coordsTo, colour)){
            if (colour == PlayerColour.White){
                CoordsOfWhiteKing = new Cords(coordsTo.getX(), coordsTo.getY());
            }
            else {
                CoordsOfBlackKing = new Cords(coordsTo.getX(), coordsTo.getY());
            }
            //moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }
        return false;
    }

    private Boolean knightsTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        PlayerColour colour = figure.getColour();
        if (coordsDontChange(coordsFrom, coordsTo)) return false;
        if ((Math.abs(coordsFrom.getX() - coordsTo.getX()) == 1 && Math.abs(coordsFrom.getY()-coordsTo.getY()) == 2 || Math.abs(coordsFrom.getX() - coordsTo.getX()) == 2 && Math.abs(coordsFrom.getY()-coordsTo.getY()) == 1)
                && takeSquare(coordsTo, colour)){
           // moveFigure(figure, coordsFrom, coordsTo);
            return true;
        }
        return false;
    }

    private Boolean queensTurn(Figure figure, Cords coordsFrom, Cords coordsTo){
        if (coordsDontChange(coordsFrom, coordsTo)) return false;
        return bishopsTurn(figure, coordsFrom, coordsTo) || rooksTurn(figure, coordsFrom, coordsTo);
    }


    private Boolean isCheck(){
        if (Turn == PlayerColour.White) {
            for(Cords coords : CoordsOfWhiteFigures){
                if (chooseFiguresTurn(chessBoard[coords.getX()][coords.getY()], coords, CoordsOfBlackKing)) {
                    return true;
                }
            }
        }
        else {
            for(Cords coords : CoordsOfBlackFigures){
                if (chooseFiguresTurn(chessBoard[coords.getX()][coords.getY()], coords, CoordsOfWhiteKing)) return true;
            }
        }
        return false;
    }

    private Boolean figuresTurn(Cords coordsFrom, Cords coordsTo) {
        Figure figure = chessBoard[coordsFrom.getX()][coordsFrom.getY()];
        if (figure == null) return false;
        else{
            if (chooseFiguresTurn(figure, coordsFrom, coordsTo)){
                moveFigure(figure, coordsFrom, coordsTo);
                if (isCheck()) System.out.println("check!");
                return true;
            }
        }
        return false;
    }

    private Boolean CheckCords(Cords coords) {
        return coords.getX() < 0 || coords.getX() > 7 || coords.getY() < 0 || coords.getY() > 7; //проверили,что координаты в правильном диапазоне
    }

    private void passTurn()
    {
        if(Turn == PlayerColour.Black)
            Turn = PlayerColour.White;
        else
            Turn = PlayerColour.Black;
    }

    public TurnResult MakeTurn(PlayerColour colour, Cords coordsFrom, Cords coordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        if (CheckCords(coordsFrom) || CheckCords(coordsTo))
            return new TurnResult(TurnError.WrongCoords, createGameState());
        if (!figuresTurn(coordsFrom, coordsTo))
            return new TurnResult(TurnError.NotAFigure, createGameState());
        passTurn();
        return new TurnResult(TurnError.None, createGameState());
    }
}
