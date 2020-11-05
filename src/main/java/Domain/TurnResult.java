package Domain;

public class TurnResult {
    final TurnError Error;
    final GameState State;

    public TurnResult(TurnError error, GameState state) {
        Error = error;
        State = state;
    }
}
