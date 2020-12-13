package Domain;

import java.util.Optional;

public class GameSession {
    private PlayerColour Turn;

    public GameSession() {
        Turn = PlayerColour.White;
    }

    private GameState createGameState() {
        return new GameState(Optional.of(Turn));
    }

    public Boolean PawnsTurn(ChessField field, Coords coordsFrom, Coords coordsTo){
        return true;
    }

    public Boolean FiguresTurn(ChessField field, Coords coordsFrom, Coords coordsTo){
        Figure figure = field.getFigure(coordsFrom);
        if (figure == null) return false;
        else
            switch (figure.getFiguresName()){
                case King:
                    return true;
                    //break;
                case Pawn:
                    return PawnsTurn(field, coordsFrom, coordsTo);
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

     public Boolean CheckCoords(Coords coords){
         return coords.X < 0 || coords.X > 7 || coords.Y < 0 || coords.Y > 7; //проверили,что координаты в правильном диапазоне
     }

    public TurnResult MakeTurn(PlayerColour colour, Coords coordsFrom, Coords coordsTo, ChessField field) {
         if (CheckCoords(coordsFrom) && !CheckCoords(coordsTo)) return new TurnResult(TurnError.WrongCoords, createGameState());
         if (!FiguresTurn(field, coordsFrom, coordsTo)) return new TurnResult(TurnError.NotAFigure, createGameState());
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        return new TurnResult(TurnError.None, createGameState());
    }
}
