package ServerModels;

import Domain.GameSession;
import Domain.PlayerColour;

public class GameInfo {

    private Player opponent;
    private GameSession gameSession;
    private PlayerColour playerColour;

    public GameInfo(Player opponent, GameSession gameSession, PlayerColour playerColour)
    {
        this.opponent = opponent;
        this.gameSession = gameSession;
        this.playerColour = playerColour;
    }

    public PlayerColour getPlayerColour() {
        return playerColour;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public Player getOpponent() {
        return opponent;
    }
}
