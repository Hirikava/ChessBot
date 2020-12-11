package Service;

import Domain.GameSession;
import Domain.PlayerColour;
import ServerModels.Player;
import com.google.inject.Inject;
import org.javatuples.Triplet;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameSessionsService {


    @Inject
    private ISendMessageService sendMessageService;

    private ConcurrentHashMap<Player, Triplet<List<Player>, GameSession, PlayerColour>> gameSessionsMap
            = new ConcurrentHashMap<Player, Triplet<List<Player>, GameSession, PlayerColour>>();


    public void startNewMatch(Player player1, Player player2) {
        GameSession gameSession = new GameSession();
        List<Player> players = Arrays.asList(player1, player2);

        gameSessionsMap.put(player1, new Triplet<List<Player>, GameSession, PlayerColour>(players, gameSession, PlayerColour.White));
        gameSessionsMap.put(player2, new Triplet<List<Player>, GameSession, PlayerColour>(players, gameSession, PlayerColour.Black));

        SendMessage message = new SendMessage(player1.getChatId(), String.format("Ваш соперник %s.", player2.getUserName()));
        SendMessage message2 = new SendMessage(player2.getChatId(), String.format("Ваш соперник %s.", player1.getUserName()));
        sendMessageService.Send(message);
        sendMessageService.Send(message2);
    }

    public Triplet<List<Player>, GameSession, PlayerColour> getGameSession(Player player) {
        return gameSessionsMap.get(player);
    }
}
