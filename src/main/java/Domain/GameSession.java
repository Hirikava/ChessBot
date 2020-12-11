package Domain;

import java.util.Optional;

public class GameSession {
    private PlayerColour Turn;
    private Figure[][] Board;

    public GameSession() {
        Turn = PlayerColour.White;
        Board = new Figure[8][8];
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


    public TurnResult MakeTurn(PlayerColour colour, Coords coordsFrom, Coords coordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        return new TurnResult(TurnError.None, createGameState());
    }
}
