package Service;

import Domain.GameSession;
import Domain.PlayerColour;
import ServerModels.Player;
import org.javatuples.Triplet;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameSessionsService {


    private ConcurrentHashMap<Player, Triplet<List<Player>, GameSession, PlayerColour>> gameSessionsMap
            = new ConcurrentHashMap<Player, Triplet<List<Player>, GameSession, PlayerColour>>();


    public void startNewMatch(Player player1, Player player2) {
        GameSession gameSession = new GameSession();
        List<Player> players = Arrays.asList(player1, player2);
        gameSessionsMap.put(player1, new Triplet<List<Player>, GameSession, PlayerColour>(players, gameSession, PlayerColour.White));
        gameSessionsMap.put(player2, new Triplet<List<Player>, GameSession, PlayerColour>(players, gameSession, PlayerColour.Black));
    }

    public Triplet<List<Player>, GameSession, PlayerColour> getGameSession(Player player) {
        return gameSessionsMap.get(player);
    }
}
