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

    public Boolean PawnsTurn(Cords coordsFrom, Cords coordsTo) {
        return true;
    }

    public Boolean FiguresTurn(Cords coordsFrom, Cords coordsTo) {
        Figure figure = chessBoard[coordsFrom.getX()][coordsFrom.getY()];
        if (figure == null) return false;
        else
            switch (figure.getFiguresName()) {
                case King:
                    return true;
                //break;
                case Pawn:
                    return PawnsTurn(coordsFrom, coordsTo);
                // break;
                case Bishop:
                    return true;
                // break;
                case Rook:
                    return true;
                //break;
                case Knight:
                    return true;
                // break;
                case Queen:
                    return true;
                //break;
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
