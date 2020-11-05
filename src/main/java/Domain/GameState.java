package Domain;

import java.util.Optional;

public class GameState {
    final Optional<PlayerColour> Winner;

    public GameState(Optional<PlayerColour> winner) {
        Winner = winner;
    }
}
