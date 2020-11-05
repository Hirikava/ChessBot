package Domain;

import java.util.Optional;

public class GameSession {
    public TurnResult MakeTurn(PlayerColour colour, Coords coordsFrom, Coords coordsTo) {
        if (colour != Turn)
            return new TurnResult(TurnError.AnotherPlayerTurn, createGameState());
        return new TurnResult(TurnError.None, createGameState());
    }

    public GameSession() {
        Turn = PlayerColour.White;
    }

    private GameState createGameState() {
        return new GameState(Optional.of(PlayerColour.Black));
    }

    private PlayerColour Turn;

}
