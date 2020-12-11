package Domain;

import java.util.Optional;

public class GameState {
    final Optional<PlayerColour> Winner;
    final Figure[][] Board;

    public GameState(Optional<PlayerColour> winner, Figure[][] board) {
        Winner = winner;
        Board = board;
    }

    public Optional<PlayerColour> getWinner() {
        return Winner;
    }

    public Figure[][] getBoard() {
        return Board;
    }
}
