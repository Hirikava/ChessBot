package Domain;

import java.util.Optional;

public class GameSession {
    private PlayerColour Turn;
    private Figure[][] Board;

    public GameSession() {
        Turn = PlayerColour.White;
        Board = new Figure[8][8];

        //Init black pieces
        Board[7][0] = new Figure(Pieces.Rook, PlayerColour.Black);
        Board[7][1] = new Figure(Pieces.Knight, PlayerColour.Black);
        Board[7][2] = new Figure(Pieces.Bishop, PlayerColour.Black);
        Board[7][3] = new Figure(Pieces.Queen, PlayerColour.Black);
        Board[7][4] = new Figure(Pieces.King, PlayerColour.Black);
        Board[7][5] = new Figure(Pieces.Bishop, PlayerColour.Black);
        Board[7][6] = new Figure(Pieces.Knight, PlayerColour.Black);
        Board[7][7] = new Figure(Pieces.Rook, PlayerColour.Black);
        for (int i = 0; i < 8; i++)
            Board[6][i] = new Figure(Pieces.Pawn, PlayerColour.Black);

        //Init white pieces
        Board[0][0] = new Figure(Pieces.Rook, PlayerColour.White);
        Board[0][1] = new Figure(Pieces.Knight, PlayerColour.White);
        Board[0][2] = new Figure(Pieces.Bishop, PlayerColour.White);
        Board[0][3] = new Figure(Pieces.Queen, PlayerColour.White);
        Board[0][4] = new Figure(Pieces.King, PlayerColour.White);
        Board[0][5] = new Figure(Pieces.Bishop, PlayerColour.White);
        Board[0][6] = new Figure(Pieces.Knight, PlayerColour.White);
        Board[0][7] = new Figure(Pieces.Rook, PlayerColour.White);
        for (int i = 0; i < 8; i++)
            Board[1][i] = new Figure(Pieces.Pawn, PlayerColour.White);
    }

    public GameState createGameState() {
        return new GameState(Optional.of(Turn), Board);
    }

    /*public void FiguresTurn(ChessField field, Coords coordsFrom, Coords coordsTo){
        Figure figure = ChessField.chessBoard[coordsFrom.X][coordsFrom.Y];
        switch (figure.getFiguresName()){
            case King:
                //метод(цвет, координатыОткуда, координатыКуда)
                break;
            case Pawn:
                break;
        }
    }*/


    public TurnResult MakeTurn(PlayerColour colour, Cords cordsFrom, Cords cordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());

        if (!ValidateCords(cordsFrom) || !ValidateCords(cordsTo))
            return new TurnResult(TurnError.InvalidCords, createGameState());

        if (Board[cordsFrom.getY()][cordsFrom.getX()] == null)
            return new TurnResult(TurnError.NoFigure, createGameState());



        Board[cordsTo.getY()][cordsTo.getX()] = Board[cordsFrom.getY()][cordsFrom.getX()];
        Board[cordsFrom.getY()][cordsFrom.getX()] = null;

        SwitchTurn();
        return new TurnResult(TurnError.None, createGameState());
    }

    private boolean ValidateCords(Cords cords) {
        return cords.getX() <= 7 && cords.getX() >= 0 && cords.getY() <= 7 && cords.getY() >= 0;
    }

    private void SwitchTurn() {
        if (Turn == PlayerColour.White)
            Turn = PlayerColour.Black;
        else
            Turn = PlayerColour.White;
    }
}
