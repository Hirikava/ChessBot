package Domain;

public class TurnResult {

    final TurnError Error;
    final GameState State;

    public TurnResult(TurnError error, GameState state) {
        Error = error;
        State = state;
    }

    public TurnError getTurnError() {
        return Error;
    }

    public GameState getGameState() {
        return State;
    }

}
